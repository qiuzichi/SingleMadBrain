package com.unipad.brain.personal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.brain.home.util.CommomAdapter;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

import java.util.ArrayList;
import java.util.List;

public class PersonalInfoActivty extends BasicActivity implements IDataObserver {
    private ListView lv_integration;
    private List<CompetitionBean> competitionBeans;
    private PersonCenterService service;
    private TextView text_myranking, txt_Rule, txt_Msg,txt_Edit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_integration_layout);
        lv_integration=(ListView)findViewById(R.id.lv_integration);
        ((TextView)findViewById(R.id.title_bar_left_text)).setOnClickListener(this);

        text_myranking=(TextView)findViewById(R.id.text_myranking);
        txt_Rule=(TextView)findViewById(R.id.txt_rule);
        txt_Edit=(TextView)findViewById(R.id.txt_editIntegration);
        txt_Msg=(TextView)findViewById(R.id.txt_rule_msg);

        competitionBeans=new ArrayList<CompetitionBean>();
        service=(PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.GET_RULE_NOTIFY, this);
        service.registerObserver(HttpConstant.LIST_TOP, this);
        }

    @Override
    public void onDestroy() {
//        service.unregistDataChangeListenerObj(this);
        service.unRegisterObserve(HttpConstant.GET_RULE_NOTIFY, this);
        service.unRegisterObserve(HttpConstant.LIST_TOP, this);
        super.onDestroy();
    }
    @Override
    public void update(int key, Object o) {
        switch (key){
            case HttpConstant.LIST_TOP:
                competitionBeans.addAll((List<CompetitionBean>)o);
                lv_integration.setAdapter(new CommomAdapter<CompetitionBean>
                        (this,competitionBeans,R.layout.top_list) {
                    @Override
                    public void convert(ViewHolder holder, CompetitionBean competitionBean) {
                        holder.setText(R.id.txt_list_username,competitionBean.getName() + "");
                        holder.setText(R.id.txt_list_scort, competitionBean.getScore() + "");

                        TextView tv_rank = holder.getView(R.id.txt_list_rank);
                        if(holder.getPosition() == 0) {
                            tv_rank.setBackgroundResource(R.drawable.textview_shape);
                            GradientDrawable drawable = (GradientDrawable) tv_rank.getBackground();
                            drawable.setColor(Color.RED);
                            tv_rank.setTextColor(Color.WHITE);
                        }else if (holder.getPosition() == 1){
                            tv_rank.setBackgroundResource(R.drawable.textview_shape);
                            GradientDrawable drawable = (GradientDrawable) tv_rank.getBackground();
                            drawable.setColor(Color.YELLOW);
                            tv_rank.setTextColor(Color.WHITE);
                        }else if (holder.getPosition() == 3){
                            tv_rank.setBackgroundResource(R.drawable.textview_shape);
                            GradientDrawable drawable = (GradientDrawable) tv_rank.getBackground();
                            drawable.setColor(Color.BLUE);
                            tv_rank.setTextColor(Color.WHITE);
                        }
                        tv_rank.setText(competitionBean.getRank() + "");
                    }
                });
                break;
            case HttpConstant.GET_RULE_NOTIFY:
                RuleGame rule = (RuleGame)o;
                String tip = "记忆规则：\n\n"+rule.getMemeryTip()+"\n\n回忆规则：\n\n"+rule.getReCallTip()+"\n\n计分规则：\n\n"+rule.getCountRule()+"\n";
                txt_Msg.setText(tip);
                break;
          }
       }

    @Override
    public void onStart() {
        super.onStart();
        if (competitionBeans.size()==0){
            service.getTopList(getIntent().getStringExtra("matchId"));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar_left_text:
                finish();
                break;
        }
    }

    @Override
    public void initData() {
       // Intent intent = new Intent();
        String ranking = getIntent().getStringExtra("ranking");
        txt_Edit.setText(ranking);
        txt_Rule.setText(Constant.getProjectName(getIntent().getStringExtra("projectId")) + getString(R.string.action_rule));
        service.getRule(getIntent().getStringExtra("matchId"));
    }
}
