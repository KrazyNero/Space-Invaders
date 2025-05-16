import javax.swing.JFrame;

public class SpaceInvaders {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}