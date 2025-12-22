#!/bin/bash
cd /app

# Build the assembly JAR
echo "Building the application..."
sbt assembly

# Check if the JAR was created
if [ -f "target/scala-2.12/tic-tac-toe-scala-assembly-1.0.jar" ]; then
    echo "JAR found. Starting the application..."
    exec java -cp target/scala-2.12/tic-tac-toe-scala-assembly-1.0.jar TicTacToeServer
else
    echo "JAR file not found. Attempting to run with sbt run instead..."
    exec sbt run
fi