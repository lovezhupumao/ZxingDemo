package com.zpm.zxinglib;

import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;

public class EncoderUtil {

	public static Bitmap getStringQRcodeWithoutLogo(String string, int width,
			int height) {
		

		Bitmap bitmap;
		BitMatrix bitMatrix =  getBitMatrix(string, width, height);
		if (bitMatrix!=null) {
			bitmap = Bitmap.createBitmap(bitMatrix.getWidth(),	bitMatrix.getHeight(), Config.RGB_565);
			for (int x = 0; x < bitMatrix.getWidth(); x++) {
				for (int y = 0; y < bitMatrix.getHeight(); y++) {
					bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000
							: 0xFFCCDDEE);

				}
			}
			return bitmap;
		} else {
			return null;
		}
		
	}

	public static Bitmap getStringQRcodeWithLogo(String string, int width, int height,
			Bitmap logo) {	

		Bitmap bitmap;
		BitMatrix bitMatrix = getBitMatrix(string, width, height);		
		if (bitMatrix!=null) {
			bitmap=createImage(bitMatrix, width, height, logo);
			return bitmap;
		} else {
			return null;
		}
		
	}

	private static Bitmap createImage(BitMatrix bitMatrix, int w, int h,	Bitmap logo) {
		Bitmap scaleLogo = getScaleLogo(logo, w, h);
		int offsetX = (w - scaleLogo.getWidth()) / 2;
		int offsetY = (h - scaleLogo.getHeight()) / 2;

		int[] pixels = new int[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (x >= offsetX && x < offsetX + scaleLogo.getWidth()
						&& y >= offsetY && y < offsetY + scaleLogo.getHeight()) {
					int pixel = scaleLogo.getPixel(x - offsetX, y - offsetY);
					if (pixel == 0) {
						if (bitMatrix.get(x, y)) {
							pixel = 0xff000000;
						} else {
							pixel = 0xffffffff;
						}
					}
					pixels[y * w + x] = pixel;
				} else {
					if (bitMatrix.get(x, y)) {
						pixels[y * w + x] = 0xff000000;
					} else {
						pixels[y * w + x] = 0xffffffff;
					}
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
		return bitmap;

	}
	private static BitMatrix getBitMatrix(String string,int w, int h) {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// ����QR��ά��ľ�����HΪ��߼��𣩾��弶����Ϣ
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// ���ñ��뷽ʽ
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);
		BitMatrix bitMatrix;
		try {
			bitMatrix = new MultiFormatWriter().encode(string,	BarcodeFormat.QR_CODE, w, h, hints);
			return bitMatrix;
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	private static Bitmap getScaleLogo(Bitmap logo, int w, int h) {
		if (logo == null)
			return null;
		Matrix matrix = new Matrix();
		float scaleFactor = Math.min(w * 1.0f / 5 / logo.getWidth(), h * 1.0f
				/ 5 / logo.getHeight());
		matrix.postScale(scaleFactor, scaleFactor);
		Bitmap result = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),
				logo.getHeight(), matrix, true);
		return result;
	}

}
