package markwatering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Core

;

import org.opencv.core.Core;


public class ALT {
	
	private static String byteListToString(List<Byte> l, Charset charset) {
	    if (l == null) {
	        return "";
	    }
	    byte[] array = new byte[l.size()];
	    int i = 0;
	    for (Byte current : l) {
	        array[i] = current;
	        i++;
	    }
	    return new String(array, charset);
	}

	public static Mat encodeImage(Mat inImage, String textToEncode) {

//		System.out.println(inImage.cols());
//		System.out.println(inImage.rows());
		int bit_count = 0;
		int encoded_text_size = textToEncode.length();
		byte data [] = new byte[3];
		done:
		for (int x = 0; x+4 < inImage.cols(); x+=4) {
			for (int y = 0; y+4 < inImage.rows(); y+=4) {
				Rect r = new Rect(x,y,4, 4);
				Mat proxy = new Mat(inImage, r);
				double[] m = Core.mean(proxy).val;
				//System.out.println(m.length);
//				System.out.println(m[0]);
				for (int i = 0; i < proxy.cols(); i++) {
					for (int j = 0; j < proxy.rows(); j++){
						proxy.get(i, j, data );
						if(bit_count < 32) {
							int v1 = ((encoded_text_size >> bit_count) & 1);
							if(v1 == 0) {
								if(m[0] > 128) {
									data[0] = (byte) (data[0] - (m[0] - 128 - 1));
									data[1] = (byte) (data[1] - (m[0] - 128 - 1));
									data[2] = (byte) (data[2] - (m[0] - 128 - 1));
								}
							}else {
								if(m[0] < 128) {
									data[0] = (byte) (data[0] + (128 - m[0] + 1));
									data[1] = (byte) (data[1] + (128 - m[0] + 1));
									data[2] = (byte) (data[2] + (128 - m[0] + 1));
								}
							}
						}else {
							int curPos = bit_count-32;
							int curBit = curPos%8;
							int curChar = curPos/8;
							if( curChar < encoded_text_size)
							{
								int v1 =  ((textToEncode.charAt(curChar) >> curBit) & 1);
								if(v1 == 0) {
									if(m[0] > 128) {
										data[0] = (byte) (data[0] - (m[0] - 128 - 1));
										data[1] = (byte) (data[1] - (m[0] - 128 - 1));
										data[2] = (byte) (data[2] - (m[0] - 128 - 1));
									}
								}else{
									if(m[0] < 128) {
										data[0] = (byte) (data[0] + (128 - m[0] + 1));
										data[1] = (byte) (data[1] + (128 - m[0] + 1));
										data[2] = (byte) (data[2] + (128 - m[0] + 1));
									}
								}
							}else {
								break done;
							}
						}
						

						proxy.put(i, j, data);
					}
				}
				bit_count++;
				m = Core.mean(proxy).val;
				//System.out.println(m.length);
//				System.out.println(m[0]);
			}
		}

		System.out.println("ALT encoding done!");
		return inImage;
	}
	public static String decodeImage(Mat inImage) {

//		System.out.println(inImage.cols());
//		System.out.println(inImage.rows());
		int bit_count = 0;
		List<Byte> decoded_string = new ArrayList<Byte>();
		int encoded_text_size = 0;
		byte chr = 0;
		byte data [] = new byte[3];
		done:
		for (int x = 0; x+4 < inImage.cols(); x+=4) {
			for (int y = 0; y+4 < inImage.rows(); y+=4) {
				Rect r = new Rect(x,y,4, 4);
				Mat proxy = new Mat(inImage, r);
				double[] m = Core.mean(proxy).val;
				//System.out.println(m.length);
//				System.out.println(m[0]);
				int c = 0;
				if(m[0] > 128) {
					c = 1;
				}
				if(bit_count < 32)
				{
					encoded_text_size = encoded_text_size | ((c & 1) << bit_count);
				}
				else
				{
					int curPos = bit_count-32;
					int curBit = curPos%8;
					int curChar = curPos/8;
					if( curChar < encoded_text_size)
					{
						if( curBit == 0) {
							chr = 0;
							decoded_string.add(chr);
						}
						chr = (byte) (chr | ((c & 1) << curBit));
						decoded_string.set(curChar, chr);
					}else {
						break done;
					}
				}
				bit_count++;
				m = Core.mean(proxy).val;
				//System.out.println(m.length);
//				System.out.println(m[0]);
			}
		}

		System.out.println("ALT decoding done!");
		return byteListToString(decoded_string, StandardCharsets.UTF_8);
	}
}
