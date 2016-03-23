package com.confress.lovewall.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

public class PhotoViewUtils {
	
	private static String image_view1_path;
	
	public static Uri saveBitmap(Bitmap bm, String imageview_path) {
		Bitmap map = Bitmap.createScaledBitmap(bm, 600, 800, true);
		File tmpDir = new File(Environment.getExternalStorageDirectory()
				+ "/com.wuhanapp." + imageview_path);
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String photo_name1 = "icon" + System.currentTimeMillis() + ".png";
		image_view1_path = tmpDir.getAbsolutePath() + "/" + photo_name1;
		File img = new File(image_view1_path);
		try {
			FileOutputStream fos = new FileOutputStream(img);
			map.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			fos.flush();
			fos.close();
			return Uri.fromFile(img);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			bm.recycle();
			map.recycle();
		}
	}
	
	
	
	public static String getImagePath() {
		return image_view1_path;
	}

	public static Uri convertUri(Uri uri, String imageview_path,Context context) {
		InputStream in = null;
		Bitmap bitmap=null;
		try {
			in = context.getContentResolver().openInputStream(uri);
			bitmap= BitmapFactory.decodeStream(in);
			in.close();
			return saveBitmap(bitmap, imageview_path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally{
			bitmap.recycle();
		}
	   }
}
