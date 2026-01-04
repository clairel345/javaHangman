import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.Timer;
import java.awt.geom.AffineTransform;

class ConfettiPanel extends JPanel implements ActionListener {

    private final int NUM_CONFETTI = 100;
    private final int CONFETTI_SIZE = 10;
    private final int SPEED = 2;

    private Random random = new Random();
    private Confetti[] confetti = new Confetti[NUM_CONFETTI];
    private javax.swing.Timer timer;

    public ConfettiPanel() {
        timer = new javax.swing.Timer(30, this);
        timer.start();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                initializeConfetti();
            }
        });
    }

    private void initializeConfetti() {
        if (getWidth() > 0 && getHeight() > 0) {
            for (int i = 0; i < NUM_CONFETTI; i++) {
                confetti[i] = new Confetti(random.nextInt(getWidth()), random.nextInt(getHeight()),
                        new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D

        for (Confetti c : confetti) {
            if(c != null) {
                g2d.setColor(c.color);

                // Rotate the rectangle
                AffineTransform originalTransform = g2d.getTransform(); // Save the original transform
                g2d.translate(c.x + CONFETTI_SIZE / 2, c.y + CONFETTI_SIZE / 2); // Move the rotation point to the center of the rectangle
                g2d.rotate(Math.toRadians(45)); // Rotate 45 degrees
                g2d.fillRect(-CONFETTI_SIZE / 2, -CONFETTI_SIZE / 2, CONFETTI_SIZE/2, CONFETTI_SIZE); // Draw the rectangle, now centered at 0,0
                g2d.setTransform(originalTransform); // Restore the original transform
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getWidth() > 0 && getHeight() > 0) {
            for (Confetti c : confetti) {
                if(c != null){
                    c.y += SPEED;
                    if (c.y > getHeight()) {
                        c.y = 0;
                        c.x = random.nextInt(getWidth());
                    }
                }
            }
        }
        repaint();
    }
}

class Confetti {
    int x, y;
    Color color;

    public Confetti(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}