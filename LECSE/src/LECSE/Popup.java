package LECSE;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Sigrid
 * @ This class is a popup to that shows that a file has been deleted
 * @ Used to refresh the main GUI when a file has been deleted and the okay or exit button has been clicked
 *
 */
public class Popup{
	public Popup(JFrame frame) throws FileNotFoundException, IOException {
		int input = JOptionPane.showOptionDialog(null, "File has been deleted.", "Message", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, null, null);

		if(input == 0 || input == -1)
		{
			MediaGUI mg = new MediaGUI(frame);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(mg);
			frame.revalidate();
		}
	}
}