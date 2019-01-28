package markwatering;

import java.util.Random;

import org.opencv.core.Mat;

public class Distortion {
	public static Mat distortImage(Mat inImage, int percentage) {
		Random generator = new Random();
		byte data[] = new byte[3];
		int countOfPixels = inImage.cols() * inImage.rows();
		int countOfDistortedPixels = percentage * countOfPixels / 1000;
		for(int i = 0; i < countOfDistortedPixels; i++) {
			int randomCol = generator.nextInt(inImage.cols());
			int randomRow = generator.nextInt(inImage.rows());
			inImage.get(randomRow, randomCol, data);
			data[0] = (byte) generator.nextInt(255);
			data[1] = (byte) generator.nextInt(255);
			data[2] = (byte) generator.nextInt(255);
			inImage.put(randomRow, randomCol, data);
		}
		System.out.println("Distortion done!");
		return inImage;
	}
}
