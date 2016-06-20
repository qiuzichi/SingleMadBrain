package com.unipad.io.w;

import android.os.Handler;
import android.text.TextUtils;

import com.unipad.io.IDataHandler;
import com.unipad.io.IPack;
import com.unipad.io.IWrite;
import com.unipad.io.bean.Request;
import com.unipad.io.mina.LongTcpClient;


public class SocketThreadManager implements IDataHandler
{
	
	private static SocketThreadManager s_SocketManager = null;
	

	private SocketOutputThread mOutThread = null;
	

	
	// 获取单例
	public static SocketThreadManager sharedInstance()
	{
		if (s_SocketManager == null)
		{
			s_SocketManager = new SocketThreadManager();
			s_SocketManager.startThreads();
		}
		return s_SocketManager;
	}
	
	// 单例，不允许在外部构建对象
	private SocketThreadManager()
	{
		mOutThread = new SocketOutputThread();
	}
	
	/**
	 * 启动线程
	 */
	
	private void startThreads()
	{
		mOutThread.start();
		// mDnsthread.start();
	}
	
	/**
	 * stop线程
	 */
	public void stopThreads()
	{
		mOutThread.setStart(false);
	}
	
	public static void releaseInstance()
	{
		LongTcpClient.instant().release();

		if (s_SocketManager != null)
		{
			s_SocketManager.stopThreads();
			s_SocketManager = null;
		}
	}
	

	public void sendMsg(Request request)
	{

		mOutThread.addMsgToSendList(request);
	}

	@Override
	public void processPack(IPack pack, IWrite writer) {

	}

	public void clear(){

	}
}
