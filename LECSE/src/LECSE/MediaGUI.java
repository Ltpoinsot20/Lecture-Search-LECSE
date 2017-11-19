package LECSE;

/**
 * @author Sigrid, Alex, Jordan
 * @version 1.0
 * @created 10-okt-2017 18:06:10
 */


import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;


public class MediaGUI extends JPanel
{
	public JTextField textField;
	public JTextArea textArea;
	
	static MyHighlightPainter myHighlightPainter = new MyHighlightPainter(Color.yellow);

	public MediaGUI()
	{

	}

	public void finalize() throws Throwable
	{

	}
	
	public void helpPage()
	{

	}

	
	public void highlightWord(int index)
	{

	}

	
	// BEGIN AUDIO METHODS SECTION

		//find sound clip
		
		
		
		// load the sound into a clip
		//DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
		
		//Clip clip = (Clip) AudioSystem.getLine(info);
		
		/**
		 * Plays audio in the 
		 * @param fileName Audio File name
		 * @param clip Audio clip
		 */
		public void playAudio(String fileName, Clip clip) // Removed static from everything, added Clip input
		{
			//choose the sound (moved into method)
			File soundFile = new File(fileName);
			AudioInputStream sound = null;
			
			try
			{
				sound = AudioSystem.getAudioInputStream(soundFile);
			}
			catch (UnsupportedAudioFileException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try
			{
				clip.open(sound);
			}
			catch (LineUnavailableException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// play sound
			clip.start();
		}
		
		/**
		 * @param fileName
		 */
		public void stopAudio(String fileName, Clip clip)
		{
			//exit the player when sound has stopped
			clip.addLineListener(new LineListener()
			{
				public void update(LineEvent event)
				{
					if (event.getType() == LineEvent.Type.STOP)
					{
						event.getLine().close();
						//System.exit(0); Closes the entire program
					}
				}
			});
		}
		
		/**
		 * Pauses/Starts playing audio
		 * @param Audio File
		 * @param clip Audio from file put into Clip
		 */
		public void pauseAudio(String fileName, Clip clip)
		{
			// pause audio when selected - do not exit out of player
			clip.addLineListener(new LineListener()
			{
				public void update(LineEvent event)
				{
					if (event.getType() == LineEvent.Type.STOP)
					{
						try
						{
							event.getLine().wait();
						}
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						clip.stop();
					}
					if (event.getType() == LineEvent.Type.START) // Is this if supposed to be inside previous if?
					{
						event.getLine().notify();
						clip.start();
					}
					
				}
			});
		}
	// END AUDIO METHODS SECTION

	public void profilePage()
	{

	}

	/**
	 * Reads the text from a text file and returns the words as a string
	 * @param fileName The name of the file to be read from
	 * @return Returns the words from the file as a string
	 * @throws IOException
	 */
	public String readText(String fileName) throws IOException
	{
        // This will reference one line at a time
        String line = null;
        String word = "";
        String newLine = "\n";
        
       try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null)
            {
                word += line + newLine;
            }   
         
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
       {
        	System.out.println(
               "Unable to open file '" + 
                fileName + "'");                
       }
      catch(IOException ex) {
           System.out.println(
               "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            //ex.printStackTrace();
        }
        return (word);
    }

    /**
     * Displays translated text
     * @param fileName Text File containing words to be displayed
     * @throws IOException
     */
	public void showText(String fileName) throws IOException
    {
		// Frame
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setTitle("Text");
    	frame.setLocation(500,400);
    	frame.setSize(500,500);

    	// Panel
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	c.gridx = 0;
    	c.gridy = 0;
    	c.insets = new Insets(2,10,10,10);

    	// Label
    	JLabel label = new JLabel("Text Translation");
    	panel.add(label,c);
    	

    	// Text Area
    	JTextArea  textarea = new JTextArea(readText(fileName));
    	textarea.setSize(150, 150);
    	panel.add(textarea);
    	textarea.setEditable(true);
    	textarea.setLineWrap(true);
    	      
    	// Scroll Bar for Text Area
    	JScrollPane scroll = new JScrollPane (textarea);
    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    	frame.add(scroll);
    	
    	// Text Field
    	JTextField field = new JTextField();
    	
    	field.setPreferredSize(new Dimension(150, 20));
    	panel.add(field);
    	field.addActionListener(e->
    	{
    		if(field.getText().length() != 0)
    		{
    			String keyword = field.getText();
    			highlight(textarea, keyword);
    		}
    		
    
    	});

    	// Save Button
    	JButton button = new JButton("Save");
    	button.addActionListener(e->
    	{
    		String words = textarea.getText();
    		BufferedWriter bw = null;
    		FileWriter fw = null;

    		try
    		{
    			String content = words;
    			fw = new FileWriter("Test.txt");
    			bw = new BufferedWriter(fw);
    			bw.write(content);
    			System.out.println("Done");
    		}
    	
    		catch (IOException er)
    		{
    			er.printStackTrace();
    		}
    	
    		try
    		{
    			if (bw != null)
    			{
    				bw.close();
    			}
    	    
    			if (fw != null)
    			{
    				fw.close();
    			}
    		}
    	
    		catch (IOException ex)
    		{
    			ex.printStackTrace();
    		}

    	    	
    	});
    	c.gridx = 0;
    	c.gridy = 1;
    	panel.add(button,c);

    	// Display
    	frame.getContentPane().add(panel, BorderLayout.NORTH);
    	frame.setVisible(true);
    }

	/**
	 * Highlights given word
	 * @param Component to highlight in
	 * @param String to highlight
	 */
	public void highlight(JTextComponent textComp, String pattern)
	{
		// First remove all old highlights
		removeHighlights(textComp);

		try
		{
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			// Search for pattern
			while ((pos = text.indexOf(pattern, pos)) >= 0)
			{
				// Create highlighter using private painter and apply around pattern
				hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}
		}
		catch (BadLocationException e)
		{
			
		}
	}
	
	/**
	 * Removes highlight from string
	 * @param Component to remove highlight from
	 */
	public void removeHighlights(JTextComponent textComp)
	{
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++)
		{
			if (hilites[i].getPainter() instanceof MyHighlightPainter)
			{
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	public void stopAudio()
	{

	}

	
	/**
	 * Uploads an audio file to the system.
	 * @param fileName Name of the file to be uploaded.
	 * @return The clip is the audio from the file
	 */
	public Clip uploadAudio(String fileName)
	{
		Clip in = null;

	    try
	    {
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
	        in = AudioSystem.getClip();
	        in.open( audioIn );
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }

	    return in;
	}
}