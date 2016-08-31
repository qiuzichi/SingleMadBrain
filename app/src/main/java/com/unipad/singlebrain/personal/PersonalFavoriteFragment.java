package com.unipad.singlebrain.personal;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unipad.AppContext;
import com.unipad.singlebrain.R;
import com.unipad.singlebrain.home.bean.RuleGame;
import com.unipad.singlebrain.personal.bean.CompetitionBean;
import com.unipad.singlebrain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.LogUtil;
import com.unipad.utils.ToastUtil;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心之我的关注
 * Created by Wbj on 2016/4/27.
 */
public class PersonalFavoriteFragment extends PersonalCommonFragment implements IDataObserver {
    private ListView lv_follow;
    private List<CompetitionBean> competitionBeans;
    private PersonCenterService service;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTitleBarRightText = mActivity.getString(R.string.my_follow);
        lv_follow =(ListView) mActivity.findViewById(R.id.lv_follow);
        competitionBeans = new ArrayList<CompetitionBean>();
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.USER_FOLLOW, this);
        service.registerObserver(HttpConstant.GET_RULE_NOTIFY, this);
        LogUtil.e("", "onCreate");


        mSwipeRefreshLayout = (SwipeRefreshLayout)mActivity.findViewById(R.id.swipe_refresh_widget_favorite);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.light_blue2,
                R.color.red,
                R.color.stroke_color,
                R.color.black
        );
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(24));
        mSwipeRefreshLayout.setRefreshing(false);

        initEvent();
    }
    private void initEvent(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        competitionBeans.clear();
                        service.getFollwList(AppContext.instance().loginUser.getUserId());
                    }
                }, 2000);
            }
        });

        lv_follow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != competitionBeans)
                    service.getRule(competitionBeans.get(position).getComId());
            }
        });


        /*避免出现item太大 之后 避免冲突scroll*/
        lv_follow.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    /*第一项可见 的时候 才可以响应swipe的滑动刷新事件*/
                    mSwipeRefreshLayout.setEnabled(true);
                else
                    mSwipeRefreshLayout.setEnabled(false);
            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int i = 0;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.e("","onAttach");

    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_favorite_frg;
    }

    @Override
    public void clickTitleBarRightText() {
        this.clearFavoriteList();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        service.unregistDataChangeListenerObj(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (competitionBeans.size() == 0){
            service.getFollwList(AppContext.instance().loginUser.getUserId());
        }
        thisShowView = 6;
    }

    /*列表清空*/
    private void clearFavoriteList() {

    }
    /*更新UI*/
    @Override
    public void update(int key, Object o) {
        switch (key){
            case HttpConstant.USER_FOLLOW:
               competitionBeans.addAll((List<CompetitionBean>)o);
               lv_follow.setAdapter(new CommonAdapter<CompetitionBean>(mActivity, competitionBeans, R.layout.personal_my_favorite) {
                   @Override
                   public void convert(ViewHolder holder, CompetitionBean competitionBean) {
                       holder.setText(R.id.txt_year,competitionBean.getCompetitionDate());
                         holder.setText(R.id.txt_name,competitionBean.getName());
                         holder.setText(R.id.txt_addr,competitionBean.getAddr());
                         holder.setText(R.id.txt_cost,competitionBean.getCost());
                   }
               });
                break;
            case HttpConstant.GET_RULE_NOTIFY:

                ToastUtil.createRuleDialog(mActivity, "1001", (RuleGame) o).show();
                break;
            default:
                break;
           }

        }

    }

