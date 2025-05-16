import java.awt.*;

public class EnemyBullet {
    int x, y, width = 4, height = 10;
    int speed = 4;

    public EnemyBullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}