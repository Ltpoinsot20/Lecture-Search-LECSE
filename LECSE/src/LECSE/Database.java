package LECSE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Sigrid Sandström
 */
public class Database {
	//Attributes for user name and major and university
	private String userName;
	private String major;
	private String university;

	//Attribute for sorting selection
	private String sort;

	//Create a file for the lectures
	private File lectureFile = new File("lectures.txt");

	//Create a file for courses and instructors
	private File courseANDinstructorFile = new File("courseANDinstructor.txt");

	//Create a file for the user info
	private File userInfoFile = new File("userInfo.txt");
	
	//Create a temporary file to modify the other files
	private File tempFile = new File("tempFile.text");

	//Create an ArrayList to hold lecture objects
	private ArrayList<Lecture> lectureArray = new ArrayList<Lecture>();

	//Create a 2D array to hold the course names and instructor names
	private String[][] course_instructor_array = new String[100][2];

	//Add name, major, university and sorter selection to the file
	/**
	 * @param name the user's name
	 * @param major the user's major
	 * @param university the user's university
	 * @param sortSelect how the user want's to sort the lectures
	 * By course: sortSelect == Course 
	 * By instructor: sortSelect == Instructor
	 * @throws IOException
	 * @ This method saves user name, major, university and sorting selection to a text file
	 */
	/**
	 * @param n the name of the user
	 * @param m the user's major
	 * @param u the user's university
	 * @param s the user's sorting selection 
	 * @throws IOException
	 */
	public void addPersonalInfo(String n, String m, String u, String s) throws IOException {
		//Set user name, major, university and sort selection
		userName = n;
		major = m;
		university = u;
		sort = s;
		
		//Add strings to the file
		FileWriter fw = new FileWriter(userInfoFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(n);
		bw.append(System.lineSeparator());
		bw.write(m);
		bw.append(System.lineSeparator());
		bw.write(u);
		bw.append(System.lineSeparator());
		bw.write(s);
		bw.close();
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @ This method loads the saved user information into the program
	 */
	public void loadUserInfo() throws FileNotFoundException, IOException {
		// if file doesn't exists, then create it
		if (!userInfoFile.exists()) {
			userInfoFile.createNewFile();
			//Add strings to the file
			FileWriter fw = new FileWriter(userInfoFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("NoName");
			bw.append(System.lineSeparator());
			bw.write("NoMajor");
			bw.append(System.lineSeparator());
			bw.write("NoUniversity");
			bw.append(System.lineSeparator());
			bw.write("Course");
			bw.close();
		}

		// Read data from file
		BufferedReader br = new BufferedReader(new FileReader(userInfoFile));

		for(int i = 0; i < 4; i++) {
			String line = br.readLine();
			if(i == 0) {
				userName = line;
			}else if(i == 1) {
				major = line;
			}else if(i == 2) {
				university = line;
			}else if(i == 3) {
				sort = line;
			}	
		}

		br.close();
	}


	//Add a lecture to the ArrayList and to the file
	/**
	 * @param lecture a Lecture object
	 * @throws IOException
	 * @ This method adds a Lecture object to an ArrayList and saves the state of the object to a text file
	 */
	public void addLecture(Lecture lecture) throws IOException{
		lectureArray.add(lecture);

		//Create the string to save to the text file
		String str = lecture.getInstructorName() + "," + lecture.getCourseName() + "," + lecture.getFileName() +
				"," + lecture.getTextPath() + ',' + lecture.getAudioName() + "," + lecture.getAudioPath() + "," + lecture.getDate();

		//Create a file writer
		FileWriter fw = new FileWriter(lectureFile.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.append(System.lineSeparator());
		bw.close();
	}


	//Remove a lecture from the array list
	/**
	 * @param lectureID the filename of the lecture that will be removed
	 * @throws IOException
	 * @ This method removes a Lecture object from the ArrayList and from the text file
	 */
	public void removeLecture(String lectureID) throws IOException{
		int lineToRemove = 0;

		for(int i = 0; i < lectureArray.size(); i++) {
			if(lectureID.equals(lectureArray.get(i).getFileName())) {
				//Get the course name  and instructor name of the lecture to remove
				String courseName = lectureArray.get(i).getCourseName();
				String instructorName = lectureArray.get(i).getInstructorName();
				//Remove the lecture from the ArrayList
				lectureArray.remove(i);

				//If this is the last lecture associated with a course, delete the course and instructor
				if(this.getCourseArrayList(courseName).size() == 0) {
					this.removeCourseAndInstructor(courseName, instructorName);
				}
				lineToRemove = i;
				//End loop
				i = lectureArray.size();
			}

		}

		//Remove lecture from text file
		BufferedReader reader = new BufferedReader(new FileReader(lectureFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
		int count = 0;
		String line;
		while((line = reader.readLine()) != null) {
			if(count != lineToRemove) {
				writer.write(line);
				writer.newLine();		
			}
			count++;
		}

		reader.close();
		writer.close();
		lectureFile.delete();
		tempFile.renameTo(lectureFile);


	}



	//Populate the ArrayList with saved Lectures
	/**
	 * @throws IOException
	 * @ This method populates the ArrayList with Lecture objects saved in the text file
	 */
	public void loadLectures() throws IOException {		
		// if file doesn't exists, then create it
		if (!lectureFile.exists()) {
			lectureFile.createNewFile();
		}

		// Read data from file
		BufferedReader br = new BufferedReader(new FileReader(lectureFile));

		// Read file line by line
		String line;
		while ((line = br.readLine()) != null) {
			//Use a comma to distinguish where to split the string
			String delims = "[,]";
			//Split the string where there is a comma
			String[] data = line.split(delims);

			// Create new Lecture object
			Lecture lecture = new Lecture();
			lecture.setInstructorName(data[0]);
			lecture.setCourseName(data[1]);
			lecture.setFileName(data[2]);
			lecture.setTextPath(data[3]);
			lecture.setAudioName(data[4]);
			lecture.setAudioPath(data[5]);
			lecture.setDate(data[6]);

			// Add object to list
			lectureArray.add(lecture);

		}
		br.close();
	}

	//Get an ArrayList of lectures with the same instructor
	/**
	 * @param instructorName the name of an instructor
	 * @return This method returns an ArrayList of Lecture objects
	 * @ This method locates all Lecture objects associated with the instructor passed as a parameter and saves them in an ArrayList
	 */
	public ArrayList<Lecture> getInstructorArrayList(String instructorName){
		ArrayList<Lecture> instructorArray = new ArrayList<Lecture>();
		for(int i = 0; i < lectureArray.size(); i++) {
			if(instructorName.equals(lectureArray.get(i).getInstructorName())) {
				instructorArray.add(lectureArray.get(i));
			}
		}
		return instructorArray;	
	}

	//Get an ArrayList of lectures with the same class Name
	/**
	 * @param courseName the name of a course
	 * @return This method returns an ArrayList of Lecture objects.
	 * @ This method locates all Lecture objects associated with the course passed as a parameter and saves them in an ArrayList
	 */
	public ArrayList<Lecture> getCourseArrayList(String courseName){
		ArrayList<Lecture> courseArray = new ArrayList<Lecture>();
		for(int i = 0; i < lectureArray.size(); i++) {
			if(courseName.equals(lectureArray.get(i).getCourseName())) {
				courseArray.add(lectureArray.get(i));
			}
		}
		return courseArray;	
	}


	//Add a course and instructor to the array and save them to a file
	/**
	 * @param course the name of a course 
	 * @param instructor the name of an instructor
	 * @return This method returns true if the adding was successful and false if the adding failed
	 * @throws IOException
	 * @ This method adds the instructor name and course name to a 2D array and saves them to a text file
	 */

	public boolean addCourse_Instructor(String course, String instructor) throws IOException {
		boolean space = false;
		for(int i = 0; i<course_instructor_array.length; i++) {
			//find the first available row of array
			if(course_instructor_array[i][0] == null) {
				//Add the course to the first column of the matrix
				course_instructor_array[i][0] = course;
				//Add the instructor to the second column of the matrix
				course_instructor_array[i][1] = instructor;
				//End the loop
				i = course_instructor_array.length;

				//Create a string to store in the file
				String str = course + "," + instructor;
				//Add the string to the file
				FileWriter fw = new FileWriter(courseANDinstructorFile.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(str);
				bw.append(System.lineSeparator());
				bw.close();

				//Signal that the array isn't full
				space = true;
			} 
		}
		//Return if the adding was successful or not
		return space;
	}


	/**
	 * @param changeTo the new string of the personal information to change
	 * @param row the which row of the text file to change.
	 * Change name: row = 0
	 * Change major: row = 1
	 * Change university: row = 2
	 * Change sorting selection: row = 3
	 * @throws IOException
	 */
	public void changePersonalInfo(String changeTo, int row) throws IOException {
		//Create a buffer reader and buffer writer
		BufferedReader reader = new BufferedReader(new FileReader(userInfoFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		for(int i = 0; i < 4; i++) {
			String line = reader.readLine();
			if(i == row) {
				writer.write(changeTo);
				writer.newLine();
			}else {
				writer.write(line);
				writer.newLine();
			}

		}

		reader.close();
		writer.close();
		//delete the original file
		userInfoFile.delete();
		//rename the temporary file to the name of the original file
		tempFile.renameTo(userInfoFile);

	}


	//Load the variables
	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @ This method loads the course_instructor_array with saved courses and instructors
	 */
	public void loadCourseInstructor() throws FileNotFoundException, IOException{
		// if file doesn't exists, then create it
		if (!courseANDinstructorFile.exists()) {
			courseANDinstructorFile.createNewFile();
		}

		// Read data from file
		BufferedReader br = new BufferedReader(new FileReader(courseANDinstructorFile));
		//count variable
		int count = 0;

		// Read file line by line
		String line;
		while ((line = br.readLine()) != null) {
			//Use a comma to distinguish where to split the string
			String delims = "[,]";
			//Split the string where there is a comma
			String[] data = line.split(delims);

			//Add the course names to the first column
			course_instructor_array[count][0] = data[0];
			//Add the instructor names to second column
			course_instructor_array[count][1] = data[1];
			
			//increment count
			count++;
		}
		br.close();
	}


	
	/**
	 * @param cn the name of a course
	 * @param in the name an instructor
	 * @throws IOException
	 * @ This method deletes a course and instructor from the array, and from the text file
	 */
	public void removeCourseAndInstructor(String cn, String in) throws IOException {
		//String to delete
		String str = cn + "," + in;

		BufferedReader reader = new BufferedReader(new FileReader(courseANDinstructorFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		//Delete line from file
		String line;
		while ((line = reader.readLine()) != null) {
			if(!line.equals(str)) {
				//Write to file
				writer.write(line);
				writer.newLine();
			}
		}

		reader.close();
		writer.close();
		//Delete the original file and rename the temp file
		courseANDinstructorFile.delete();
		tempFile.renameTo(courseANDinstructorFile);

		//Delete course and instructor from the array
		for(int i = 0; i < course_instructor_array.length; i++) {
			if(cn.equals(course_instructor_array[i][0])) {
				//Delete course and instructor from the array
				course_instructor_array[i][0] = null;
				course_instructor_array[i][1] = null;
			}
		}
	}

	/**
	 * @return This method returns an ArrayList with the users Lectures
	 */
	public ArrayList<Lecture> getLectures(){
		return lectureArray;
	}
	
	/**
	 * @return This method returns the 2D string array that holds the user's courses and instructors
	 */
	public String[][] getCourseAndINstructor(){
		return course_instructor_array;
	}
	
	/**
	 * @return This method returns the user's major
	 */
	public String getMajor(){
		return major;
	}

	/**
	 * @return This method returns the user's name
	 */
	public String getName(){
		return userName;
	}

	/**
	 * @return This method returns the user's university
	 */
	public String getUniversity(){
		return university;
	}

	/**
	 * @return This method return's the user's sorting selection
	 */
	public String getSortSelection() {
		return sort;
	}
	
	/**
	 * @param n the user's name
	 * @ This method sets the user's name to n
	 */
	public void setName(String n) {
		userName = n;
	}
	
	/**
	 * @param m the user's major
	 * @ This method sets the user's major to m
	 */
	public void setMajor(String m) {
		major = m;
	}
	
	/**
	 * @param uni the user's university
	 * @ This method sets the user's university to uni
	 */
	public void setUniversity(String uni) {
		university = uni;
	}
	
	/**
	 * @param selection the way the user wants to sort it's lectures
	 * @ This method sets the sort to selection
	 */
	public void setSortSelection(String selection) {
		sort = selection;
	}
}

