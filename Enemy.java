import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Enemy {
    int x, y, width = 50, height = 40;
    int dx = 2;
    int health = 2;
    BufferedImage image;
    Random rand = new Random();

    public Enemy(int x, int y, String imageName) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(getClass().getResource(imageName));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not load enemy image: " + imageName);
        }
    }

    public void move() {
        x += dx;
        if (x < 0 || x > 760) {
            dx = -dx;
            y += 20;
        }
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }

        // Draw health bar
        g.setColor(Color.GREEN);
        g.fillRect(x, y - 5, width * health / 2, 5);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isHit() {
        health--;
        return health <= 0;
    }

    public boolean shouldShoot() {
        return rand.nextDouble() < 0.002; // low chance per frame
    }
}