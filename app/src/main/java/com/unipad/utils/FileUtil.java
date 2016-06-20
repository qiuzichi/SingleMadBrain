package com.unipad.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;


import android.content.Context;
import android.os.Environment;

public class FileUtil {
	private static final String TAG = "FileUtil";

	public static File getCacheFile(String imageUri) {
		File cacheFile = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File sdCardDir = Environment.getExternalStorageDirectory();
			String fileName = getFileName(imageUri);
			File dir = new File(sdCardDir.getPath() + "/download");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			cacheFile = new File(dir, fileName);
			// Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir +
			// ",file:" + fileName);
		}
		return cacheFile;
	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}

	// 判断手机是否有SD卡
	public static boolean hasSDCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public static String getPath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getPath() + "/MadBrain";
		} else {
			return "";
		}
	}

	/**
	 *   * 得到amr的时长   * @param file   * @return   * @throws IOException
	 * 
	 * @throws Exception
	 */
	public static long getAmrDuration(File file) throws Exception {
		long duration = -1;
		int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0,
				0, 0 };
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
			long length = file.length();// 文件的长度  
			int pos = 6;// 设置初始位置  
			int frameCount = 0;// 初始帧数  
			int packedPos = -1;

			byte[] datas = new byte[1];// 初始数据值  
			while (pos <= length) {
				randomAccessFile.seek(pos);
				if (randomAccessFile.read(datas, 0, 1) != 1) {
					duration = length > 0 ? ((length - 6) / 650) : 0;
					break;
				}
				packedPos = (datas[0] >> 3) & 0x0F;
				pos += packedSize[packedPos] + 1;
				frameCount++;
			}
			duration += frameCount * 20;// 帧数*20  
		} finally {
			if (randomAccessFile != null) {
				randomAccessFile.close();
			}
		}
		return duration;
	}

	public static void createDir() {
		File destDir = new File(getPath());
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}

	// 读写/data/data/<应用程序名>目录上的文件
	// 写数据
	public static void writeFile(Context context, String fileName,
			String writeStr) {
		try {
			FileOutputStream fout = context.openFileOutput(fileName,
					context.MODE_PRIVATE);
			byte[] bytes = writeStr.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	// 读数据
//	public static String readFile(Context context, String fileName) {
//		String res = "";
//		try {
//			FileInputStream fin = context.openFileInput(fileName);
//			int length = fin.available();
//			byte[] buffer = new byte[length];
//			fin.read(buffer);
//			res = EncodingUtils.getString(buffer, "UTF-8");
//			fin.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return res;
//	}

	/**
	 * 将assets目录下的资源文件复制到SD卡中
	 * 
	 * @param context
	 * @param strAssetsFilePath
	 * @param strDesFilePath
	 * @return
	 */
	public static boolean assetsCopyData(Context context,
			String strAssetsFilePath, String strDesFilePath) {
		boolean bIsSuc = true;
		InputStream inputStream = null;
		OutputStream outputStream = null;

		File file = new File(strDesFilePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
				Runtime.getRuntime().exec("chmod 766" + file);
			} catch (Exception e) {
				bIsSuc = false;
			}
		} else {// 存在
			return true;
		}

		try {
			inputStream = context.getAssets().open(strAssetsFilePath);
			outputStream = new FileOutputStream(file);

			int nLen = 0;
			byte[] buff = new byte[1024];
			while ((nLen = inputStream.read(buff)) > 0) {
				outputStream.write(buff, 0, nLen);
			}
			// 完成
		} catch (Exception e) {
			bIsSuc = false;
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
				bIsSuc = false;
			}
		}
		return bIsSuc;
	}

	/**
	 * 写数据到SD中的文件
	 * 
	 * @param fileName
	 * @param write_str
	 */
	public static void writeFileSdcardFile(String fileName, String write_str) {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			byte[] bytes = write_str.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyBigDataToSD(Context context, String strOutFileName)
			throws IOException {
		InputStream myInput = null;
		OutputStream myOutput = new FileOutputStream(strOutFileName);
		myInput = context.getAssets().open("yphone.zip");
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
}
