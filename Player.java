import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    int x, y, width = 60, height = 40;
    BufferedImage image;
    int health = 10;


    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(getClass().getResource("ship.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading ship image: " + e.getMessage());
        }
    }

    public void move(int dx) {
        x += dx;
        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }

        g.setColor(Color.GREEN);
        g.fillRect(x + 10, y - 10, health * 5, 5);
    }

    public boolean isHit() {
        health--;
        return health <= 0;
    }
}
