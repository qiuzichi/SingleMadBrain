package com.unipad.brain.home.dao;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.unipad.utils.FileUtil;
import com.unipad.utils.ToastUtil;

import java.io.File;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class LoadService extends Service {
	private static Context context;


	// 在服务中 更新软件
	public static Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case 1:
				String path = (String) msg.obj;
				load(path);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		receiver = new DownloadCompleteReceiver();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	static DownloadManager manager;
	static DownloadCompleteReceiver receiver;

	/* 下载 */
	@SuppressWarnings("deprecation")
	private static void load(String path) {
		String apkName = path.substring(path.lastIndexOf("/") + 1,
				path.length());
		DownloadManager.Request down = new DownloadManager.Request(
				Uri.parse(path));
		// 设置允许使用的网络类型，这里是移动网络和wifi都可以
		down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
				| DownloadManager.Request.NETWORK_WIFI);
		down.setTitle("正在下载" + apkName);
		// 禁止发出通知，既后台下载
		down.setShowRunningNotification(true);
		// 不显示下载界面
		down.setVisibleInDownloadsUi(true);
		// 设置下载后文件存放的位置 Environment.getExternalStorageDirectory().getPath() +
		down.setDestinationInExternalPublicDir("MLC/upload", apkName);
		// 将下载请求放入队列
		manager.enqueue(down);
		try {
			context.registerReceiver(receiver, new IntentFilter(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		} catch (Exception e) {
		}
	}

	// 接受下载完成后的intent
	class DownloadCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
				String fileName = "";
				DownloadManager.Query query = new DownloadManager.Query();
				query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);// 设置过滤状态：成功
				Cursor c = manager.query(query);// 查询以前下载过的‘成功文件’
				if (c.moveToFirst()) {// 移动到最新下载的文件
					fileName = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
				}
				File f = new File(fileName.replace("file://", ""));// 过滤路径
					try {
						if (f.exists()) {
							FileUtil.openFile(f, context);
						} else {
							ToastUtil.showToast("下载失败");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * 打开文件
	 * 
	 * @param file
	 */
	private void openFile(File file) {
		// Uri uri = Uri.parse("file://"+file.getAbsolutePath());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型+
		String type = "application/vnd.android.package-archive";
		// 设置intent的data和Type属性。
		intent.setDataAndType(Uri.fromFile(file), type);
		// 跳转
		try {
			startActivity(intent);

		} catch (Exception e) {

		}
		//关闭服务
		stopSelf();
		context.unregisterReceiver(receiver);
	}
}
