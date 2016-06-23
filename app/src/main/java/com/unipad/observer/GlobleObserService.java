package com.unipad.observer;

import android.os.Message;

import com.unipad.AppContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gongkan on 2016/6/2.
 */
public  abstract class GlobleObserService {

    protected HashMap<Integer,List<IDataObserver>> observerMap = new HashMap<>();

    public void registerObserver(int key, IDataObserver obj)
    {
        List<IDataObserver> observerList = observerMap.get(key);

        if (null == observerList)
        {
            observerList = new ArrayList<IDataObserver>();
            observerMap.put(key, observerList);

        }
        if (!observerList.contains(obj))
        {
            observerList.add(obj);
        }
    }

    public void unRegisterObserve(int key, IDataObserver listener)
    {
        List<IDataObserver> list = observerMap.get(key);
        if (null != list)
        {
            list.remove(listener);
        }
    }

    /**
     * 去除注册的监听器
     * @param key
     * @param listener
     */
    public synchronized void unregistDataChangeListenerObj(IDataObserver listener)
    {
        Iterator<Map.Entry<Integer, List<IDataObserver>>> iters = observerMap.entrySet().iterator();
        while (iters.hasNext())
        {
            HashMap.Entry<Integer, List<IDataObserver>> entry = (HashMap.Entry<Integer, List<IDataObserver>>) iters
                    .next();

            List<IDataObserver> observers = entry.getValue();

            if (null == observers)
            {
                continue;
            }

            int loc = -1;
            for (int i = 0; i < observers.size(); i++)
            {
                if (listener == observers.get(i))
                {
                    loc = i;
                    break;
                }
            }
            if (loc != -1)
            {
                observers.remove(loc);
            }
        }
    }
    /**
     * 处理数据变化了，通知界面数据进行更新
     * @param key
     */
    public void noticeDataChange(Integer key, Object obj)
    {
        List<IDataObserver> observerList = observerMap.get(key);
        if (observerList == null)
        {
            return;
        }
        BeenObser msgData;
        GlobleHandle msgHandler = AppContext.instance().globleHandle;
        Message msg;
        for (IDataObserver uiView : observerList)
        {
            // 判断View是否显示出来
            if (null == uiView)
            {
                continue;
            }
            msgData = new BeenObser(obj,key,uiView);
            msg = Message.obtain();
            msg.obj = msgData;
            msgHandler.sendMessage(msg);
        }
    }

}
