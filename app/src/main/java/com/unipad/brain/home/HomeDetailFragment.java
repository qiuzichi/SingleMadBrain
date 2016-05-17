package com.unipad.brain.home;

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

import com.unipad.brain.R;
import com.unipad.brain.home.bean.HomeBean;
import com.unipad.brain.home.bean.ProjectBean;
import com.unipad.brain.home.competitionpj.view.HomePresenter;
import com.unipad.common.BasicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * yzj----项目:虚拟事件
 */
public class HomeDetailFragment extends BasicFragment {
    private static final String TAG = HomeDetailFragment.class.getSimpleName();
    private ListView lv_project;
    private FrameLayout fl_project;
    private TextView txt_title;
    private TextView txt_attention_content;
    private TextView txt_memory_content;
    private TextView txt_recall_content;
    private TextView txt_function_content;
    /**
     * Fragment界面父布局
     */
    private RelativeLayout mParentLayout;
    private HomePresenter homePresenter;
    private List<HomeBean> homeBeans;
    private String next="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_home_layout, container,
                false);
        return mParentLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        lv_project= (ListView) mParentLayout.findViewById(R.id.lv_project);
        fl_project= (FrameLayout) mParentLayout.findViewById(R.id.fl_project);
        txt_title= (TextView) mParentLayout.findViewById(R.id.txt_title);
        txt_attention_content= (TextView) mParentLayout.findViewById(R.id.txt_attention_content);
        txt_memory_content= (TextView) mParentLayout.findViewById(R.id.txt_memory_content);
        txt_recall_content= (TextView) mParentLayout.findViewById(R.id.txt_recall_content);
        txt_function_content= (TextView) mParentLayout.findViewById(R.id.txt_function_content);
       // mParentLayout.findViewById(R.id.img_close).setOnClickListener(this);

        lv_project.setAdapter(new nvvervi());

        lv_project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //homePresenter.changeUI(position);
                homePresenter.setProjectContent(position);
            }
        });
    }

    @Override
    public void changeBg(int color) {
        mParentLayout.setBackgroundColor(color);
    }

    @Override
    public void memoryTimeToEnd() {
        //this.inAnswerMode();
    }

    @Override
    public void rememoryTimeToEnd() {
        //mStubShade.setVisibility(View.VISIBLE);
        //endAnswerMode(1);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     *  此方法，  初始化home页左边菜单。
     */
    private void initData(){
        homeBeans = new ArrayList<HomeBean>();
        //人名头像记忆
        ProjectBean npPj = new ProjectBean("人名头像记忆","" , new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean npBean = new HomeBean(R.drawable.sel_np,R.drawable.nor_np,npPj,false);
        //二进制数字
        ProjectBean ejzPj = new ProjectBean("二进制数字", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean ejzBean = new HomeBean(R.drawable.sel_rjz,R.drawable.nor_rjz,ejzPj,false);
        //马拉松数字项目
        ProjectBean mlsNumPj = new ProjectBean("马拉松数字项目", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean mlsNumBean = new HomeBean(R.drawable.sel_mlsnum,R.drawable.nor_mlsnum,mlsNumPj,false);
        //抽象图形
        ProjectBean cxTxPj = new ProjectBean("抽象图形", "", new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean cxTxBean = new HomeBean(R.drawable.sel_cx,R.drawable.nor_cx,cxTxPj,false);
        // 快速随机数字
        ProjectBean kssjPj = new ProjectBean("快速随机数字", "CDVSDV" ,  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"CDVDVWE","FBRRYJU","GWGWG5","FKBNG");
        HomeBean kssjBean = new HomeBean(R.drawable.sel_kssj,R.drawable.nor_kssj,kssjPj,false);
        // 虚拟事件和日期
        ProjectBean xnsjPj = new ProjectBean("虚拟事件和日期", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean xnsjBean = new HomeBean(R.drawable.sel_xlsj,R.drawable.nor_xlsj,xnsjPj,false);
        // 马拉松扑克记忆
        ProjectBean mlspkPj = new ProjectBean("马拉松扑克记忆", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean mlspkBean = new HomeBean(R.drawable.sel_mlspk,R.drawable.nor_mlspk,mlspkPj,false);
        // 随机词语记忆
        ProjectBean sjcyPj = new ProjectBean("随机词语记忆", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean sjcyBean = new HomeBean(R.drawable.sel_sjcy,R.drawable.nor_sjcy,sjcyPj,false);
        //听记数字
        ProjectBean thnumPj = new ProjectBean("听记数字", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean thnumBean = new HomeBean(R.drawable.sel_tjnum,R.drawable.nor_tjnum,thnumPj,false);
        // 快速扑克记忆
        ProjectBean kspkPj = new ProjectBean("快速扑克记忆", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
        HomeBean kspkBean = new HomeBean(R.drawable.sel_kspk,R.drawable.nor_kspk,kspkPj,false);
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


    class nvvervi extends BaseAdapter{

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

            convertView=LayoutInflater.from(mActivity).inflate(R.layout.fragment_home_item,null);

            TextView name= (TextView) convertView.findViewById(R.id.txt_name);
            ImageView img= (ImageView) convertView.findViewById(R.id.img_photo);

            name.setText(homeBeans.get(position).projectBean.getName());
            img.setImageResource(homeBeans.get(position).norImgId);

            return convertView;
        }
    }

}
