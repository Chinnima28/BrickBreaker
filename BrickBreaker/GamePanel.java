import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private int paddleX = 300;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 15;
    private int ballX = 200;
    private int ballY = 200;
    private int ballSize = 20;
    private int ballDX = 2;
    private int ballDY = -2;
    private final int BRICK_WIDTH = 75;
    private final int BRICK_HEIGHT = 30;
    private final int ROWS = 5;
    private final int COLS = 10;
    private boolean[][] bricks = new boolean[ROWS][COLS];
    private int brickDX = 1; // Speed of brick movement
    private final int BRICK_MOVEMENT_LIMIT = 200; // Width before bricks reverse direction
    private int brickMovementCount = 0;
    private boolean gameOver = false; // Flag to check if game is over
    private boolean gameWon = false; // Flag to check if game is won

    public GamePanel() {
        timer = new Timer(10, this); // Timer for game updates
        timer.start();
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            movePaddle(-5);
                            break;
                        case KeyEvent.VK_RIGHT:
                            movePaddle(5);
                            break;
                    }
                }
            }
        });

        // Initialize bricks
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                bricks[i][j] = true; // All bricks are initially visible
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw paddle
        g2d.setColor(Color.BLUE);
        g2d.fillRect(paddleX, getHeight() - 50, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g2d.setColor(Color.RED);
        g2d.fillOval(ballX, ballY, ballSize, ballSize);

        // Draw bricks
        g2d.setColor(Color.GREEN);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (bricks[i][j]) {
                    g2d.fillRect(j * BRICK_WIDTH + brickMovementCount, i * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT);
                }
            }
        }

        // Display win or loss message
        if (gameWon) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("You Win!", getWidth() / 2 - 30, getHeight() / 2);
        } else if (gameOver) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Game Over", getWidth() / 2 - 40, getHeight() / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !gameWon) {
            updateBall();
            checkBrickCollision(); // Check for collisions with bricks
            updateBrickMovement(); // Update brick positions
            checkGameStatus(); // Check if the game is won or lost
            repaint(); // Repaint the panel to show updated game state
        }
    }

    private void movePaddle(int dx) {
        paddleX += dx;
        if (paddleX < 0) paddleX = 0;
        if (paddleX > getWidth() - PADDLE_WIDTH) paddleX = getWidth() - PADDLE_WIDTH;
    }

    private void updateBall() {
        ballX += ballDX;
        ballY += ballDY;

        // Bounce off walls
        if (ballX <= 0 || ballX >= getWidth() - ballSize) ballDX = -ballDX;
        if (ballY <= 0) ballDY = -ballDY;

        // Bounce off paddle
        if (ballY >= getHeight() - 50 - ballSize && ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
            ballDY = -ballDY;
        }

        // Check for ball falling below the paddle
        if (ballY >= getHeight()) {
            gameOver = true; // Set game over flag
        }
    }

    private void checkBrickCollision() {
        int ballRow = ballY / BRICK_HEIGHT;
        int ballCol = (ballX - brickMovementCount) / BRICK_WIDTH;

        // Ensure ball is within the bounds of a brick
        if (ballRow >= 0 && ballRow < ROWS && ballCol >= 0 && ballCol < COLS) {
            if (bricks[ballRow][ballCol]) {
                bricks[ballRow][ballCol] = false; // Remove the brick

                // Check for collision on all four sides of the ball
                if (ballY + ballSize > ballRow * BRICK_HEIGHT &&
                    ballY < (ballRow + 1) * BRICK_HEIGHT &&
                    ballX + ballSize > ballCol * BRICK_WIDTH + brickMovementCount &&
                    ballX < (ballCol + 1) * BRICK_WIDTH + brickMovementCount) {
                    ballDY = -ballDY; // Bounce ball off the brick
                }
            }
        }
    }

    private void updateBrickMovement() {
        brickMovementCount += brickDX;

        // Reverse direction if the bricks reach the edge of the panel
        if (brickMovementCount > BRICK_MOVEMENT_LIMIT || brickMovementCount < 0) {
            brickDX = -brickDX;
        }
    }

    private void checkGameStatus() {
        // Check if all bricks are destroyed
        boolean allBricksDestroyed = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (bricks[i][j]) {
                    allBricksDestroyed = false;
                    break;
                }
            }
            if (!allBricksDestroyed) break;
        }
        if (allBricksDestroyed) {
            gameWon = true; // Set game won flag
        }
    }
}
