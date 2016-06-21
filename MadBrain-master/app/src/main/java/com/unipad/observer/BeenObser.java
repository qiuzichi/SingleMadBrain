package com.unipad.observer;

public class BeenObser {
	private Object o;
	private int key;
	private IDataObserver obser;
	public BeenObser(Object o, int key, IDataObserver obser) {
		super();
		this.o = o;
		this.key = key;
		this.obser = obser;
	}
	public BeenObser(Object o, int key) {
		super();
		this.o = o;
		this.key = key;
	}
	public Object getO() {
		return o;
	}
	public void setO(Object o) {
		this.o = o;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public IDataObserver getObser() {
		return obser;
	}
	public void setObser(IDataObserver obser) {
		this.obser = obser;
	}
	
}
