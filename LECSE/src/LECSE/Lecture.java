package LECSE;

/**
 * @author Sigrid Sandström
 * This class holds all the information associated with a lecture
 *
 */
public class Lecture {

	private String audioName;
	private String audioPath;
	private String courseName;
	private String date;
	private String fileName;
	private String instructorName;
	private String textPath;

	/**
	 * @return This method returns the audio name of the lecture
	 */
	public String getAudioName(){
		return audioName;
	}

	/**
	 * @return This method returns the audio path of the lecture 
	 */
	public String getAudioPath(){
		return audioPath;
	}

	/**
	 * @return This method returns the course name associated with the lecture
	 */
	public String getCourseName(){
		return courseName;
	}

	/**
	 * @return This method returns the filename of the lecture
	 */
	public String getFileName(){
		return fileName;
	}

	/**
	 * @return This method returns the instructor name associated with the lecture
	 */
	public String getInstructorName(){
		return instructorName;
	}

	/**
	 * @return This method returns the path for the text file of the lecture
	 */
	public String getTextPath(){
		return textPath;
	}

	/**
	 * @return This method returns the date that the lecture was uploaded to the program
	 */
	public String getDate(){
		return date;
	}


	/**
	 * @ This method sets the audio name of the recorded audio lecture
	 * @param name the name of the audio file
	 */
	public void setAudioName(String name){
		audioName = name;
	}

	/**
	 * @ This method sets the audio path of the recorded audio lecture
	 * @param path the path of the audio file
	 */
	public void setAudioPath(String path){
		audioPath = path;
	}

	/**
	 * @ This method sets the course name of the lecture
	 * @param name the name of the course associated with the lecture
	 */
	public void setCourseName(String name){
		courseName = name;
	}

	/**
	 * @ This method sets date of the day that the lecture was uploaded
	 * @param d the date the lecture was uploaded to the program
	 */
	public void setDate(String d){
		date = d;
	}

	/**
	 * @ This method sets the file name of the text file
	 * @param name the name of the text file 
	 */
	public void setFileName(String name){
		fileName = name;
	}

	/**
	 * @ This method sets the instructor name of the lecture
	 * @param insName the name of the instructor associated with the lecture
	 */
	public void setInstructorName(String insName){
		instructorName = insName;
	}

	/**
	 * @ This method sets the path for the text file
	 * @param path the path for the text file
	 */
	public void setTextPath(String path){
		textPath = path;
	}
}
