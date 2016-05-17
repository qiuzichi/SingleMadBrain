package com.unipad.common.adapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public class ListGridAdapter<T> extends LoaderAdapter<T>{

    public int mLineCount = 5;

    public ListGridAdapter(Context context,int resId) {
        this(context,resId,3);
    }
    
    public ListGridAdapter(Context context,int resId,int lineCount) {
        super(context,resId);

            mLineCount = lineCount;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mLayInflater.inflate(mItemLayout, null);
        }
        
        int rPosition = position * mLineCount;
        ViewGroup parentView = (ViewGroup)convertView;
        Log.e("","getView()");
        for (int i = 0; i < parentView.getChildCount(); i++) {
            int newPosition = rPosition + i;
            T info = getItem(newPosition);
            bindView(parentView.getChildAt(i), info);
        }
        return convertView;
    }

    public void bindView(View convertView, T info) {
        if (info == null) {
            convertView.setVisibility(View.INVISIBLE);
        }else{
            convertView.setVisibility(View.VISIBLE);
        }
        convertView.setTag(info);
        //convertView.setOnClickListener(mItemClickListener);
    }

    @Override
    public int getCount() {
        // return mDatas == null ? 0 : mDatas.size();
        int num = mDatas == null ? 0 : mDatas.size();
        num = (num + mLineCount - 1) / mLineCount;
        return num;
    }
    
    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        int num = mDatas == null ? 0 : mDatas.size();
        if(null != mDatas && position < num){
            return mDatas.get(position);
        }
        return null;
    }

}