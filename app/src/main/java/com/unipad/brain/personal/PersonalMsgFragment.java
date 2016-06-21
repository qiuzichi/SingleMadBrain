package com.unipad.brain.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.CommonActivity;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
        service.registerObserver(HttpConstant.USER_IN_GAEM,this);
        service.getApplyList(AppContext.instance().loginUser.getUserId());
    }

    @Override
    public void onStart() {
        super.onStart();
        thisShowView = 6;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        service.unregistDataChangeListenerObj(this);
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
                if(o instanceof String){
                    ToastUtil.showToast((String)o);
                } else {
                    competitionBeans.addAll((List<CompetitionBean>)o);
                    lv_apple.setAdapter(new CommonAdapter<CompetitionBean>(mActivity, competitionBeans, R.layout.personal_msg_item_layout) {
                        @Override
                        public void convert(ViewHolder holder, CompetitionBean competitionBean) {
                            holder.setText(R.id.txt_year, competitionBean.getCompetitionDate());
                            holder.setText(R.id.txt_name, competitionBean.getName() + "/" + competitionBean.getProjecNname());
                            holder.setText(R.id.txt_addr,competitionBean.getAddr());
                            holder.setText(R.id.txt_cost, competitionBean.getCost());
                            holder.getView(R.id.in_game).setVisibility(competitionBean.getApplyState() == 0 ? View.VISIBLE : View.GONE);

                            //holder.setImageResource(R.id.img_photo, homeBean.isSelect ? homeBean.selImgId : homeBean.norImgId);
                            //holder.setTextColor(R.id.txt_name, homeBean.isSelect ? iHome.getContext().getResources().getColor(R.color.main_1) : iHome.getContext().getResources().getColor(R.color.black));
                            /////////----- 以下两行代码表示 设置某个控件的点击事件-----////
                             holder.getView(R.id.in_game).setTag(competitionBean);
                             holder.getView(R.id.in_game).setOnClickListener(new OnClickApply());
                        }
                    });
                }
                break;
            case HttpConstant.USER_IN_GAEM:
                // 判断是否可以进入比赛
                try {
                    JSONObject jsonObject = new JSONObject((String)o);
                    if(jsonObject == null){
                        //13973752686
                        ToastUtil.showToast((String)o);
                        return;
                    }
                    int code = jsonObject.optInt("ret_code");
                    if(code == 0){
                        JSONObject data = jsonObject.optJSONObject("data");
                        String allow = data.optString("allow");
                        if("0".equals(allow)){
                            Intent intent = new Intent(mActivity, CommonActivity.class);
                            intent.putExtra("projectId",data.optString("projectId"));
                            intent.putExtra("matchId",data.optString("matchId"));
                            this.startActivity(intent);
                        } else if("-1".equals(allow)){
                            ToastUtil.showToast(getString(R.string.not_game));
                        } else {
                            ToastUtil.showToast(getString(R.string.gameed));
                        }
                    } else {
                        ToastUtil.showToast("系统错误");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     *  进入比赛
     */
    class OnClickApply implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CompetitionBean competitionBean = (CompetitionBean)v.getTag();
            service.checkMatchStart(competitionBean.getComId(),competitionBean.getProjectId());
        }
    }
}
