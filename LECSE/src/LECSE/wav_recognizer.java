package LECSE;

import java.io.File;

//import javax.sound.sampled.LineUnavailableException;
//import net.sourceforge.javaflacencoder.FLACFileWriter;

import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;

/**
 * This is the main class for LECSE's speech recognizer.
 * 
 * @param 
 * @author Lucas Tijerina
 *
 */

public class wav_recognizer{

	private final Recognizer rec = new Recognizer("en-US", "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	private File soundFile;
	private String final_output = "";
	private String[] pos_rep = new String[500];
	private String[] confidence = new String[500];

	public wav_recognizer(File soundFile) {
		setAudioFile(soundFile);
		System.out.println("Reading file: " + soundFile);
	}
	
	public void startRecognition() {
		String output = "";
		
		try {

			// Get the wave file from the embedded resources
			WavFile inputWavFile = WavFile.openWavFile(soundFile);

			// Get the number of audio channels in the wav file
			int numChannels = inputWavFile.getNumChannels();
			// set the maximum number of frames for a target file,
			// based on the number of milliseconds assigned for each file
			int maxFramesPerFile = (int) inputWavFile.getSampleRate() * 10;

			// Create a buffer of maxFramesPerFile frames
			double[] buffer = new double[maxFramesPerFile * numChannels];

			int framesRead;
			int fileCount = 0;
			
			do {

				// Read frames into buffer
				
				framesRead = inputWavFile.readFrames(buffer, maxFramesPerFile);
				WavFile outputWavFile = WavFile.newWavFile(
						new File("out" + (fileCount + 1) + ".wav"),
						inputWavFile.getNumChannels(),
						framesRead,
						inputWavFile.getValidBits(),
						inputWavFile.getSampleRate());

				// Write the buffer
				outputWavFile.writeFrames(buffer, framesRead);
				outputWavFile.close();
				fileCount++;

				//String pos_resp = "";
				System.out.println("Recognizing File " + fileCount);
				
				GoogleResponse resp = rec.getRecognizedDataForWave("out" + fileCount + ".wav");
				output = resp.getResponse();
				setConfidence(resp.getConfidence(), fileCount-1);
				
				if (output != null) { 
					setFinal_output(output + " ");
				}
				
				if (!resp.getOtherPossibleResponses().isEmpty()) {
					pos_rep[fileCount-1] = (String) resp.getOtherPossibleResponses().get(0);
					pos_rep[fileCount-1] = pos_rep[fileCount-1].trim();
				}

			} while (framesRead != 0);

			// Close the input file
			inputWavFile.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void setConfidence(String confidence, int counter) {
		
		if (confidence == null) {
			confidence = "NA";
		}
		this.confidence[counter] = confidence;
	}
	
	public void setFinal_output(String output) {
		this.final_output = final_output + output;
	}
	
	public void setAudioFile(File soundFile) {
		this.soundFile = soundFile;
	}
	
	public File getAudioFile() {
		return soundFile;
	}

	public String[] getConfidence() {
		return confidence;
	}
	
	public String[] getPossibleResponses(){
		return pos_rep;
	}
	
	public String getOutput() {
		return final_output;
	}
}