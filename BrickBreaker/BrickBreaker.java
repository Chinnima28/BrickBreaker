import javax.swing.JFrame;

public class BrickBreaker extends JFrame {
    public BrickBreaker() {
        setTitle("Brick Breaker Game");
        setSize(800, 600); // Width and height of the game window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel()); // Add the game panel to the frame
    }

    public static void main(String[] args) {
        // Run the game
        BrickBreaker game = new BrickBreaker();
        game.setVisible(true);
    }
}
