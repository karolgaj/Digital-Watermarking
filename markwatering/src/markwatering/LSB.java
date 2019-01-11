package markwatering;

import org.opencv.core.Mat;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LSB {
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
		int bit_count = 0;
		int encoded_text_size = textToEncode.length();
		byte data[] = new byte[3];
		for (int i = 0; i < inImage.cols(); i++) {
			for (int j = 0; j < inImage.rows(); j++){
				inImage.get(j, i, data);

				for (int k = 0; k < data.length; k++) {
					byte c = data[k];
					 
					if (bit_count < 32) {	// pierwsze 32 bity na rozmiar wiadomosci
						byte v1 = (byte) ((encoded_text_size >> bit_count) & 1);	// rzutowanie textsize do byte
						c = (byte) (c & (0xFE));	// zerowanie ostatniego bitu
						c = (byte) (c | (v1));		// nadpisywanie c bit po bicie
					} else {
						int curPos = bit_count - 32;
						int curBit = curPos % 8;
						int curChar = curPos / 8;
						if (curChar < encoded_text_size) {
//							System.out.println("Encoding!");
							byte v1 = (byte) ((textToEncode.charAt(curChar) >> curBit) & 1);
//							System.out.println(v1);
//							System.out.println(c);
							c = (byte) (c & (0xFE));
							c = (byte) (c | (v1));
//							System.out.println(c);
						}
					}
					data[k] = c;
					bit_count++;
				}


				inImage.put(j, i, data);
			}
		}
		System.out.println("LSB encoding done!");
		return inImage;
	}

	public static String decodeImage(Mat inImage) {
		int bit_count = 0;
		List<Byte> decoded_string = new ArrayList<Byte>();
		int encoded_text_size = 0;
		byte r = 0;
		byte data[] = new byte[3];
		for (int i = 0; i < inImage.cols(); i++) {
			for (int j = 0; j < inImage.rows(); j++){
				inImage.get(j, i, data);
				
				for (int k = 0; k < data.length; k++) {
					byte c = data[k];
					if (bit_count < 32) {
						encoded_text_size = encoded_text_size | ((c & 1) << bit_count);
					} else {
						int curPos = bit_count - 32;
						int curBit = curPos % 8;
						int curChar = curPos / 8;
						if (curChar < encoded_text_size) {
							if (curBit == 0) {
								r = 0;
								decoded_string.add(r);
							}
							r = (byte) (r | ((c & 1) << curBit));
							decoded_string.set(curChar, r);
						}
					}
					bit_count++;
				}
			}
		}
		System.out.println("LSB decoding done!");
		return byteListToString(decoded_string, StandardCharsets.UTF_8);
	}
	
}
