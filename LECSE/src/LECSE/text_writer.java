package LECSE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public class text_writer{

	private static final String FILENAME = "output.txt";
	private static File soundFile = new File("Nimarcus.wav");
	private static wav_recognizer wavREC = new wav_recognizer(soundFile);

	public static void main(String[] args) throws Exception, UnsupportedAudioFileException {

		AudioPlayer player = new AudioPlayer(soundFile);
		player.run();
		
		//		BufferedWriter bw = null;
		//		FileWriter fw = null;
		//		
		//		wavREC.startRecognition();
		//		String [] confidence = wavREC.getConfidence();
		//		String content = wavREC.getOutput();
		//
		//		try {
		//			fw = new FileWriter(FILENAME);
		//			bw = new BufferedWriter(fw);
		//			bw.write(content);
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		} 
		//
		//		finally {
		//			try {
		//				if (bw != null)
		//					bw.close();
		//				if (fw != null)
		//					fw.close();
		//			} catch (IOException ex) {
		//				ex.printStackTrace();
		//			}
		//		}
	}
}