package LECSE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * @author Sigrid and Alex
 * @ This class is a JPanel that contains all the components of the profile page
 */
public class ProfileGUI extends JPanel {

	/**
	 * @param frame is the main frame that is used to display the ProfileGUI JPanel
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ProfileGUI(JFrame frame) throws FileNotFoundException, IOException {
		//Load the database
		Database db = new Database();		
		db.loadUserInfo();
		db.loadCourseInstructor();
		db.loadLectures();

		//Create a text field for the name 
		JTextField name = new JTextField(db.getName());
		name.setEditable(false);
		name.setPreferredSize(new Dimension(460,45));
		name.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		name.setBackground(Color.WHITE);
		name.setBorder(null);

		//Create a text field for the major
		JTextField major = new JTextField(db.getMajor());
		major.setEditable(false);
		major.setPreferredSize(new Dimension(460,45));
		major.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		major.setBackground(Color.WHITE);
		major.setBorder(null);

		//Create a text field for the university
		JTextField university = new JTextField(db.getUniversity());
		university.setEditable(false);
		university.setPreferredSize(new Dimension(460,45));
		university.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		university.setBackground(Color.WHITE);
		university.setBorder(null);

		//Create a text field for the sorting selection
		JTextField sort = new JTextField(db.getSortSelection());
		sort.setEditable(false);
		sort.setPreferredSize(new Dimension(460,45));
		sort.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		sort.setBackground(Color.WHITE);
		sort.setBorder(null);

		//Create the header and titles
		JLabel header = new JLabel("Profile");
		JLabel title1 = new JLabel("NAME");
		JLabel title2 = new JLabel("MAJOR");
		JLabel title3 = new JLabel("UNIVERSITY");
		JLabel title4 = new JLabel("INSTRUCTORS");
		JLabel title5 = new JLabel("COURSES");
		JLabel title6 = new JLabel("SORT BY");
		
		//Set the font of the header and titles
		header.setFont(new Font("Segoe UI", Font.PLAIN, 50));
		title1.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		title2.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		title3.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		title4.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		title5.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		title6.setFont(new Font("Segoe UI", Font.PLAIN, 30));

		//Create a new JPanel with a GridBagLayout and constraints
		JPanel p = new JPanel(new GridBagLayout());
		p.setPreferredSize(new Dimension(500,900));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		p.setBackground(Color.white);
		
		//Add the header
		p.add(header, gbc);

		//Add the first title
		gbc.gridy++;
		gbc.insets = new Insets(20, 10, 0, 0);
		p.add(title1, gbc);

		//Add the name text field
		gbc.gridy++;
		gbc.insets = new Insets(0, 10, 0, 0);
		p.add(name, gbc);

		//Add the second title
		gbc.gridy++;
		gbc.insets = new Insets(20, 10, 0, 0);
		p.add(title2, gbc);

		//Add the major text field 
		gbc.gridy++;
		gbc.insets = new Insets(0, 10, 0, 0);
		p.add(major, gbc);

		//Add the third title
		gbc.gridy++;
		gbc.insets = new Insets(20, 10, 0, 0);
		p.add(title3, gbc);

		//Add the university text field
		gbc.gridy++;
		gbc.insets = new Insets(0, 10, 0, 0);
		p.add(university, gbc);

		//Add the fourth title
		gbc.gridy++;
		gbc.insets = new Insets(20, 10, 0, 0);
		p.add(title6, gbc);

		//Add the sorting selection text field
		gbc.gridy++;
		gbc.insets = new Insets(0, 10, 0, 0);
		p.add(sort, gbc);

		//Create a combo box
		String[] choices = {"","Edit personal information", "Save Changes", "Change sorting selection"};
		JComboBox<String> menu = new JComboBox<String>(choices);
		menu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menu.setPreferredSize(new Dimension(195,35));
		//Add an action to the different choices of the combo box
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(menu.getSelectedItem().equals("Edit personal information")) {
					name.setEditable(true);
					major.setEditable(true);
					university.setEditable(true);
					Border border = BorderFactory.createLineBorder(Color.black, 2);
					name.setBorder(border);
					major.setBorder(border);
					university.setBorder(border);
				}else if(menu.getSelectedItem().equals("Change sorting selection")) {
					JCheckBox instructor = new JCheckBox("Instructor");
					instructor.setFont(new Font("Segoe UI", Font.PLAIN, 20));
					instructor.setMnemonic(KeyEvent.VK_C); 
					instructor.setBackground(Color.white);

					JCheckBox course = new JCheckBox("Course");
					course.setFont(new Font("Segoe UI", Font.PLAIN, 20));
					course.setMnemonic(KeyEvent.VK_G); 
					course.setBackground(Color.white);


					JLabel lb = new JLabel("SORT BY");
					lb.setFont(new Font("Segoe UI", Font.PLAIN, 30));

					//Create a panel and frame
					JPanel optionPanel = new JPanel(new GridBagLayout());
					GridBagConstraints gbc2 = new GridBagConstraints();
					gbc2.anchor = GridBagConstraints.NORTHWEST;
					gbc2.insets = new Insets(0, 20, 0, 0);
					gbc2.gridx = 0;
					gbc2.gridy = 0;
					gbc2.weightx = 1;
					gbc2.fill = GridBagConstraints.NONE;
					optionPanel.setBackground(Color.white);
					optionPanel.add(lb, gbc2);

					gbc2.gridy++;
					gbc2.insets = new Insets(20, 20, 0, 0);
					optionPanel.add(instructor, gbc2);

					gbc2.gridy++;
					gbc2.insets = new Insets(0, 20, 0, 0);
					optionPanel.add(course, gbc2);

					// Frame setting
					JFrame sortFrame = new JFrame();
					sortFrame.setLayout(new FlowLayout());
					sortFrame.setSize(300, 220);
					sortFrame.getContentPane().setLayout(new BorderLayout());
					JPanel empty = new JPanel();
					empty.setBackground(Color.white);
					sortFrame.add(optionPanel, BorderLayout.PAGE_START);
					sortFrame.add(empty);

					sortFrame.setLocationRelativeTo(null);
					sortFrame.setVisible(true);

					instructor.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							db.setSortSelection("Instructor");
							try {
								db.changePersonalInfo("Instructor", 3);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							sort.setText("Instructor");
							//Close frame
							sortFrame.setVisible(false);;
						}
					});

					course.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							db.setSortSelection("Course");
							try {
								db.changePersonalInfo("Course", 3);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							sort.setText("Course");
							//Close frame
							sortFrame.setVisible(false);;
						}
					});
				}else if(menu.getSelectedItem().equals("Save Changes")) {
					name.setEditable(false);
					major.setEditable(false);
					university.setEditable(false);
					name.setBorder(null);
					major.setBorder(null);
					university.setBorder(null);
					try {
						db.addPersonalInfo(name.getText(), major.getText(), university.getText(), db.getSortSelection());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		
		gbc.weighty = 1;
		gbc.gridy++;
		gbc.insets = new Insets(20, 10, 0, 0);
		p.add(menu, gbc);
		
		//Create a JTextArea for the help page
				JTextArea HelpText = new JTextArea("LECSE is a program that allows the user to "
						+ "upload an audio file of a lecture and receive a transmitted text receipt"
						+ "of the lecture.\n\n"
						+ "To begin, the user must set up a profile.\n\n"
						+ "To upload a lecture, click the upload button found in the media page, browse your files, "
						+ "and select the .wav file of the lecture you wish to upload.\n\n"
						+ "You must enter in the name of the lecture, course, instructor, and date. "
						+ "This information will be used in saving the lecture to the system so that you may access it later.\n\n"
						+ "Once the upload is complete, you will be able to see an editable visual "
						+ "transcript of what was said in the audio file as well has be given the option to listen to the audio.\n\n"
						+ "Both the auido and text file will be sorted into files and saved to the "
						+ "program so that you may access it later if you wish."
						+ "To access the saved text files and audio files, go to 'This computer', followed by"
						+ " 'Documents', then 'LECSE'."
				);
				
		
		//Create a button for the help page
		JButton help = new JButton("HELP");
		help.setPreferredSize(new Dimension(100,30));
		help.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Create another JPanel with a GridBagLayout and constraints for the Help page
				JFrame helpFrame = new JFrame();
				helpFrame.setLayout(new FlowLayout());
				helpFrame.setSize(500, 500);
				helpFrame.getContentPane().setLayout(new BorderLayout());
				
				JPanel helpPanel = new JPanel();
				helpPanel.setBackground(Color.white);
				HelpText.setBorder(BorderFactory.createMatteBorder(25, 25, 25, 25, Color.WHITE));
				helpFrame.add(helpPanel,BorderLayout.PAGE_START);
				HelpText.setEditable(false);
				HelpText.setLineWrap(true);
				HelpText.setWrapStyleWord(true);
				helpFrame.add(HelpText);
				helpFrame.setLocationRelativeTo(null);
				helpFrame.setVisible(true);
			}
		});
				
		JPanel p3 = new JPanel(new GridBagLayout());
		p3.setPreferredSize(new Dimension(400,900));
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.anchor = GridBagConstraints.NORTHWEST;
		gbc3.insets = new Insets(0, 0, 0, 0);
		gbc3.gridx = 0;
		gbc3.gridy = 0;
		gbc3.weighty = 0;
		gbc3.fill = GridBagConstraints.NONE;
		p3.setBackground(Color.white);
		p3.add(help, gbc3);
		
		//Create a button to switch to mediaGUI
		JButton changePanel = new JButton("Media");
		changePanel.setPreferredSize(new Dimension(100,30));
		changePanel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		changePanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Code to switch panel here
				try {
					MediaGUI mg = new MediaGUI(frame);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(mg);
					frame.revalidate();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
			
		gbc3.gridx = 1;
		p3.add(changePanel, gbc3);
		
		gbc3.gridx = 0;
		gbc3.gridy++;
		gbc3.insets = new Insets(54, 0, 0, 0);
		p3.add(title4, gbc3);

		//Get the array of courses and instructors
		String[][] ci = db.getCourseAndINstructor();	    

		//Add the instructors
		for(int i = 0; i < ci.length; i++) {
			if(ci[i][1] != null) {
				JLabel instructor = new JLabel(ci[i][1]);
				instructor.setFont(new Font("Segoe UI", Font.PLAIN, 22));
				gbc3.gridy++;
				gbc3.insets = new Insets(0, 0, 0, 0);
				p3.add(instructor, gbc3);
			}
			//Max show 12 instructors
			if(i == 11) {
				i = ci.length;
			}
		}

		gbc3.gridy++;
		gbc3.insets = new Insets(20, 0, 0, 0);
		p3.add(title5, gbc3);

		//Add the courses	    
		for(int i = 0; i < ci.length; i++) {
			if(ci[i][0] != null) {
				JLabel course = new JLabel(ci[i][0]);
				course.setFont(new Font("Segoe UI", Font.PLAIN, 22));
				gbc3.gridy++;
				gbc3.insets = new Insets(0, 0, 0, 0);
				p3.add(course, gbc3);
			}
			//Max show 12 courses
			if(i == 11) {
				i = ci.length;
			}
		}
		
		JLabel empty = new JLabel();
		gbc3.weighty = 1;
		p3.add(empty, gbc3);
		
		setLayout(new FlowLayout());
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		add(p);
		add(p3);
		setBackground(Color.white);
	}

}