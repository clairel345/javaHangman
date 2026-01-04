import javax.swing.*;
import java.awt.*;

public class HangmanGraphic extends JPanel {
	private int wrongGuesses = 0;

	public void setWrongGuesses(int guesses){
		this.wrongGuesses = guesses;
		repaint(); //triggers redraw
	}

	public int getWrongGuesses() {
        return wrongGuesses;
    }
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		//set background
		setBackground(Color.WHITE);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));

		//Draw gallows
		g2.drawLine(50, 250, 150, 250);   // base
        g2.drawLine(100, 50, 100, 250);   // vertical post
        g2.drawLine(100, 50, 200, 50);    // top beam
        g2.drawLine(200, 50, 200, 80);    // rope

        //Draw hangman parts based on wrong guesses
         if (wrongGuesses >= 1) g2.drawOval(180, 80, 40, 40);          // head
        if (wrongGuesses >= 2) g2.drawLine(200, 120, 200, 180);       // body
        if (wrongGuesses >= 3) g2.drawLine(200, 130, 170, 160);       // left arm
        if (wrongGuesses >= 4) g2.drawLine(200, 130, 230, 160);       // right arm
        if (wrongGuesses >= 5) g2.drawLine(200, 180, 180, 220);       // left leg
        if (wrongGuesses >= 6) g2.drawLine(200, 180, 220, 220);       // right leg

	}

}