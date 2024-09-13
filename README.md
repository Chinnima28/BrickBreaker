# Brick Breaker Game

A simple Brick Breaker game implemented in Java using `JFrame`, `JPanel`, and Java Graphics. The game features a paddle, a ball, and a grid of bricks that the player must break by bouncing the ball off the paddle.

## Features

- **Interactive Gameplay**: Move the paddle using the left and right arrow keys to keep the ball in play and break bricks.
- **Timer-Based Updates**: The game state updates every 10 milliseconds to ensure smooth gameplay.
- **Brick Grid**: A grid of bricks that disappear when hit by the ball.
- **Game Over State**: The game ends when the ball falls below the paddle.

## Screenshots

Here’s a snapshot of the game in action:

![Game Snapshot](https://github.com/Chinnima28/BrickBreaker/blob/b860ce5b9515f30861c5be67a23b0077ae1d01b8/Brickbreaker%20output.png)

## How to Run

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Chinnima28/BrickBreaker.git
   cd BrickBreaker
   ```
2. **Complie the Code**

   ```bash
   javac BrickBreaker.java GamePanel.java
   ```
3. **Run the Game**
   ```bash
   java BrickBreaker
   ```
4. **Control the Paddle**
   Use the left and right arrow keys to move the paddle and keep the ball in play.

## Code Structure
- **BrickBreaker.java:** Contains the main method to start the game.
- **GamePanel.java:** Contains the game logic, rendering, and user input handling.
