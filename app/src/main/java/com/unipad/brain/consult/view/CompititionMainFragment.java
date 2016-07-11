package com.unipad.brain.consult.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.ConsultBaseFragment;
import com.unipad.brain.consult.entity.ListAreaEnum;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.CompetitionBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 63 on 2016/6/28.
 */
public class CompititionMainFragment  extends ConsultBaseFragment  implements IDataObserver {

    private NewsService service;
    private List<CompetitionBean> mNewCompetitionDatas = new ArrayList<CompetitionBean>();
    private NewCompetitionAdapter mNewCompetitionAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_compitition_main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_NEWCOMPETITION, this);
        //默认加载第一页的数据 10条 分页加载数据
        service.getNewCompetition(AppContext.instance().loginUser.getUserId(), null, null, 1, 10);

        ListView mListView = (ListView)view.findViewById(R.id.listview_compitition_main);
        mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);

        mNewCompetitionAdapter = new NewCompetitionAdapter(getmContext(), mNewCompetitionDatas, R.layout.layout_compition_info_item);
        mListView.setAdapter(mNewCompetitionAdapter);
    }


    private class NewCompetitionAdapter extends CommonAdapter<CompetitionBean>{

        public NewCompetitionAdapter(Context context, List<CompetitionBean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, CompetitionBean competitionBean) {
            //比赛模式
            TextView race_model = (TextView) holder.getView(R.id.tv_competion_info_model_item);
            race_model.setText(Constant.getProjectName(competitionBean.getProjectId()));

            //比赛区域
            TextView race_gradle = (TextView) holder.getView(R.id.tv_competion_info_area_item);
            ListAreaEnum areaEnum = ListAreaEnum.values()[Integer.parseInt(competitionBean.getGradeId().substring(competitionBean.getGradeId().lastIndexOf("0") + 1)) - 1];
            Drawable drawable = getResources().getDrawable(areaEnum.getLabelResId());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            race_gradle.setText(areaEnum.getNameResId());
            race_gradle.setCompoundDrawables(null, drawable, null, null);
            //设置参赛日期  2016.07.08
            ((TextView) holder.getView(R.id.tv_competion_info_date_item)).setText(competitionBean.getCompetitionDate());
            //参赛时间
            ((TextView) holder.getView(R.id.tv_competion_info_time_item)).setText(competitionBean.getCompetitionTime());
            //比赛费用
            ((TextView) holder.getView(R.id.tv_competion_info_fee_item)).setText("￥ " + competitionBean.getCost());
           //参加报名
            ((Button) holder.getView(R.id.btn_input_competition)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_NEWCOMPETITION:
                //获取数据

                mNewCompetitionDatas.addAll((List<CompetitionBean>) o);

                mNewCompetitionAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
    // 单击搜索按钮时激发该方法


    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }
    private void clear(){
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_NEWCOMPETITION, this);
    }
}
