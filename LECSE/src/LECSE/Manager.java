import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * @author Sigrid
 * @This class contains the main frame used to display the ProfileGUI and the MediaGUI
 */
public class Manager {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @This is the main method that starts the whole program
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		JFrame frame = new JFrame();
		frame.setSize(950, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout()); 
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.getContentPane().setBackground(Color.white);
		ProfileGUI pg = new ProfileGUI(frame);
		frame.add(pg);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}