package com.unipad.brain.longPoker.adapter;

import android.content.Context;

public class NormalSpinerAdapter extends AbstractSpinerAdapter<String>{

	public  NormalSpinerAdapter(Context context){
		super(context);
		mObjects.add("");
		mObjects.add("A");
		mObjects.add("2");
		mObjects.add("3");
		mObjects.add("4");
		mObjects.add("5");
		mObjects.add("6");
		mObjects.add("7");
		mObjects.add("8");
		mObjects.add("9");
		mObjects.add("10");
		mObjects.add("J");
		mObjects.add("Q");
		mObjects.add("K");
	}
}
