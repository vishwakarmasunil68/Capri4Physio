package com.capri4physio.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ImageUtil {



	/**
	 * getExifOrientation -- Roate the image on the right angel
	 * @param filepath -- path of the file to be rotated
	 * @return
	 */
	public static int getExifOrientation(String filepath) {

		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}

			}
		}
		return degree;
	}

	public static int[] getExifWidth(String file) {
		String filepath = "";
		filepath = Environment.getExternalStorageDirectory()
				+ "/LaterApp/" + file;
		int[] dimen = new int[2];
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
		}
		if (exif != null) {
			dimen[0] = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, -1);
			dimen[1] = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, -1);
		}

		return dimen;
	}

	// Rotates the bitmap by the specified degree.
	// If a new bitmap is created, the original bitmap is recycled.
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
			}
		}
		return b;
	}

	public static int[] decodePhotoSize(String file_name) {
		String path = "";
		path = Environment.getExternalStorageDirectory()
				+ "/LaterApp/" + file_name;
		File infile = new File(path);
		// Decode image size
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(infile), null, o);

			return new int[] { o.outWidth, o.outHeight };
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new int[] { 0, 0 };
	}

	public static Bitmap decodePhoto(String file_name, int max_width,
			int max_height) {

		String path = "";
		path = Environment.getExternalStorageDirectory()
				+ "/LaterApp/" + file_name;
		File infile = new File(path);
		Bitmap bm = null;
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			// BitmapFactory.decodeFile(path, o);
			try {
				BitmapFactory
						.decodeStream(new FileInputStream(infile), null, o);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int scale = 1;

			double vertical_scale = 0;
			double horizontal_scale = 0;
			if (max_height > 0 && o.outHeight > max_height) {
				vertical_scale = o.outHeight / (double) max_height;
			}
			if (max_width > 0 && o.outWidth > max_width) {
				horizontal_scale = o.outWidth / (double) max_width;
			}

			if (horizontal_scale > 0 || vertical_scale > 0) {
				scale = (int) Math.floor(Math.max(horizontal_scale,
						vertical_scale));
			}
			// Log.d("TEST", "SCALE " + scale);

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;

			o2.inPreferredConfig = Bitmap.Config.ARGB_8888;
			try {
				bm = BitmapFactory.decodeStream(new FileInputStream(infile),
						null, o2);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int degrees = getExifOrientation(file_name);
			bm = rotate(bm, degrees);

			return bm;

		} catch (OutOfMemoryError ex) {

			return null;
		}

	}
	
	public static Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 50;

			// Find the correct scale value. It should be the power of 2.
			int scale = 2;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = 8;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
	
	
	public static byte[] getByteFromBitmap(Context context, String picturePath) {
		byte[] byteArray=null;
		try {
			if (picturePath == null || picturePath.equals("")) {
				return null;
			}
			int toSample = 1;
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2; 
			Bitmap mBitmap = BitmapFactory.decodeFile(picturePath,options);
		
			int imageWidth = mBitmap.getWidth();
			if (imageWidth > 700 && imageWidth < 1500) {

				toSample = 2;
			} else if (imageWidth > 1500 && imageWidth < 2500) {

				toSample = 4;
			}
			else if (imageWidth > 2500 && imageWidth < 4500) {

				toSample = 6;
			}

			else if (imageWidth > 4500&&imageWidth < 6500) {

				toSample = 8;
			}
			
			else if (imageWidth > 6500) {

				toSample = 10;
			}

			DisplayMetrics metrix = context.getResources().getDisplayMetrics();

			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inDither = false;
			opts.inSampleSize = toSample;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			opts.inTempStorage = new byte[16 * 1024];

			//
			ExifInterface exif = new ExifInterface(picturePath);
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 1);

			//
			Bitmap scaledBitmap = BitmapFactory.decodeFile(picturePath, opts);
			int ws=scaledBitmap.getWidth();
			scaledBitmap = getRotation(scaledBitmap, orientation);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					byteArrayOutputStream);
			byteArray = byteArrayOutputStream.toByteArray();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteArray;
	}
	
	
	
	public static Bitmap getRotation(Bitmap bitmap, int orientation) {

		try {

			Log.e(orientation + "", "Image orientation >>>>>> " + orientation);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				bitmap = rotate(bitmap, 90);
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				bitmap = rotate(bitmap, 180);
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				bitmap = rotate(bitmap, 270);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}


	public static String getStorageDirectory(){
		String sdCardPath = "";

		Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (isSDPresent) {
			sdCardPath = Environment.getExternalStorageDirectory().toString();
		} else {
			sdCardPath = Environment.getRootDirectory().toString();
		}

		return sdCardPath;
	}

	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
		return imageEncoded;
	}

	public static Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
}
