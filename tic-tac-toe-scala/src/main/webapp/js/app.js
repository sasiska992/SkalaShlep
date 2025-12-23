document.addEventListener('DOMContentLoaded', function() {
    const gameBoard = document.getElementById('game-board');
    const currentPlayerDisplay = document.getElementById('player');
    const gameStatusDisplay = document.getElementById('game-status');
    const resetBtn = document.getElementById('reset-btn');
    const modeToggleBtn = document.getElementById('mode-toggle');
    const botThoughtsDisplay = document.getElementById('bot-thoughts');
    
    let gameState = {
        board: [
            ['', '', ''],
            ['', '', ''],
            ['', '', '']
        ],
        currentPlayer: 'X',
        gameOver: false,
        winner: null
    };
    
    let gameMode = 'two-player'; // 'two-player' or 'vs-bot'
    
    // Create the game board
    function createBoard() {
        gameBoard.innerHTML = '';
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                const cell = document.createElement('div');
                cell.className = 'cell';
                cell.dataset.row = i;
                cell.dataset.col = j;
                
                cell.addEventListener('click', () => handleCellClick(i, j));
                
                gameBoard.appendChild(cell);
            }
        }
    }
    
    function updateBoard() {
        const cells = document.querySelectorAll('.cell');
        cells.forEach(cell => {
            const row = parseInt(cell.dataset.row);
            const col = parseInt(cell.dataset.col);
            const value = gameState.board[row][col];
            
            cell.textContent = value;
            cell.classList.remove('x', 'o');
            
            if (value === 'X') {
                cell.classList.add('x');
            } else if (value === 'O') {
                cell.classList.add('o');
            }
        });
        
        currentPlayerDisplay.textContent = gameState.currentPlayer;
        gameStatusDisplay.textContent = gameState.winner ? `Winner: ${gameState.winner}` : 
                                      gameState.gameOver ? 'Game Over: Draw!' : '';
        
        // Disable clicks if game is over
        cells.forEach(cell => {
            if (gameState.gameOver) {
                cell.style.pointerEvents = 'none';
            } else {
                cell.style.pointerEvents = 'auto';
            }
        });
    }
    
    async function handleCellClick(row, col) {
        if (gameState.gameOver || gameState.board[row][col] !== '') {
            return;
        }
        
        // Make the move
        await makeMove(row, col);
        
        // Check if game continues and it's bot's turn
        if (!gameState.gameOver && gameMode === 'vs-bot' && gameState.currentPlayer === 'O') {
            botThoughtsDisplay.style.display = 'block';
            setTimeout(makeBotMove, 800); // Simulate bot thinking
        }
    }
    
    async function makeMove(row, col) {
        try {
            const response = await fetch('/api/move', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `row=${row}&col=${col}`
            });
            
            if (response.ok) {
                gameState = await response.json();
                updateBoard();
            }
        } catch (error) {
            console.error('Error making move:', error);
        }
    }
    
    async function makeBotMove() {
        // Wait for the server to process the human's move first, then make bot move
        // The bot move will happen automatically on the server side after human move
        // So we just need to refresh the game state

        try {
            const response = await fetch('/api/game', {
                method: 'GET'
            });

            if (response.ok) {
                gameState = await response.json();
                updateBoard();

                // Check if the game continues and if it's the bot's turn again
                // (might happen if we're playing against a more advanced bot)
                if (!gameState.gameOver && gameMode === 'vs-bot' && gameState.currentPlayer === 'O') {
                    setTimeout(makeBotMove, 800); // Recursive call if needed
                }
            }
        } catch (error) {
            console.error('Error getting updated game state:', error);
        }

        botThoughtsDisplay.style.display = 'none';
    }
    
    async function resetGame() {
        try {
            const response = await fetch('/api/reset', {
                method: 'POST'
            });
            
            if (response.ok) {
                gameState = await response.json();
                updateBoard();
            }
        } catch (error) {
            console.error('Error resetting game:', error);
        }
        
        botThoughtsDisplay.style.display = 'none';
    }
    
    function toggleGameMode() {
        gameMode = gameMode === 'two-player' ? 'vs-bot' : 'two-player';
        modeToggleBtn.textContent = gameMode === 'two-player' ? 'Play vs Player' : 'Play vs Bot';
        resetGame();
    }
    
    // Initialize the game
    createBoard();
    updateBoard();
    
    // Event listeners
    resetBtn.addEventListener('click', resetGame);
    modeToggleBtn.addEventListener('click', toggleGameMode);
});