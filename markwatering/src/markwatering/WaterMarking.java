package markwatering;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class WaterMarking {

	public static void main(String[] arg) throws FileNotFoundException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		PrintWriter zapisLSB = new PrintWriter("resources/resultsLSB.txt");
		PrintWriter zapisALT = new PrintWriter("resources/resultsALT.txt");
		PrintWriter diffCountALT = new PrintWriter("resources/diffCountALT.txt");
		PrintWriter diffCountLSB = new PrintWriter("resources/diffCountLSB.txt");
		String location = "resources/lena_std.png";
		String msg = "TopSecretMessageTopSecretMessageTopSecretMessageTopSecretMessage";
		
//		Mat imageDist = Imgcodecs.imread(location);
//		Mat resDist = Distortion.distortImage(imageDist, 5);
//		Imgcodecs.imwrite("resources/lena_res_dist.png", resDist);
		
//		Mat imageALT = Imgcodecs.imread(location);
//		Mat resALT = ALT.encodeImage(imageALT, "TopSecretMessage");
//		Imgcodecs.imwrite("resources/lena_res_alt.png", resALT);
//		String resultALT = ALT.decodeImage(resALT);
		
//		Mat imageLSB = Imgcodecs.imread(location);
//		Mat resLSB = LSB.encodeImage(imageLSB, "SuperTajna");
//		Imgcodecs.imwrite("resources/lena_res_lsb.jpg", resLSB);
//		String resultLSB = LSB.decodeImage(resLSB);
		
//		System.out.println("Result ALT: " + resultALT);
//		System.out.println("Result LSB: " + resultLSB);
		
		/*Mat[] imagesLSB = new Mat[100];
		
		for(int i = 0; i < 100; i++) {
			imagesLSB[i] = Imgcodecs.imread(location);
			Mat resLSB = LSB.encodeImage(imagesLSB[i], "SuperTajnaWiadomosc");
			String source = "resources/compressionLSB/lena_res_" + (i + 1) + ".png";
			Imgcodecs.imwrite(source, resLSB);
//			String resultLSB = LSB.decodeImage(resLSB);
		}*/
		/*Mat[] resultsLSB = new Mat[100];
		for(int i = 0; i < 1; i++) {
			String source = "resources/compressionLSB/results/lena_res_" + (i + 1) + "-min.png";
			resultsLSB[i] = Imgcodecs.imread(source);
			String resultLSB = LSB.decodeImage(resultsLSB[i]);
			zapis.println(resultLSB);
		}*/
		
		/*Mat[] imagesALT = new Mat[100];
		
		for(int i = 20; i < 30; i++) {
			imagesALT[i] = Imgcodecs.imread(location);
			Mat resALT = ALT.encodeImage(imagesALT[i], msg);
			Mat resDist = Distortion.distortImage(resALT, 1);
			String source = "resources/distortionALT/lena_res_" + (i + 1) + ".png";
			Imgcodecs.imwrite(source, resDist);
		}
		
		Mat[] resultsALT = new Mat[100];
		for(int i = 20; i < 30; i++) {
			String source = "resources/distortionALT/lena_res_" + (i + 1) + ".png";
			resultsALT[i] = Imgcodecs.imread(source);
			String resultALT = ALT.decodeImage(resultsALT[i]);
			zapisALT.println(resultALT);
			System.out.println("Result ALT: " + resultALT);
			
			int diffCount = 0;
			for(int j = 0; j < msg.length(); j++) {
				if(msg.charAt(j) != resultALT.charAt(j))
					diffCount++;
			}
			diffCountALT.println(diffCount);			
		}*/
		
		Mat[] imagesLSB = new Mat[100];
		
		for(int i = 20; i < 30; i++) {
			imagesLSB[i] = Imgcodecs.imread(location);
			Mat resLSB = LSB.encodeImage(imagesLSB[i], msg);
			Mat resDist = Distortion.distortImage(resLSB, 1);
			String source = "resources/distortionLSB/lena_res_" + (i + 1) + ".png";
			Imgcodecs.imwrite(source, resDist);
		}
		
		Mat[] resultsLSB = new Mat[100];
		for(int i = 20; i < 30; i++) {
			String source = "resources/distortionLSB/lena_res_" + (i + 1) + ".png";
			resultsLSB[i] = Imgcodecs.imread(source);
			String resultLSB = LSB.decodeImage(resultsLSB[i]);
			resultLSB = resultLSB.substring(0, msg.length());
			zapisLSB.println(resultLSB);
			System.out.println("Result LSB: " + resultLSB);
			
			int diffCount = 0;
			for(int j = 0; j < msg.length(); j++) {
				if(msg.charAt(j) != resultLSB.charAt(j))
					diffCount++;
			}
			diffCountLSB.println(diffCount);			
		}
	
		zapisALT.close();
		zapisLSB.close();
		diffCountALT.close();
		diffCountLSB.close();
	}
}
