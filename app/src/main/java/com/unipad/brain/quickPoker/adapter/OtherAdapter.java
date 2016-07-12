package com.unipad.brain.quickPoker.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.unipad.brain.R;
import com.unipad.brain.quickPoker.entity.ChannelItem;

public class OtherAdapter extends BaseAdapter {
	private Context context;
	public List<ChannelItem> channelList;
	private ImageView item_text;
	/** 是否可见 */
	boolean isVisible = true;
	/** 要删除的position */
	public int remove_position = -1;

	public OtherAdapter(Context context, List<ChannelItem> channelList) {
		this.context = context;
		this.channelList = channelList;
	}

	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ChannelItem getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.quick_poker_v_answer_item, null);
		item_text = (ImageView) view.findViewById(R.id.text_item);

		item_text.setImageResource(getItem(position).resId);

		return view;
	}

	/** 获取频道列表 */
	public List<ChannelItem> getChannnelLst() {
		return channelList;
	}

	/** 添加频道列表 */
	public void addItem(ChannelItem channel) {
		boolean  isAddsuccess = false;
		for (int i = 0; i < channelList.size(); i++) {
			if (channel.id < channelList.get(i).resId) {
				channelList.add(i,channel);
				isAddsuccess = true;
				break;
			}
		}
		if (!isAddsuccess) {
			channelList.add(channel);
		}
		notifyDataSetChanged();
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		if (remove_position != -1) {
			channelList.remove(remove_position);
			remove_position = -1;
			notifyDataSetChanged();
		}
	}

	/** 设置频道列表 */
	public void setListDate(List<ChannelItem> list) {
		channelList = list;
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}

	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}

}