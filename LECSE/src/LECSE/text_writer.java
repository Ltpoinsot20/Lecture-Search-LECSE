package LECSE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//import javax.sound.sampled.UnsupportedAudioFileException;
//		AudioPlayer player = new AudioPlayer(soundFile);
//		player.run();

public class text_writer{

	private String filename;
	private File soundFile;
	private String filepath;
	private wav_recognizer wavREC = new wav_recognizer(soundFile);

	public text_writer(String filename, File soundFile, String file_path){
		setfilename(filename);
		setsoundFile(soundFile);
		setfilepath(file_path);
	}
	
	public String get_recognized_text() {
		wavREC.startRecognition();
		//String [] confidence = wavREC.getConfidence();
		String content = wavREC.getOutput();
		return content;
	}
	
	public void file_writer(String content) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		filename = filepath + File.separator + filename;
		

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
	
	public void setfilename(String filename){
		this.filename = filename;
	}
	
	public void setsoundFile(File soundFile){
		this.soundFile = soundFile;
	}
	
	public void setfilepath(String filepath){
		this.filepath = filepath;
	}
}