package LECSE;

import java.awt.Component;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Jordan
 * @version 1.0
 * @created 10-okt-2017 18:06:10

 */
public class AudioConverter

{
	public AudioConverter()
	{

	}

	public void finalize() throws Throwable
	{

	}

	/**
	 * Opens a window for the user to select the .wav file they wish to upload
	 * @return The name of the file chosen by the user, otherwise null
	 */
	public String getAudioFile()
	{
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "WAV files", "wav");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);

	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	    	return chooser.getSelectedFile().getAbsolutePath();
	    }
	    else
	    {
	    	JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Unable to upload file");
	    	return null;
	    }
		//return null;
	}
}
