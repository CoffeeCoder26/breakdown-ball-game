import java.awt.Color;

import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		//Create The Frame
		
				JFrame jFrame = new JFrame();
				GamePlay gamePlay = new GamePlay();
				jFrame.setTitle("Breakdown Ball Game");
				jFrame.setBounds(10,10,700,600);
				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jFrame.setVisible(true);
				jFrame.setResizable(false);
				jFrame.setBackground(Color.BLACK);
				
				jFrame.add(gamePlay);

	}

}
