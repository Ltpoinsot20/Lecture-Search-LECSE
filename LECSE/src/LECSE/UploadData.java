package LECSE;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * @author Alex, Sigrid, Jordan, Lucas
 *
 */
public class UploadData{
	private File tempfile;
	
	public UploadData(JFrame mainFrame) throws FileNotFoundException, IOException {

		Database db = new Database();		
		db.loadUserInfo();
		db.loadCourseInstructor();
		db.loadLectures();
		
		AudioConverter converter = new AudioConverter();
		
		JFrame frame = new JFrame();

		// Create a text field for lecture title
		JLabel lectureTitle = new JLabel("Lecture: ");
		JTextField Lecture = new JTextField();
		Lecture.setPreferredSize(new Dimension(150,45));
		Lecture.setEditable(true);
		lectureTitle.setPreferredSize(new Dimension(150,45));
		lectureTitle.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		Lecture.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lectureTitle.setBackground(Color.WHITE);


		// Create a text field for instructor name
		JLabel instructorName = new JLabel("Instructor: ");
		JTextField Instructor = new JTextField();
		Instructor.setPreferredSize(new Dimension(150,45));
		Instructor.setEditable(true);
		instructorName.setPreferredSize(new Dimension(460,45));
		instructorName.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		Instructor.setFont(new Font("Segoe UI", Font.PLAIN, 20));

		// Create a text field for course name
		JLabel courseName = new JLabel("Course: ");
		JTextField Course = new JTextField();
		Course.setPreferredSize(new Dimension(150,45));
		Course.setEditable(true);
		courseName.setPreferredSize(new Dimension(460,45));
		courseName.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		Course.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		courseName.setBackground(Color.WHITE);
		
		// Create a text field for date
		JLabel date = new JLabel("Date: ");
		JTextField Date = new JTextField();
		Date.setPreferredSize(new Dimension(150,45));
		Date.setEditable(true);
		date.setPreferredSize(new Dimension(460,45));
		date.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		Date.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		date.setBackground(Color.WHITE);

		// set the font for header and titles
		lectureTitle.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		instructorName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		courseName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		date.setFont(new Font("Segoe UI", Font.PLAIN, 30));

		//Create a new JPanel with a GridBagLayout and constraints
		JPanel panel = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());
		panel.setBackground(Color.white);
		panel2.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(225, 200));
		panel2.setPreferredSize(new Dimension(225, 200));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		panel.setBackground(Color.white);


		//Add the lecture title text field
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 30, 0, 0);
		panel.add(lectureTitle, gbc);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel2.add(Lecture, gbc);

		//Add the instructor name text field 
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 30, 0, 0);
		panel.add(instructorName, gbc);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel2.add(Instructor, gbc);

		//Add the course name text field 
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 30, 0, 0);
		panel.add(courseName, gbc);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel2.add(Course, gbc);

		//Add the date text field 
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 30, 0, 0);
		panel.add(date, gbc);

		JLabel empty = new JLabel();
		gbc.gridy = 4;
		gbc.weighty = 1;
		gbc.weightx = 1;
		panel.add(empty, gbc);


		//Add the text field
		gbc.weighty = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel2.add(Date, gbc);

		//Create a button
		JButton chooser = new JButton("Choose File");
		chooser.setPreferredSize(new Dimension(100,30));
		chooser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "LECSE"+ File.separator + "Audio Files";
		chooser.addActionListener(new ActionListener() {
			
			// Performs speech to text conversion when file is uploaded
			@Override
			public void actionPerformed(ActionEvent e) {
				String filepath = converter.getAudioFile();
				tempfile = new File(filepath);
				tempfile.renameTo(new File(path +  File.separator + tempfile.getName()));
			}
		});
		// add file browsing
		JButton upload = new JButton("Okay");
		upload.setPreferredSize(new Dimension(65,30));
		upload.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Begining Speech to Text Conversion...\nThis may take a few minutes");
				text_writer writer = new text_writer(tempfile.getName(), path, Lecture.getText());
				String output_text = writer.get_recognized_text();
				writer.file_writer(output_text);
				try {
					if(Lecture.getText().length() != 0 && Course.getText().length() != 0 && Instructor.getText().length() != 0 && Date.getText().length() != 0) {
						//Add the course and instructor to array in database + file if it doen't exist already
						Boolean exist = false;
						String course_name = Course.getText();
						String instructor_name = Instructor.getText();
						String [][] ci = db.getCourseAndINstructor();
						for(int i = 0; i < ci.length; i++) {
							if(course_name.equals(ci[i][0]) && instructor_name.equals(ci[i][1])) {
								exist = true;
							}
						}

						if(exist == false) {
							db.addCourse_Instructor(course_name, instructor_name);
						}

						//Create a new lecture object
						Lecture lecture = new Lecture();
						lecture.setCourseName(course_name);
						lecture.setInstructorName(instructor_name);
						lecture.setFileName(Lecture.getText());
						lecture.setDate(Date.getText());
						lecture.setAudioName(tempfile.getName());
						db.addLecture(lecture);

						MediaGUI mg = new MediaGUI(mainFrame);
						mainFrame.getContentPane().removeAll();
						mainFrame.getContentPane().add(mg);
						mainFrame.revalidate();
						frame.setVisible(false);
					}else {
						UIManager.put("OptionPane.background", Color.white);
						UIManager.put("Panel.background", Color.white);
						JOptionPane.showMessageDialog(frame, "All text fields must be filled out.");
					}


				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			
		});

		//Add the file browsing button
		gbc.insets = new Insets(70, 10, 0, 0);
		gbc.gridy = 4;
		gbc.gridx = 0;
		panel2.add(chooser, gbc);

		//Add the okay button
		gbc.insets = new Insets(40, 85, 0, 0);
		gbc.gridy = 5;
		gbc.weighty = 1;
		gbc.weightx = 1;
		panel2.add(upload, gbc);

		//JFrame Frame = new JFrame();
		frame.setSize(450, 400);
		frame.setLayout(new GridLayout()); 
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.add(panel);
		frame.add(panel2);
		frame.getContentPane().setBackground(Color.white);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	} // end of main method
	
} // end of class

