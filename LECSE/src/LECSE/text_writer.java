package LECSE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Lucas Tijerina
 * The text_writer class is used to call the speech to text method from wav_recognizer
 * and write the output text into a text file on a location given by the filename.
 */

public class text_writer{

	private String filename;
	private String filepath;
	private String textfile_name;

	public text_writer(String filename, String file_path, String textfile_name){
		setfilename(filename);
		setfilepath(file_path);
		setfiletextname(textfile_name);
	}

	/**
	 * @return content
	 * This method returns the output text from the speech to text recognition.
	 */
	
	public String get_recognized_text() {
		File soundFile = new File(filepath + File.separator + filename);
		wav_recognizer wavREC = new wav_recognizer(soundFile);
		wavREC.startRecognition();
		//String [] confidence = wavREC.getConfidence();
		String content = wavREC.getOutput();
		return content;
	}
	
	/**
	 * @param content
	 * The parameters for this method is a string variable obtained from the speech
	 * to text conversion. Does not return any values, however it writes the text
	 * into a .txt file
	 */
	
	public void file_writer(String content) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		filename = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "LECSE"+ File.separator + "Text Files" + File.separator + textfile_name + ".txt";
		

		try {
			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * @param filename
	 */
	public void setfilename(String filename){
		this.filename = filename;
	}
	
	/**
	 * @param filepath
	 */
	public void setfilepath(String filepath){
		this.filepath = filepath;
	}
	
	/**
	 * @param textfile_name
	 */
	public void setfiletextname(String textfile_name){
		this.textfile_name = textfile_name;
	}
}