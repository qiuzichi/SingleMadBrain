package com.unipad.singlebrain.consult.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipad.singlebrain.R;
import com.unipad.singlebrain.consult.entity.ConsultClassBean;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;

import java.util.List;

/**
 * Created by 63 on 2016/6/27.
 */
public class MyInfoListAdapter extends CommonAdapter<ConsultClassBean>{

    public MyInfoListAdapter(Context context, List<ConsultClassBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ConsultClassBean consultClassBean) {

        ((ImageView)holder.getView(R.id.iv_info_label)).setImageResource(consultClassBean.getLabelResId());
        ((TextView)holder.getView(R.id.tv_info_name)).setText(consultClassBean.getNameResId());
        ImageView isSelected = (ImageView) holder.getView(R.id.iv_item_selected_label);
        if(consultClassBean.isSelected()){
            isSelected.setVisibility(View.VISIBLE);
        }else {
            isSelected.setVisibility(View.GONE);
        }
    }
}


