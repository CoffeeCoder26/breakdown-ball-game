import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int playScore = 0;

	private int totalBricks = 22;

	private Timer timer;
	private int timeDelay = 8;

	private int playerX = 310;

	private int ballXPosition = 120;
	private int ballYPosition = 350;
	private int ballXDirection = -1;
	private int ballYDirection = -2;

	private MapGenerator mapGenerator;

	public GamePlay() {
		// TODO Auto-generated constructor stub
		mapGenerator = new MapGenerator(3, 7);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(timeDelay, this);
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(1, 1, 690, 590);

		mapGenerator.draw((Graphics2D) g);

		// Paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 8);

		// Score
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + playScore, 590, 30);

		// Ball
		g.setColor(Color.yellow);
		g.fillOval(ballXPosition, ballYPosition, 20, 20);

		if (totalBricks <= 0) {
			play = false;
			ballXDirection = 0;
			ballYDirection = 0;
			g.setColor(Color.red);

			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("You Win" + playScore, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Press Enter To Start Again ", 230, 350);
		}

		if (ballXPosition > 570) {
			play = false;
			ballXDirection = 0;
			ballYDirection = 0;
			g.setColor(Color.red);

			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Game Over" + playScore, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Press Enter To Start Again ", 230, 350);

		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		timer.start();

		if (play) {

			if (new Rectangle(ballXPosition, ballYPosition, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYDirection = -ballYDirection;
			}

			A: for (int i = 0; i < mapGenerator.map.length; i++) {
				for (int j = 0; j < mapGenerator.map[0].length; j++) {
					if (mapGenerator.map[i][j] > 0) {
						int brickX = j * mapGenerator.brickWidth + 80;
						int brickY = j * mapGenerator.brickWidth + 50;
						int brickWidth = mapGenerator.brickWidth;
						int brickHeight = mapGenerator.brickHeight;

						Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRectangle = new Rectangle(ballXPosition, ballYPosition, 20, 20);

						Rectangle brickRectangle = rectangle;

						if (ballRectangle.intersects(brickRectangle)) {
							mapGenerator.setBrickValue(0, i, j);
							totalBricks--;
							playScore += 5;

							if (ballXPosition + 19 <= brickRectangle.x || ballYPosition + 1 >= brickRectangle.width) {
								ballXDirection = -ballXDirection;
							} else {
								ballYDirection = -ballYDirection;
							}
							break A;
						}

					}

				}
			}

			ballXPosition += ballXDirection;
			ballYPosition += ballYDirection;

			if (ballXDirection < 0) {
				ballXDirection = -ballXDirection;
			}

			if (ballYDirection < 0) {
				ballYDirection = -ballYDirection;
			}

			if (ballXPosition > 650) {
				ballXDirection = -ballXDirection;
			}

		}

		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballXPosition = 120;
				ballYPosition = 350;
				ballXDirection = -1;
				ballYDirection = -2;
				playerX = 310;
				playScore = 0;
				totalBricks = 22;
				mapGenerator = new MapGenerator(3, 7);

				repaint();
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
