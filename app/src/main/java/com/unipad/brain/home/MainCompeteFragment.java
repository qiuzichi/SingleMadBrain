package com.unipad.brain.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.slidingmenu.lib.SlidingMenu;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.HomeBean;
import com.unipad.brain.home.bean.ProjectBean;
import com.unipad.brain.home.competitionpj.view.HomePresenter;
import com.unipad.common.CommonActivity;
import com.unipad.common.bean.CompeteItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * yzj----项目:虚拟事件
 */
public class MainCompeteFragment extends MainBasicFragment {
    private RelativeLayout relatlayout;
    private ListView lv_project;
    private FrameLayout fl_project;
    private TextView txt_title;
    private TextView txt_attention_content;
    private TextView txt_memory_content;
    private TextView txt_recall_content;
    private TextView txt_function_content;
    //比赛项目
    private TextView txt_pname;
    //城市赛
    private TextView txt_city_memory, txt_city_recall;
    //中国赛
    private TextView txt_china_memory, txt_china_recall;
    //世界赛
    private TextView txt_world_memory, txt_world_recall;
    //帮助按钮
    private ImageView img_phelp;
    /**
     * Fragment界面父布局
     */
    //private RelativeLayout mParentLayout;
    //索引
    private int projectindex = -1;
    private HomePresenter homePresenter;
    private List<HomeBean> homeBeans;
    private String next = "";
    HomeListAdapter homeListAdapter = new HomeListAdapter();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

        this.setSidebar();
        lv_project = (ListView) mActivity.findViewById(R.id.lv_project);
        fl_project = (FrameLayout) mActivity.findViewById(R.id.fl_project);
        txt_title = (TextView) mActivity.findViewById(R.id.txt_title);
        txt_attention_content = (TextView) mActivity.findViewById(R.id.txt_attention_content);
        txt_memory_content = (TextView) mActivity.findViewById(R.id.txt_memory_content);
        txt_recall_content = (TextView) mActivity.findViewById(R.id.txt_recall_content);
        txt_function_content = (TextView) mActivity.findViewById(R.id.txt_function_content);
        txt_pname = (TextView) mActivity.findViewById(R.id.txt_pname);
        txt_city_memory = (TextView) mActivity.findViewById(R.id.txt_city_memory);
        txt_city_recall = (TextView) mActivity.findViewById(R.id.txt_city_recall);
        txt_china_memory = (TextView) mActivity.findViewById(R.id.txt_china_memory);
        txt_china_recall = (TextView) mActivity.findViewById(R.id.txt_china_recall);
        txt_world_memory = (TextView) mActivity.findViewById(R.id.txt_world_memory);
        txt_world_recall = (TextView) mActivity.findViewById(R.id.txt_world_recall);
        img_phelp = (ImageView) mActivity.findViewById(R.id.img_phelp);
        relatlayout = (RelativeLayout) mActivity.findViewById(R.id.relatlayout);
        mActivity.findViewById(R.id.btn_apple).setOnClickListener(this);//比赛报名
        img_phelp.setOnClickListener(this);

        relatlayout.setVisibility(View.GONE);
        lv_project.setAdapter(homeListAdapter);//(new nvvervi());

        lv_project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (relatlayout.getVisibility() == View.GONE) {
                    relatlayout.setVisibility(View.VISIBLE);
                }

                     projectindex = position;
                homeListAdapter.notifyDataSetChanged();
                txt_pname.setText(homeBeans.get(position).projectBean.getName());
                txt_city_memory.setText((homeBeans.get(position).projectBean.getMemorysDate())[0]);
                txt_city_recall.setText((homeBeans.get(position).projectBean.getRecallsDate())[0]);
                txt_china_memory.setText((homeBeans.get(position).projectBean.getMemorysDate())[1]);
                txt_china_recall.setText((homeBeans.get(position).projectBean.getRecallsDate())[1]);
                txt_world_memory.setText((homeBeans.get(position).projectBean.getMemorysDate())[2]);
                txt_world_recall.setText((homeBeans.get(position).projectBean.getRecallsDate())[2]);

                if (position == 6 || position == 9) {
                    return;
                }


            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_compete_fragment;
    }

    public static int TmpSeconds = 10;

    /**
     * 打开比赛Activity
     */
    private void openCommonActivity() {
        // Intent intent = new Intent(mActivity, CommonActivity.class);
        // startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apple:
                Intent intent = new Intent(mActivity, CompetitionListActivity.class);
                intent.putExtra("projectBean", homeBeans.get(projectindex).projectBean);
                this.startActivity(intent);
                break;
            case R.id.img_phelp:
                setMenuOpen();
                break;
            case R.id.img_close:
                menu.toggle();
                break;
            default:
                break;
        }
    }

    /**
     * 此方法，  初始化home页左边菜单。
     */
    private void initData() {
        homeBeans = new ArrayList<>();
        //人名头像记忆
        ProjectBean npPj = new ProjectBean(mActivity.getString(R.string.project_1), "00001","目标", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean npBean = new HomeBean(R.drawable.sel_np, R.drawable.nor_np, npPj, false);
        //二进制数字
        ProjectBean ejzPj = new ProjectBean(mActivity.getString(R.string.project_2),"00002","", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean ejzBean = new HomeBean(R.drawable.sel_rjz, R.drawable.nor_rjz, ejzPj, false);
        //马拉松数字项目
        ProjectBean mlsNumPj = new ProjectBean(mActivity.getString(R.string.project_3),"00003","", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean mlsNumBean = new HomeBean(R.drawable.sel_mlsnum, R.drawable.nor_mlsnum, mlsNumPj, false);
        //抽象图形
        ProjectBean cxTxPj = new ProjectBean(mActivity.getString(R.string.project_4),"00004", "", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean cxTxBean = new HomeBean(R.drawable.sel_cx, R.drawable.nor_cx, cxTxPj, false);
        // 快速随机数字
        ProjectBean kssjPj = new ProjectBean(mActivity.getString(R.string.project_5),"00005", "CDVSDV", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "CDVDVWE", "FBRRYJU", "GWGWG5", "FKBNG");
        HomeBean kssjBean = new HomeBean(R.drawable.sel_kssj, R.drawable.nor_kssj, kssjPj, false);
        // 虚拟事件和日期
        ProjectBean xnsjPj = new ProjectBean(mActivity.getString(R.string.project_6),"00006", "", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean xnsjBean = new HomeBean(R.drawable.sel_xlsj, R.drawable.nor_xlsj, xnsjPj, false);
        // 马拉松扑克记忆
        ProjectBean mlspkPj = new ProjectBean(mActivity.getString(R.string.project_7),"00007", "", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean mlspkBean = new HomeBean(R.drawable.sel_mlspk, R.drawable.nor_mlspk, mlspkPj, false);
        // 随机词语记忆
        ProjectBean sjcyPj = new ProjectBean(mActivity.getString(R.string.project_8),"00008", "", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean sjcyBean = new HomeBean(R.drawable.sel_sjcy, R.drawable.nor_sjcy, sjcyPj, false);
        //听记数字
        ProjectBean thnumPj = new ProjectBean(mActivity.getString(R.string.project_9),"00009", "", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean thnumBean = new HomeBean(R.drawable.sel_tjnum, R.drawable.nor_tjnum, thnumPj, false);
        // 快速扑克记忆
        ProjectBean kspkPj = new ProjectBean(mActivity.getString(R.string.project_10),"00010", "", new String[]{"5min", "5min", "5min"}, new String[]{"15min", "15min", "15min"}, new String[]{"1" + next, "2" + next, "2" + next}, "", "", "", "");
        HomeBean kspkBean = new HomeBean(R.drawable.sel_kspk, R.drawable.nor_kspk, kspkPj, false);
        homeBeans.add(npBean);
        homeBeans.add(ejzBean);
        homeBeans.add(mlsNumBean);
        homeBeans.add(cxTxBean);
        homeBeans.add(kssjBean);
        homeBeans.add(xnsjBean);
        homeBeans.add(mlspkBean);
        homeBeans.add(sjcyBean);
        homeBeans.add(thnumBean);
        homeBeans.add(kspkBean);
    }

    class HomeListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return homeBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return homeBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_home_item, null);

            TextView name = (TextView) convertView.findViewById(R.id.txt_name);
            ImageView img = (ImageView) convertView.findViewById(R.id.img_photo);
            name.setText(homeBeans.get(position).projectBean.getName());
            if (projectindex == position) {
                name.setTextColor(mActivity.getResources().getColor(R.color.main_1));
                img.setImageResource(homeBeans.get(position).selImgId);
            } else {
                name.setTextColor(mActivity.getResources().getColor(R.color.black));
                img.setImageResource(homeBeans.get(position).norImgId);
            }
            return convertView;
        }
    }


    /**
     * 侧边栏是否显示
     */
    private SlidingMenu menu;

    public void setMenuOpen() {
        if (null != menu) {
            if (!menu.isShown()) {
                menu.showMenu();
            } else {
                menu.toggle();
            }
        }
    }

    /*
     * 设置侧边栏
	 */
    public View setSidebar() {
        menu = new SlidingMenu(mActivity);
        // 设置左侧边栏
        menu.setMode(SlidingMenu.RIGHT);
        // 手指滑动全屏屏幕，弹出侧边栏
        // menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置阴影图片的宽度
        menu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置阴影图片的宽度
        menu.setShadowDrawable(R.drawable.shadow);
        // SlidingMenu划出时主页面显示的剩余宽度
        //menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置SlidingMenu菜单的宽度
        //menu.setBehindWidth(550);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setBehindOffset(mActivity.getWindowManager().getDefaultDisplay().getWidth() / 2);
        // SlidingMenu滑动时的渐变程度
        menu.setFadeDegree(0.55f);
        menu.attachToActivity(mActivity, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sliding_menu_layout);
        // 得到View对象
        View view = menu.getMenu();
        // 设置点击事件
        view.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
        //注意
        return view;
    }

}
