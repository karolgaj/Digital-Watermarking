package markwatering;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class WaterMarking {

	public static void main(String[] arg) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String location = "resources/lena_std.tif";
		Mat image = Imgcodecs.imread(location);
		Mat resImage = ALT.encodeImage(image, "1234testasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf");
		Mat resImage2 = LSB.encodeImage(image, "SuperTajnaWiadomosc");
		Imgcodecs.imwrite("resources/lena_res.tif", resImage);
		String result = ALT.decodeImage(resImage);
		String result2 = LSB.decodeImage(resImage2);
		System.out.println(result);
		System.out.println(result2);
	}
}
