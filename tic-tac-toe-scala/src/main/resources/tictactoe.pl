% Tic Tac Toe rules in Prolog
% Representation: x for X, o for O, e for empty

% Check if a player has won
wins(Player, [Player,Player,Player,_,_,_,_,_,_]).
wins(Player, [_,_,_,Player,Player,Player,_,_,_]).
wins(Player, [_,_,_,_,_,_,Player,Player,Player]).
wins(Player, [Player,_,_,Player,_,_,Player,_,_]).
wins(Player, [_,Player,_,_,Player,_,_,Player,_]).
wins(Player, [_,_,Player,_,_,Player,_,_,Player]).
wins(Player, [Player,_,_,_,Player,_,_,_,Player]).
wins(Player, [_,_,Player,_,Player,_,Player,_,_]).

% Check if the board is full
board_full(Board) :-
    \+ member(e, Board).

% Check if the game is over
game_over(Board, Winner) :-
    wins(x, Board),
    Winner = x, !.
game_over(Board, Winner) :-
    wins(o, Board),
    Winner = o, !.
game_over(Board, draw) :-
    board_full(Board), !.

% Get possible moves
possible_moves(Board, Moves) :-
    findall(Pos, (nth0(Pos, Board, e)), Moves).

% Evaluate a position (simple heuristic)
evaluate_position(Board, Score) :-
    wins(x, Board), !,
    Score is -10.  % Human wins (bad for AI)
evaluate_position(Board, Score) :-
    wins(o, Board), !,
    Score is 10.   % AI wins (good for AI)
evaluate_position(Board, Score) :-
    board_full(Board), !,
    Score is 0.    % Draw
evaluate_position(_, 0).  % Game not over

% Minimax algorithm
minimax(Board, Depth, IsMaximizing, Score) :-
    game_over(Board, Winner),
    (   Winner = x -> Score is -10 - Depth
    ;   Winner = o -> Score is 10 + Depth
    ;   Score is 0
    ), !.
    
minimax(Board, Depth, true, BestScore) :-  % AI's turn (maximizing)
    possible_moves(Board, Moves),
    Moves \= [], !,
    minimax_max_loop(Board, Moves, Depth, -1000, BestScore).
    
minimax(Board, Depth, false, BestScore) :-  % Human's turn (minimizing)
    possible_moves(Board, Moves),
    Moves \= [], !,
    minimax_min_loop(Board, Moves, Depth, 1000, BestScore).
    
minimax(_, _, _, 0).  % Fallback

minimax_max_loop(_, [], _, CurrentBest, CurrentBest).
minimax_max_loop(Board, [Move|Rest], Depth, CurrentBest, BestScore) :-
    nth0(Move, Board, e, UpdatedBoard, o),
    NewDepth is Depth - 1,
    minimax(UpdatedBoard, NewDepth, false, MoveScore),
    NewBest is max(CurrentBest, MoveScore),
    minimax_max_loop(Board, Rest, Depth, NewBest, BestScore).

minimax_min_loop(_, [], _, CurrentBest, CurrentBest).
minimax_min_loop(Board, [Move|Rest], Depth, CurrentBest, BestScore) :-
    nth0(Move, Board, e, UpdatedBoard, x),
    NewDepth is Depth - 1,
    minimax(UpdatedBoard, NewDepth, true, MoveScore),
    NewBest is min(CurrentBest, MoveScore),
    minimax_min_loop(Board, Rest, Depth, NewBest, BestScore).

% Find best move for AI
best_move(Board, BestMove) :-
    possible_moves(Board, Moves),
    Moves \= [], !,
    best_move_loop(Board, Moves, -1000, _, BestMove).
    
best_move_loop(_, [], _, _, 0).  % Fallback if no valid move
best_move_loop(Board, [Move|Rest], BestValue, BestMove, FinalBestMove) :-
    nth0(Move, Board, e, UpdatedBoard, o),
    minimax(UpdatedBoard, 0, false, MoveValue),
    (   MoveValue > BestValue ->
        NewBestValue = MoveValue,
        NewBestMove = Move
    ;   NewBestValue = BestValue,
        NewBestMove = BestMove
    ),
    best_move_loop(Board, Rest, NewBestValue, NewBestMove, FinalBestMove).

% Convert 2D board to 1D list for processing
board_to_list([[A,B,C],[D,E,F],[G,H,I]], [A,B,C,D,E,F,G,H,I]).
list_to_board([A,B,C,D,E,F,G,H,I], [[A,B,C],[D,E,F],[G,H,I]]).

% Main predicate to get the best move
get_best_move_2d(CurrentBoard2D, Row, Col) :-
    board_to_list(CurrentBoard2D, BoardList),
    best_move(BoardList, BestPos),
    Row is BestPos // 3,
    Col is BestPos mod 3.