package com.unipad.brain.location;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.location.bean.CityBean;
import com.unipad.brain.location.bean.CompetitionBean;
import com.unipad.brain.location.bean.ProvinceBean;
import com.unipad.brain.location.dao.LocationService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

import java.util.List;

/**
 * Created by Wbj on 2016/5/24.
 * Updata by gongjb on 2016/6/22
 */
public class LocationActivity extends BasicActivity implements IDataObserver, AdapterView.OnItemClickListener {
    // 省份list
    private ListView lv_province;
    // 城市List
    private ListView lv_city;
    // 賽事Id
    private ListView lv_com;
    // 获取定位dao
    private LocationService service;

    List<ProvinceBean> provinceBeans;
    List<CityBean> cityBeans;
    List<CompetitionBean> competitionBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_aty);
    }

    @Override
    public void initData() {
        lv_province = (ListView) findViewById(R.id.lv_province);
        lv_province.setOnItemClickListener(this);
        lv_city = (ListView) findViewById(R.id.lv_city);
        lv_city.setOnItemClickListener(this);
        lv_com = (ListView) findViewById(R.id.lv_com);
        lv_com.setOnItemClickListener(this);
        findViewById(R.id.title_bar_left_text).setOnClickListener(this);
        findViewById(R.id.title_bar_right_text).setOnClickListener(this);
        service = (LocationService) AppContext.instance().getService(Constant.LOCATION_SERVICE);
        service.registerObserver(HttpConstant.GET_PROVINCE, this);
        service.registerObserver(HttpConstant.GET_CITY, this);
        service.registerObserver(HttpConstant.CITY_GAME, this);
        service.getProvinceList();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_bar_left_text:
                finish();
                break;
            case R.id.title_bar_right_text:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        service.unregistDataChangeListenerObj(this);
        super.onDestroy();
    }

    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.GET_CITY:
                if (o instanceof String) {
                    ToastUtil.showToast((String) o);
                } else {
                    cityBeans = (List<CityBean>) o;
                    lv_city.setAdapter(new CommonAdapter<CityBean>(this, cityBeans, R.layout.location_item) {
                        @Override
                        public void convert(ViewHolder holder, CityBean provinceBean) {
                            holder.setText(R.id.txt_name, provinceBean.cityName);
                            TextView txtName = (TextView) holder.getView(R.id.txt_name);
                            txtName.setTextColor(LocationActivity.this.getResources().getColor(R.color.black));
                        }
                    });
                }
                break;
            case HttpConstant.GET_PROVINCE:
                if (o instanceof String) {
                    ToastUtil.showToast((String) o);
                } else {
                    provinceBeans = (List<ProvinceBean>) o;
                    lv_province.setAdapter(new CommonAdapter<ProvinceBean>(this, provinceBeans, R.layout.location_item) {
                        @Override
                        public void convert(ViewHolder holder, ProvinceBean provinceBean) {
                            holder.setText(R.id.txt_name, provinceBean.ProvinceName);
                            TextView txtName = (TextView) holder.getView(R.id.txt_name);
                            txtName.setTextColor(LocationActivity.this.getResources().getColor(R.color.text_gray));
                        }
                    });
                    if (null != provinceBeans && provinceBeans.size() > 0) {
                        service.getCityList(provinceBeans.get(0).provinceId);
                    }
                }
                break;
            case HttpConstant.CITY_GAME:
                // 赛事
                if (o instanceof String) {
                    ToastUtil.showToast((String) o);
                } else {
                    competitionBeans = (List<CompetitionBean>) o;
                    lv_com.setAdapter(new CommonAdapter<CompetitionBean>(this, competitionBeans, R.layout.personal_msg_item_layout) {
                        @Override
                        public void convert(ViewHolder holder, CompetitionBean competitionBean) {
                            holder.setText(R.id.txt_year, competitionBean.getCompetitionDate());
                            holder.setText(R.id.txt_name, competitionBean.getName() + "/" + competitionBean.getProjecNname());
                            holder.setText(R.id.txt_addr, competitionBean.getAddr());
                            holder.setText(R.id.txt_cost, competitionBean.getCost());
                            Button in_game = (Button)holder.getView(R.id.in_game);
                            in_game.setText(competitionBean.getIsApply() == 0 ? getString(R.string.my_apply) : getString(R.string.applied) );
                           // holder.getView(R.id.in_game).setVisibility(competitionBean.getApplyState() == 0 ? View.VISIBLE : View.GONE);
                            //holder.setImageResource(R.id.img_photo, homeBean.isSelect ? homeBean.selImgId : homeBean.norImgId);
                            //holder.setTextColor(R.id.txt_name, homeBean.isSelect ? iHome.getContext().getResources().getColor(R.color.main_1) : iHome.getContext().getResources().getColor(R.color.black));
                            /////////----- 以下两行代码表示 设置某个控件的点击事件-----////
                           // holder.getView(R.id.in_game).setTag(competitionBean);
                            //holder.getView(R.id.in_game).setOnClickListener(new OnClickApply());
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_province:
                service.getCityList(provinceBeans.get(position).provinceId);
                break;
            case R.id.lv_city:
                // 根据城市ID 获取比赛列表
                service.getCompetitionList(cityBeans.get(position).cityId);
                break;
        }
    }
}
