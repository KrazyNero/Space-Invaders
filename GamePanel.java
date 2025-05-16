import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    Player player;
    ArrayList<Bullet> bullets;
    ArrayList<Enemy> enemies;
    ArrayList<EnemyBullet> enemyBullets;

    boolean left, right, spacePressed;
    boolean gameOver = false;
    boolean win = false;
    Random rand = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(375, 500);
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyBullets = new ArrayList<>();

        String[] colors = {
            "alien-white.png",
            "alien-cyan.png",
            "alien-magenta.png",
            "alien-yellow.png"
        };

        for (int row = 0; row < 4; row++) {
            String img = colors[row % colors.length];
            for (int col = 0; col < 6; col++) {
                enemies.add(new Enemy(80 + col * 100, 50 + row * 60, img));
            }
        }

        timer = new Timer(15, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver || win) return;

        if (left) player.move(-5);
        if (right) player.move(5);

        if (spacePressed) {
            if (bullets.isEmpty() || bullets.get(bullets.size() - 1).y < player.y - 40) {
                bullets.add(new Bullet(player.x + player.width / 2 - 2, player.y));
            }
        }

        // Move bullets
        bullets.removeIf(b -> {
            b.move();
            return b.y < 0;
        });

        // Move enemies + randomly shoot
        for (Enemy enemy : enemies) {
            enemy.move();
            if (rand.nextDouble() < 0.002) {
                enemyBullets.add(new EnemyBullet(enemy.x + enemy.width / 2, enemy.y + enemy.height));
            }
        }

        // Move enemy bullets
        Iterator<EnemyBullet> ebIter = enemyBullets.iterator();
        while (ebIter.hasNext()) {
            EnemyBullet eb = ebIter.next();
            eb.move();
            if (eb.y > 600) {
                ebIter.remove();
                continue;
            }

            Rectangle playerBounds = new Rectangle(player.x, player.y, player.width, player.height);
            if (playerBounds.intersects(eb.getBounds())) {
                gameOver = true;
                timer.stop();
            }
        }

        // Bullet-enemy collisions
        Iterator<Enemy> eIter = enemies.iterator();
        while (eIter.hasNext()) {
            Enemy enemy = eIter.next();
            for (Iterator<Bullet> bIter = bullets.iterator(); bIter.hasNext(); ) {
                Bullet b = bIter.next();
                if (enemy.getBounds().intersects(b.getBounds())) {
                    bIter.remove();
                    if (enemy.isHit()) {
                        eIter.remove();
                    }
                    break;
                }
            }
        }

        if (enemies.isEmpty()) {
            win = true;
            timer.stop();
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);

        for (Bullet b : bullets) {
            b.draw(g);
        }

        for (Enemy e : enemies) {
            e.draw(g);
        }

        for (EnemyBullet eb : enemyBullets) {
            eb.draw(g);
        }

        if (win) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("You Win!", 320, 300);
        }

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over!", 290, 300);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_R -> {
                if (gameOver || win) {
                    resetGame();
                }
            }
        }
}

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    public void resetGame() {
    player = new Player(375, 500);
    bullets.clear();
    enemyBullets.clear();
    enemies.clear();
    gameOver = false;
    win = false;

    String[] colors = {
        "alien-white.png",
        "alien-cyan.png",
        "alien-magenta.png",
        "alien-yellow.png"
    };

    for (int row = 0; row < 4; row++) {
        String img = colors[row % colors.length];
        for (int col = 0; col < 6; col++) {
            enemies.add(new Enemy(80 + col * 100, 50 + row * 60, img));
        }
    }

    timer.start();
}
}