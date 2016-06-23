package com.unipad.brain.home.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.unipad.brain.R;
import com.unipad.brain.dialog.LoadingDialog;


public class MyTools {
	
	//本方法判断自己些的一个Service-->com.odier.mobile.service.LocationService是否已经运行  
	public static boolean isWorked(Context context) {  
	  ActivityManager myManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
	  ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
	  for(int i = 0 ; i<runningService.size();i++)  
	  {  
	    if(runningService.get(i).service.getClassName().toString().equals("com.odier.mobile.service.LocationService"))  
	  {  
	    return true;  
	   }  
	  }  
	  return false;  
	 }

	public static ProgressDialog showDialog(Context context) {
		ProgressDialog progressDialog = ProgressDialog.show(context, "",
				"正在获取数据....", true, false);
		return progressDialog;
	}
	
	public static void showToast(Context context,String msg){
		Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
	}
	
	public static LoadingDialog showProgressDialog(Context context,String msg){
		LoadingDialog dialog = new LoadingDialog(context, msg);
		return dialog;
	}
	

	public static void errorMsgShow(final Activity activity, String msg) {
		new AlertDialog.Builder(activity).setIcon(R.drawable.ic_launcher)
				.setTitle("提示").setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						activity.finish();
					}
				}).show();
	}
	
	
	public static void errorMsgShow(final Context activity, String msg) {
		new AlertDialog.Builder(activity).setIcon(R.drawable.ic_launcher)
				.setTitle("提示").setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();
	}
	
	
	public static Bitmap loadImageFromNet(String url) {
		Bitmap bitmap = null;


		InputStream is = null;
		FileOutputStream fos = null;
		try {
			//构建图片的url地址
			URL ur = new URL(url);
			HttpURLConnection urlConn=(HttpURLConnection)ur.openConnection();;

			//设置超时的时间，5000毫秒即5秒
			urlConn.setConnectTimeout(5000);
			//设置获取图片的方式为GET
			urlConn.setRequestMethod("GET");
			//响应码为200，则访问成功
			if (urlConn.getResponseCode() == 200) {
				//获取连接的输入流，这个输入流就是图片的输入流
				is = urlConn.getInputStream();
				return bitmap;
				/*//构建一个file对象用于存储图片
				File file = new File(Environment.getExternalStorageDirectory(), "pic.jpg");
				fos = new FileOutputStream(file);
				int len = 0;
				byte[] buffer = new byte[1024];
				//将输入流写入到我们定义好的文件中
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}*/
				//将缓冲刷入文件
				//fos.flush();
				//告诉handler，图片已经下载成功
				//handler.sendEmptyMessage(LOAD_SUCCESS);
			}
		} catch (Exception e) {
			//告诉handler，图片已经下载失败
			//handler.sendEmptyMessage(LOAD_ERROR);
			e.printStackTrace();
		} finally {
			//在最后，将各种流关闭
			try {
				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				//handler.sendEmptyMessage(LOAD_ERROR);
				e.printStackTrace();
			}
		}







		/*HttpURLConnection client = AndroidHttpClient.newInstance("Android");
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		HttpConnectionParams.setSocketBufferSize(params, 3000);
		HttpResponse response = null;
		InputStream inputStream = null;
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url);
			response = client.execute(httpGet);
			int stateCode = response.getStatusLine().getStatusCode();
			if (stateCode != HttpStatus.SC_OK) {
				return bitmap;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					inputStream = entity.getContent();
					return bitmap = BitmapFactory.decodeStream(inputStream);
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (ClientProtocolException e) {
			httpGet.abort();
			e.printStackTrace();
		} catch (IOException e) {
			httpGet.abort();
			e.printStackTrace();
		} finally {
			((AndroidHttpClient) client).close();
		}*/
		return bitmap;
	}



	//下载图片的主方法
	private void getPicture() {

		URL url = null;

	}
	
	
	/**
	 * 把resId对应的图片转换成bitmap对象
	 */
	public static Bitmap readBitMap(int resId,Context context){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable=true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is,null,opt);	
	}
	
	/**
	 * 把字节(byte)长度转成kb(千字节)或mb(兆字节) 
	 */
	public static String toKBorMB(long bytes) {  
        BigDecimal filesize = new BigDecimal(bytes);  
        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        if (returnValue > 1)  
            return (returnValue + "MB");  
        BigDecimal kilobyte = new BigDecimal(1024);  
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        return (returnValue + "KB");  
    } 

	
	public static File ImageCache(String name) {
		File cache = new File(Environment.getExternalStorageDirectory(), name);
		if (!cache.exists()) {
			cache.mkdirs();
		}
		return cache;
	};
	
	public static Bitmap downloadDrawable(String strUri) {
		Bitmap bitmap = null;
		byte[] buf = downloadByte(strUri);
		if (buf != null) {
			bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
		}
		return bitmap;
	}
	
	public static Bitmap downloadDrawable2(String strUri) {
		Bitmap bitmap = null;
		try {
			byte[] buf = read(getInStream(strUri));
			if (buf != null) {
				bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 根据请求路径获取流
	 * @param path
	 * @return
	 */
	public static InputStream getInStream(String path){
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			int returnCode = conn.getResponseCode();
			System.out.println(returnCode);
			if (conn.getResponseCode() == 200) {
				return conn.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	
	/**
	 * 读取流中的数据
	 * @param inStream
	 * @throws Exception
	 */
	public static byte[] read(InputStream inStream) throws Exception {
		if (inStream == null) {
			return null;
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
//		inStream.read(buffer);//返回读取到的数据长度，当返回值为-1时表示数据已经读完
		
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);//边读边往内存写入数据
		}
		inStream.close();//关闭输入流
		outStream.close();
		return outStream.toByteArray();
	}
	
	public static byte[] downloadByte(String strUri) {
		byte[] ret = null;
		InputStream is = null;
		ByteArrayOutputStream byteStream = null;
		try {
			final URL url = new URL(strUri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			conn.setConnectTimeout(7000);
			
			int returnCode = conn.getResponseCode();
			LogUtils.d("返回码-----------"+returnCode);
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				byteStream = new ByteArrayOutputStream();
				final int bufSize = 1024;
				byte[] buf = new byte[bufSize];
				int readSize = 0;
				while ((readSize = is.read(buf)) > 0) {
					byteStream.write(buf, 0, readSize);
				}
				ret = byteStream.toByteArray();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ioe) {

				}
			}

			if (byteStream != null) {
				try {
					byteStream.close();
				} catch (IOException ioe) {

				}
			}
		}
		return ret;
	}
	
	/**
	 * 保存在SD卡中
	 * @param bitmap 图像数据
	 * @param file	 保存文件夹
	 * @param filename	 文件名称
	 */
	public static void storeInSD(Bitmap bitmap, File file, String filename) {
		if (bitmap == null) {
			return;
		}
		if (!file.exists()) {
			file.mkdir();
		}
		File imFile = new File(file, filename);
		try {
			imFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imFile);
			bitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * 获取当前网络连接状态
	 * 
	 * @param context
	 * @return true为网络连接状态，false为网络断开状态
	 */
	public static boolean NetState(Context context) {
		try {
			// 获取手机连接管理对象
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (manager != null) {
				// 获取网络信息对象
				NetworkInfo info = manager.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// Log.e("yao_welcome_Log", "网络连接状态获取失败", e);
		}
		return false;
	}
	
	/** sd卡状态 */
	public static enum SdcardStatus {NA, Available, DiskFull, NoSdcard};
	//  sdcard相关参数
    private static final int MIN_SDCARD_AVAILABLE_SIZE=10*1024*1024; //sdcard最小剩余空间数，如果小于，则认为sdcard空间已满，不允许操作 
  
    private SdcardStatus mSDCardStatus = SdcardStatus.NA;
    
    /**
     * 判断当前SD卡是否可写
     * @return 返回boolean值，true表示SD卡可写；false表示SD卡不可写
     */
	public boolean checkSDCardWritable() {
		boolean result = false;
		// 只在程序启动时判断一次，程序运行过程中，仅仅是获取判断结果，不再重新判断
		// 本业务中所有照片类应用必须放到sd卡上，所以不允许存储目录在data区和sd卡之间自由切换的情况
		// 用户可能在应用期间拔插sd卡，所以每次使用时候都重新判断一次；

		// 判断SD卡是否可用

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			// 增加检查sd卡剩余空间
			String sdcarddir = Environment.getExternalStorageDirectory().getAbsolutePath();
			StatFs stat = new StatFs(sdcarddir);
			int blockSize = stat.getBlockSize();
			int availBlock = stat.getAvailableBlocks();
			long availSize = (long) blockSize * (long) availBlock;

			if (availSize > MIN_SDCARD_AVAILABLE_SIZE) {

				mSDCardStatus = SdcardStatus.Available;
			}

			else {
				mSDCardStatus = SdcardStatus.DiskFull;
			}

		} else {
			mSDCardStatus = SdcardStatus.NoSdcard;
		}

		// 根据首次判断的结果，返回boolean值
		if (mSDCardStatus == SdcardStatus.Available) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	//将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
	
	/**
	 * 圆角显示图片
	 * @param roundPx 值越大角越圆
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	
	//比例缩小图片
	public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight,
			Context context) {
		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		int h = (int) (newHeight * densityMultiplier);
		int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

		photo = Bitmap.createScaledBitmap(photo, w, h, true);
		return photo;
	}
	
	/**
	 * 判断当前gps是否打开 
	 * @param context
	 * @return
	 */
	public static boolean isGPSopen(Context context){
		LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		boolean gps = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean network = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if(gps || network){
			return true;
		}
//		if(gps && network){
//			return true;
//		}
		return false;
	}
	/*
	 * 获取imei号
	 */

	public static String getImei(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();

	}
}
