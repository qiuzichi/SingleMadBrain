package com.unipad.common.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class LoaderAdapter<T> extends BaseAdapter {
    private Context mContext;
    protected LayoutInflater mLayInflater;
    protected int mItemLayout;
    protected Locale mLocale;
    protected ArrayList<T> mDatas = new ArrayList<T>();
    protected OnClickListener mItemClickListener;

    public LoaderAdapter(Context context, int resId) {
        mContext = context;
        mItemLayout = resId;
        mLayInflater = LayoutInflater.from(mContext);
        mLocale = context.getResources().getConfiguration().locale;
    }
//delete all the Imagework unsed code.

/* DTS2014121901077   qiuhongwei/00227094 20141219 end */
    public Context getContext() {
        return mContext;
    }

    public void addData(List<T> infos) {
        if (null == infos) {
            return;
        }
        if (null == mDatas) {
            mDatas = new ArrayList<T>();
        }
        int count = infos.size();
        for (int i = 0; i < count; i++) {
            T info = infos.get(i);
            if (!mDatas.contains(info)) {
                mDatas.add(info);
            } else {
                int index = mDatas.indexOf(info);
                if (index != -1) {
                    mDatas.remove(info);
                    mDatas.add(index, info);
                }
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<T> getDatas() {
        return mDatas;
    }
    
    public void setData(List<T> infos) {



        mDatas = new ArrayList<T>();
        if(null != infos){
            mDatas.addAll(infos);
        } else {
            Log.e("","list is null");
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (null != mDatas) {
            mDatas.clear();
        }
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mLayInflater.inflate(mItemLayout, parent,false);
        }
        
        T info = getItem(position);
  
        bindView(convertView,info,position);
        
        return convertView;
    }

    public void setOnItemClickListener(OnClickListener listener){
        mItemClickListener = listener;
    }
    
    public void bindView(View convertView, T info,int position) {

    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        //not use this method.it is no use.
        int count = getCount();
        if (mDatas == null || position >= count) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
