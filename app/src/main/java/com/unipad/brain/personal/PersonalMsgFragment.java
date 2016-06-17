package com.unipad.brain.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.unipad.brain.R;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心之我的消息
 * Created by Wbj on 2016/4/27.
 */
public class PersonalMsgFragment extends PersonalCommonFragment {

    private ListView lv_apple;

    private List<CompetitionBean> competitionBeans;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.clear);
        lv_apple =(ListView) mActivity.findViewById(R.id.lv_apple);

        competitionBeans = new ArrayList<CompetitionBean>();
        competitionBeans.add(new CompetitionBean());
        competitionBeans.add(new CompetitionBean());
        competitionBeans.add(new CompetitionBean());
        competitionBeans.add(new CompetitionBean());
        competitionBeans.add(new CompetitionBean());

       lv_apple.setAdapter(new CommonAdapter<CompetitionBean>(mActivity, competitionBeans, R.layout.competition_item_layout) {
  //          @Override
            public void convert(ViewHolder holder, CompetitionBean homeBean) {
 //				holder.setText(R.id.txt_name, homeBean.name);
//				holder.setImageResource(R.id.img_photo, homeBean.isSelect ? homeBean.selImgId : homeBean.norImgId);
//				holder.setTextColor(R.id.txt_name, homeBean.isSelect ? iHome.getContext().getResources().getColor(R.color.main_1) : iHome.getContext().getResources().getColor(R.color.black));
                /////////----- 以下两行代码表示 设置某个控件的点击事件-----////
               // holder.getView(R.id.btn_apply).setTag(homeBean);
               // holder.getView(R.id.btn_apply).setOnClickListener(applyOrAtClickListenter);
           }
       });
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_frg_msg;
    }

    @Override
    public void clickTitleBarRightText() {
        this.clearMsgList();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 清空消息列表
     */
    private void clearMsgList() {
        ToastUtil.showToast(mTitleBarRightText);
    }
}
