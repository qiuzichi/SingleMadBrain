package com.unipad.brain.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
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
    private TextView text_myranking;
    private TopAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        competitionBeans=new ArrayList<CompetitionBean>();
        service=(PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.LIST_TOP,this);
        adapter=new TopAdapter(this,competitionBeans,R.layout.top_list);
        setContentView(R.layout.personal_integration_layout);

    }

    @Override
    public void onDestroy() {
        service.unregistDataChangeListenerObj(this);
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
                        holder.setText(R.id.txt_list_top,competitionBean.getName());
                        holder.setText(R.id.text_myranking,competitionBean.getUserId());

                    }
                });
                break;
          }
       }

    @Override
    public void onStart() {
        super.onStart();
        if (competitionBeans.size()==0){
            service.getTopList(AppContext.instance().loginUser.getUserId());

        }

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void initData() {
        Intent intent = new Intent();
        String ranking = intent.getStringExtra("ranking");
        text_myranking.setText(ranking);

    }

    private class TopAdapter extends CommomAdapter <CompetitionBean>{

        public TopAdapter(Context context, List<CompetitionBean>datas, int layoutId) {
            super(context, datas, layoutId);

        }

        @Override
        public void convert(ViewHolder holder, CompetitionBean competitionBean) {
            text_myranking=(TextView)findViewById(R.id.text_myranking);
            lv_integration=(ListView)findViewById(R.id.lv_integration);

        }
    }
}
