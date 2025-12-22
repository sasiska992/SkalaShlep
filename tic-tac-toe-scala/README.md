# Scala Tic Tac Toe with Prolog AI

A web-based Tic Tac Toe game implemented in Scala using the Scalatra framework with an intelligent opponent powered by Prolog.

## Features

- Web-based Tic Tac Toe game interface
- Two-player mode (taking turns on one computer)
- Single-player mode against a smart bot with Prolog AI
- Lightweight Scala web server
- Prolog integration for advanced AI implementation
- Optimized resource consumption for smooth operation on systems with limited resources

## Technologies

- **Backend**: Scala with Scalatra framework
- **AI**: SWI-Prolog with minimax algorithm for optimal moves
- **Frontend**: HTML, CSS, JavaScript
- **Build Tool**: sbt (Scala Build Tool)
- **Containerization**: Docker

## Installation and Running

### Using Docker (Recommended)

The easiest and most reliable way to run the application:

```bash
# Build the Docker image
docker build -t tic-tac-toe-scala .

# Run the application
docker run -p 8080:8080 tic-tac-toe-scala
```

Then open your browser at: `http://localhost:8080`

### Local Running (Requires Scala, sbt, and SWI-Prolog installed)

```bash
# Install dependencies
sbt compile

# Run the application
sbt run
```

Open your browser at: `http://localhost:8080`

## Architecture

- **Backend**: Scala/Scalatra handles HTTP requests and game logic
- **AI**: SWI-Prolog implements the minimax algorithm for optimal bot moves
- **Frontend**: HTML/CSS/JavaScript for interactive interface
- **Communication**: REST API for client-server interaction

## Game Modes

1. **Two Players**: Two people take turns on the same computer
2. **Against Bot**: Play against an AI opponent using Prolog (bot makes optimal moves)

## Project Structure

```
tic-tac-toe-scala/
├── build.sbt                 # sbt configuration
├── Dockerfile               # Docker image definition
├── docker-compose.yml       # Docker Compose configuration
├── startup.sh              # Application startup script
├── README.md               # Documentation
├── src/
│   └── main/
│       ├── scala/
│       │   ├── TicTacToeServlet.scala  # Main server code
│       │   └── TicTacToeServer.scala   # Server startup
│       ├── resources/
│       │   ├── tictactoe.pl            # Prolog AI code
│       │   ├── index.html              # Main page
│       │   ├── css/style.css          # Styling
│       │   └── js/app.js              # Client-side JavaScript
│       └── webapp/                    # Alternative static files
└── project/
    └── plugins.sbt          # sbt plugins
```

## AI Algorithm

The bot uses the minimax algorithm implemented in Prolog to determine optimal moves. This provides a challenging and interesting opponent that:
- Attempts to win when possible
- Blocks opponent's winning moves
- Makes optimal moves in every situation

## Security and Efficiency

- Application designed for efficient operation even on systems with limited resources
- Docker usage provides runtime environment isolation
- Simple architecture reduces probability of errors

## Possible Issues and Solutions

1. **Resource Issues**: If your system gets overloaded, use the Docker version with resource limits
2. **Port Issues**: If port 8080 is busy, change it when running: `docker run -p NEW_PORT:8080 ...`
3. **AI Performance Issues**: Bot moves might take 1-2 seconds, especially on early moves

## Author

Created as a university project for functional programming.

## License

MIT License - see the `LICENSE` file for details.

---

# Игра "Крестики-нолики" на Scala с ИИ на Prolog

Веб-приложение с игрой "Крестики-нолики", реализованное на Scala с использованием фреймворка Scalatra и интеллектуальным противником на Prolog.

## Особенности

- Веб-интерфейс игры "Крестики-нолики"
- Режим игры "два игрока" (по очереди на одном компьютере)
- Режим игры против умного бота с ИИ на Prolog
- Легковесный веб-сервер на Scala
- Интеграция с Prolog для реализации продвинутого ИИ
- Оптимизированное потребление ресурсов для легкой работы на слабых системах

## Технологии

- **Backend**: Scala с фреймворком Scalatra
- **AI**: SWI-Prolog с алгоритмом минимакс для оптимальных ходов
- **Frontend**: HTML, CSS, JavaScript
- **Сборка**: sbt (Scala Build Tool)
- **Контейнеризация**: Docker

## Установка и запуск

### С использованием Docker (рекомендуется)

Самый простой и надежный способ запуска приложения:

```bash
# Сборка Docker-образа
docker build -t tic-tac-toe-scala .

# Запуск приложения
docker run -p 8080:8080 tic-tac-toe-scala
```

После этого откройте в браузере: `http://localhost:8080`

### Локальный запуск (требует установленные Scala, sbt и SWI-Prolog)

```bash
# Установка зависимостей
sbt compile

# Запуск приложения
sbt run
```

Откройте в браузере: `http://localhost:8080`

## Архитектура

- **Backend**: Scala/Scalatra обрабатывает HTTP-запросы и логику игры
- **AI**: SWI-Prolog реализует алгоритм минимакс для определения оптимальных ходов бота
- **Frontend**: HTML/CSS/JavaScript для интерактивного интерфейса
- **Коммуникация**: REST API для взаимодействия между клиентом и сервером

## Игровые режимы

1. **Два игрока**: два человека играют по очереди на одном компьютере
2. **Против бота**: игра против ИИ с использованием Prolog (бот делает оптимальные ходы)

## Структура проекта

```
tic-tac-toe-scala/
├── build.sbt                 # Конфигурация sbt
├── Dockerfile               # Определение Docker-образа
├── docker-compose.yml       # Docker Compose конфигурация
├── startup.sh              # Скрипт запуска приложения
├── README.md               # Документация
├── src/
│   └── main/
│       ├── scala/
│       │   ├── TicTacToeServlet.scala  # Основной серверный код
│       │   └── TicTacToeServer.scala   # Запуск сервера
│       ├── resources/
│       │   ├── tictactoe.pl            # Код ИИ на Prolog
│       │   ├── index.html              # Главная страница
│       │   ├── css/style.css          # Стили
│       │   └── js/app.js              # Клиентский JavaScript
│       └── webapp/                    # Альтернативные статические файлы
└── project/
    └── plugins.sbt          # Плагины sbt
```

## Алгоритм ИИ

Бот использует алгоритм минимакс, реализованный на Prolog, для определения оптимальных ходов. Это обеспечивает сложного и интересного противника, который:
- Старается выиграть при возможности
- Блокирует выигрышные ходы противника
- Делает оптимальные ходы в каждой ситуации

## Безопасность и эффективность

- Приложение спроектировано для эффективной работы даже на системах с ограниченными ресурсами
- Использование Docker позволяет изолировать среду выполнения
- Простая архитектура снижает вероятность ошибок

## Возможные проблемы и решения

1. **Проблема с ресурсами**: Если система перегружается, используйте Docker-версию с ограничением ресурсов
2. **Проблемы с портами**: Если порт 8080 занят, измените его при запуске: `docker run -p НОВЫЙ_ПОРТ:8080 ...`
3. **Проблемы с производительностью ИИ**: Ходы бота могут занимать 1-2 секунды, особенно на первых ходах

## Автор

Создано как университетский проект по функциональному программированию.

## Лицензия

MIT License - см. файл `LICENSE` для подробностей.