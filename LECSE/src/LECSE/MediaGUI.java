package LECSE;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * @author Sigrid, Alex, Jordan, Lucas
 * @ This class is a JPanel that contains all the components of the media view
 */
public class MediaGUI extends JPanel
{
	public JTextField textField;
	public JTextArea textArea;
	String audioPath;
	String audioName = "";
	String textPath;
	public JLabel label = new JLabel("Translated Text:");
	int turn = 0;
	static MyHighlightPainter myHighlightPainter = new MyHighlightPainter(Color.yellow);
	AudioPlayer player;

	/**
	 * @param frame is the main frame that is used to display the MediaGUI JPanel
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException 
	 */
	public MediaGUI(JFrame frame) throws FileNotFoundException, IOException{
		Database db = new Database();		
		db.loadUserInfo();
		db.loadCourseInstructor();
		db.loadLectures();

		String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "LECSE";
		File customDir = new File(path);
		customDir.mkdir();
		audioPath = path + File.separator + "Audio Files";
		File audioFolder = new File(audioPath);
		audioFolder.mkdir();
		textPath = path + File.separator + "Text Files";
		File textFolder = new File(textPath);
		textFolder.mkdir();

		setLayout(new GridBagLayout());
		setBackground(Color.white);
		GridBagConstraints c = new GridBagConstraints();

		//Create a button to switch to ProfileGUI
		JButton changePanel = new JButton("Profile");
		changePanel.setPreferredSize(new Dimension(100,30));
		changePanel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		changePanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Code to switch panel here
				try {
					ProfileGUI pg = new ProfileGUI(frame);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(pg);
					frame.revalidate();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		c.insets = new Insets(10,0,0,100);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(changePanel,c);

		// Text Area
		JTextArea  textarea = new JTextArea();
		textarea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		textarea.setSize(500, 500);
		add(textarea);
		textarea.setEditable(true);
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);

		//Create the root node
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Lectures");
		//Pass the database and the root node to the createNodes method

		//Create a tree that allows one selection at a time.
		JTree tree = new JTree(top);
		tree.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tree.getSelectionModel().setSelectionMode
		(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Create the nodes
		String[][] ci = db.getCourseAndINstructor();

		if(ci[0][0] != null) {
			for(int i = 0; i < ci.length; i++) {
				if(ci[i][1] != null) {
					//Sort by course
					if(db.getSortSelection().equals("Course")) {
						DefaultMutableTreeNode course = new DefaultMutableTreeNode(ci[i][0]);
						top.add(course);

						ArrayList<Lecture> lectures = db.getCourseArrayList(ci[i][0]);
						for(int k = 0; k < lectures.size(); k++) {
							DefaultMutableTreeNode lecture = new DefaultMutableTreeNode(lectures.get(k).getFileName());
							course.add(lecture);			
						}
						//Sort by instructor
					}else if(db.getSortSelection().equals("Instructor")) {
						DefaultMutableTreeNode instructor = new DefaultMutableTreeNode(ci[i][1]);
						top.add(instructor);

						ArrayList<Lecture> lectures = db.getInstructorArrayList(ci[i][1]);
						for(int k = 0; k < lectures.size(); k++) {
							DefaultMutableTreeNode lecture = new DefaultMutableTreeNode(lectures.get(k).getFileName());
							instructor.add(lecture);			
						}
					}
				}
			}
		}

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			//add an event to the nodes
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
						tree.getLastSelectedPathComponent();

				if (node == null) return;


				if (node.isLeaf()) {
					String namename = node.getUserObject().toString();
					String fileName = namename + ".txt";
					//label.setText("Selected File: " + namename);
					ArrayList<Lecture> lec = db.getLectures();
					for(int i = 0; i < lec.size(); i++) {
						if(namename.equals(lec.get(i).getFileName())) {
							audioName = lec.get(i).getAudioName();
						}
					}

					//Setting the text area to display the file!!
					try {
						textarea.setText(readText(fileName, textPath, frame));
						String fullAudioName = "";
						fullAudioName = path + File.separator + "Audio Files" + File.separator + audioName;
						setAudioPath(fullAudioName);
						player = new AudioPlayer(new File(getAudioPath()));

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} 
			}
		});

		//Add a delete feature on a right click
		MouseAdapter ml = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (SwingUtilities.isRightMouseButton(e)) {
					String label = "Delete";
					JPopupMenu popup = new JPopupMenu();
					JMenuItem mi = new JMenuItem(label);
					mi.addActionListener(a ->
					{
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)
								tree.getLastSelectedPathComponent();
						String fileName = node.getUserObject().toString();
						try {
							db.removeLecture(fileName);
							Popup pu = new Popup(frame);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
					popup.add(mi);
					popup.show(tree, e.getX(), e.getY());

				}
			}

		};
		tree.addMouseListener(ml);

		//Create the scroll pane and add the tree to it. 
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setSize(new Dimension(800,800));
		treeView.setBorder(null);

		//Listen for when the selection changes.'
		tree.addTreeExpansionListener(new TreeExpansionListener() {
			@Override
			public void treeCollapsed(TreeExpansionEvent arg0) {
				// TODO Auto-generated method stub
				treeView.setSize(tree.getWidth(),tree.getHeight());
			}

			@Override
			public void treeExpanded(TreeExpansionEvent arg0) {
				// TODO Auto-generated method stub
				treeView.setSize(150,tree.getHeight());
			}
		});


		c.insets = new Insets(10,0,0,100);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(treeView,c);


		// Scroll Bar for Text Area
		JScrollPane scroll = new JScrollPane (textarea);
		scroll.setPreferredSize(new Dimension(500,650));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// Label
		//JLabel label = new JLabel("Selected File:");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,10);
		add(label,c);

		JLabel label2 = new JLabel("Search:");
		label2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,10);
		add(label2,c);

		// Text Field
		JTextField field = new JTextField();

		field.setPreferredSize(new Dimension(150, 20));
		c.gridx = 3;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(field,c);
		field.addActionListener(e->
		{
			if(field.getText().length() != 0)
			{
				String keyword = field.getText();
				highlight(textarea, keyword);
			}
		});



		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		add(scroll, c);

		// Save Button
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		saveButton.setPreferredSize(new Dimension(120,30));
		saveButton.addActionListener(e->
		{
			String words = textarea.getText();
			BufferedWriter bw = null;
			FileWriter fw = null;

			try
			{				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
						tree.getLastSelectedPathComponent();
				if(node != null) {
					String fileName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "LECSE"+ File.separator + "Text Files" + File.separator + node.getUserObject().toString() + ".txt";
					String content = words;
					fw = new FileWriter(fileName);
					bw = new BufferedWriter(fw);
					bw.write(content);
					UIManager.put("OptionPane.background", Color.white);
					UIManager.put("Panel.background", Color.white);
					JOptionPane.showMessageDialog(frame, "Text has been saved!");
				}
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
		c.gridx = 1;
		c.gridy = 4;
		add(saveButton,c);


		// Upload Button
		JButton uploadButton = new JButton("Upload");
		uploadButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		uploadButton.setPreferredSize(new Dimension(120,30));
		uploadButton.addActionListener(e->
		{
			try {
				UploadData ud = new UploadData(frame);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		c.gridx = 2;
		c.gridy = 4;
		c.insets = new Insets(10,0,0,200);
		add(uploadButton,c);

		// Audio player button
		JButton AudioButton = new JButton("PLAY/STOP");
		AudioButton.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		AudioButton.setPreferredSize(new Dimension(200,50));
		AudioButton.addActionListener(e->
		{
			if(turn == 0) {
				player.start();
				turn = 1;
			}
			else {
				player.cancel();
				try {
					player = new AudioPlayer(new File(getAudioPath()));
				} catch (IOException | UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				turn = 0;
			}
		});
		
		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(50,0,0,0);
		add(AudioButton,c);
	}	

	/**
	 * Reads the text from a text file and returns the words as a string
	 * @param fileName The name of the file to be read from
	 * @return Returns the words from the file as a string
	 * @throws IOException
	 */
	public static String readText(String fileName, String path, JFrame frame) throws IOException
	{
		// This will reference one line at a time
		String line = null;
		String word = "";
		String newLine = "\n";

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = 
					new FileReader(path + File.separator + fileName);

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
			UIManager.put("OptionPane.background", Color.white);
			UIManager.put("Panel.background", Color.white);
			JOptionPane.showMessageDialog(frame, "Unable to open file '" + fileName + "'");              
		}
		catch(IOException ex) {
			UIManager.put("OptionPane.background", Color.white);
			UIManager.put("Panel.background", Color.white);
			JOptionPane.showMessageDialog(frame, "Error reading file '" 
					+ fileName + "'");              
		}
		return (word);
	}


	/**
	 * Highlights given word
	 * @param Component to highlight in
	 * @param String to highlight
	 */
	public static void highlight(JTextComponent textComp, String pattern)
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
	public static void removeHighlights(JTextComponent textComp)
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

	/**
	 * @param audioName the name of the audio file
	 * @ This method sets the name of the audio file
	 */
	public void setAudioName(String audioName) {
		this.audioName = audioName;

	}

	/**
	 * @return This method return's the path for the audio file
	 */
	public String getAudioPath() {
		return audioPath;
	}

	/**
	 * @param audioPath the path of the audio file
	 * @ This method sets the name of the path of the audio path
	 */
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	/**
	 * @return This method return's the path for the text file
	 */
	public String getTextPath() {
		return textPath;
	}
}
