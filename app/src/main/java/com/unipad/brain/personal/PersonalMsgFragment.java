package com.unipad.brain.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心之我的报名
 * Created by Wbj on 2016/4/27.
 */
public class PersonalMsgFragment extends PersonalCommonFragment implements IDataObserver {

    private ListView lv_apple;
    private List<CompetitionBean> competitionBeans;

    // 个人中心模块 服务器交互 服务
    private PersonCenterService service;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.clear);
        lv_apple =(ListView) mActivity.findViewById(R.id.lv_apple);
        competitionBeans = new ArrayList<CompetitionBean>();
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.USER_APPLYED,this);
        service.getApplyList(AppContext.instance().loginUser.getUserId());
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

    @Override
    public void update(int key, Object o) {
         // 此方法用于更新UI
        switch (key){
            case HttpConstant.USER_APPLYED:
                lv_apple.setAdapter(new CommonAdapter<CompetitionBean>(mActivity, competitionBeans, R.layout.personal_msg_item_layout) {
                    @Override
                    public void convert(ViewHolder holder, CompetitionBean homeBean) {
      //				holder.setText(R.id.txt_name, homeBean.name);
      //				holder.setImageResource(R.id.img_photo, homeBean.isSelect ? homeBean.selImgId : homeBean.norImgId);
      //				holder.setTextColor(R.id.txt_name, homeBean.isSelect ? iHome.getContext().getResources().getColor(R.color.main_1) : iHome.getContext().getResources().getColor(R.color.black));
                        /////////----- 以下两行代码表示 设置某个控件的点击事件-----////
                        // holder.getView(R.id.btn_apply).setTag(homeBean);
                        // holder.getView(R.id.btn_apply).setOnClickListener(applyOrAtClickListenter);
                    }
                });
                break;
        }
    }
}
