package LECSE;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import LECSE.MediaGUI;

public class UploadData{
	//public static void main(String[] args) throws FileNotFoundException, IOException{
	//Load the database

	public UploadData(JFrame mainFrame) throws FileNotFoundException, IOException {

		Database db = new Database();		
		db.loadUserInfo();
		db.loadCourseInstructor();
		db.loadLectures();
		
		JFrame frame = new JFrame();

		JButton chooser = new JButton("Choose File");
		chooser.setPreferredSize(new Dimension(100,30));
		chooser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		//upload.addActionListener(new ActionListener() {}
		// add file browsing
		JButton upload = new JButton("Okay");
		upload.setPreferredSize(new Dimension(100,30));
		upload.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//Add the users input to the database
	
					MediaGUI mg = new MediaGUI(mainFrame);
					mainFrame.getContentPane().removeAll();
					mainFrame.getContentPane().add(mg);
					mainFrame.revalidate();
					frame.setVisible(false);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});


		// Create a text field for lecture title
		JLabel lectureTitle = new JLabel("Lecture: ");
		JTextField Lecture = new JTextField();
		Lecture.setPreferredSize(new Dimension(150,45));
		Lecture.setEditable(true);
		//	Lecture.setBorder(null);
		//Lecture.setMinimumSize(new Dimension(150, 45));
		String lecture_title = Lecture.getText();
		lectureTitle.setPreferredSize(new Dimension(150,45));
		lectureTitle.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		lectureTitle.setBackground(Color.WHITE);
		lectureTitle.setBorder(null);


		// Create a text field for instructor name
		JLabel instructorName = new JLabel("Instructor: ");
		JTextField Instructor = new JTextField();
		Instructor.setPreferredSize(new Dimension(150,45));
		Instructor.setEditable(true);
		String instructor_name = Instructor.getText();
		instructorName.setPreferredSize(new Dimension(460,45));
		instructorName.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		instructorName.setBackground(Color.WHITE);
		instructorName.setBorder(null);

		// Create a text field for course name
		JLabel courseName = new JLabel("Course: ");
		JTextField Course = new JTextField();
		Course.setPreferredSize(new Dimension(150,45));
		Course.setEditable(true);
		String course_name = courseName.getText();
		courseName.setPreferredSize(new Dimension(460,45));
		courseName.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		courseName.setBackground(Color.WHITE);
		courseName.setBorder(null);

		// Create a text field for date
		JLabel date = new JLabel("Date: ");
		JTextField Date = new JTextField();
		Date.setPreferredSize(new Dimension(150,45));
		Date.setEditable(true);
		String date_field = date.getText();
		date.setPreferredSize(new Dimension(460,45));
		date.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		date.setBackground(Color.WHITE);
		date.setBorder(null);

		// set the font for header and titles
		lectureTitle.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		instructorName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		courseName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		date.setFont(new Font("Segoe UI", Font.PLAIN, 30));

		//Create a new JPanel with a GridBagLayout and constraints
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(450, 400));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
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
		//Add text field
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel.add(Lecture, gbc);

		//Add the instructor name text field 
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 30, 0, 0);
		panel.add(instructorName, gbc);
		//Add the text field
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel.add(Instructor, gbc);

		//Add the course name text field 
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 30, 0, 0);
		panel.add(courseName, gbc);
		//Add the text field
		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel.add(Course, gbc);

		//Add the date text field 
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 30, 0, 0);
		panel.add(date, gbc);
		//Add the text field
		gbc.gridy = 3;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		panel.add(Date, gbc);

		//Add the file browsing button
		gbc.insets = new Insets(0, 65, 0, 0);
		gbc.gridy = 4;
		gbc.gridx = 0;
		panel.add(chooser, gbc);

		//Add the okay button
		gbc.gridy = 4;
		gbc.gridx = 2;
		gbc.weighty = 1;
		gbc.weightx = 1;
		panel.add(upload, gbc);

		//JFrame Frame = new JFrame();
		frame.setSize(450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout()); 
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.add(panel);
		frame.getContentPane().setBackground(Color.white);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	} // end of main method
} // end of class

