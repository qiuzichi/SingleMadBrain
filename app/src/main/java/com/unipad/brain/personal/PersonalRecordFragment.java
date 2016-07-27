package com.unipad.brain.personal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.dialog.ShowDialog;
import com.unipad.brain.home.bean.HisRecord;
import com.unipad.brain.home.dao.HisRecordService;
import com.unipad.brain.home.util.CommomAdapter;
import com.unipad.brain.personal.bean.BrokenLineData;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.brain.personal.view.BrokenLineView;
import com.unipad.brain.view.WheelMainView;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HitopHistRecord;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.DensityUtil;
import com.unipad.utils.ToastUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 * 个人中心之历史成绩
 * Created by Wbj on 2016/4/27.
 */
public class PersonalRecordFragment extends PersonalCommonFragment implements View.OnClickListener
,WheelMainView.OnChangingListener,IDataObserver {
    private static final String TAG = PersonalRecordFragment.class.getSimpleName();
    private static final String DATE_REGEX = "\\d{4}/\\d{2}/\\d{2}";
    private Button mEditSearchBeginDate, mEditSearchEndDate;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private BrokenLineView mViewBrokenLine;
    private ShowDialog showDialog;
    private PersonCenterService service;
    private WheelMainView wheelMainView;
    /**
     * 默认以以折线图的形式显示成绩视图
     */
    private boolean mIsBrokenLine = false;
    private int mRedColor, mBlackColor;
    private TableLayout gridView;
    private List<HisRecord> hisRecords;
    private ViewGroup viewParent;
    private HisRecord record;
    private PopupWindow mPopupWindows;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.race_mode);
        mRedColor = mActivity.getResources().getColor(R.color.red);
        mBlackColor = mActivity.getResources().getColor(R.color.black);
        (mEditSearchBeginDate = (Button) mActivity.findViewById(R.id.record_search_begin_data)).setOnClickListener(this);
        (mEditSearchEndDate = (Button) mActivity.findViewById(R.id.record_search_end_data)).setOnClickListener(this);
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        mActivity.findViewById(R.id.record_text_search).setOnClickListener(this);
        mActivity.findViewById(R.id.record_text_delete).setOnClickListener(this);
        mActivity.findViewById(R.id.text_record_city).setVisibility(View.GONE);
        mActivity.findViewById(R.id.text_record_china).setVisibility(View.GONE);
        mActivity.findViewById(R.id.text_record_world).setVisibility(View.GONE);
        validateDate();
        ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).registerObserver(HttpConstant.HISRECORD_OK,this);

    }
    @Override
    public int getLayoutId() {
       return R.layout.personal_frg_record;
//        return R.layout.history_answer;
    }

    /*右侧点击事件*/
    @Override
    public void clickTitleBarRightText() {
        //弹出自定义窗体 同时带check选项  切换视图
        List<String> selectData = new ArrayList<String>();
        selectData.add("比赛模式");
        selectData.add("参赛模式");
        MyPopupAdappte myPopupAdappte = new MyPopupAdappte(mActivity, selectData, R.layout.item_select_popup);
        showSelectDialog(myPopupAdappte);
    }

    /**
     * 弹出选择对话框
     */
    private void showSelectDialog(BaseAdapter baseAdapter) {
        ListView lv = new ListView(mActivity);
        TextView mRightTextView = mActivity.getmTextRight();
        lv.setBackgroundResource(R.drawable.icon_spinner_listview_background);
        // 隐藏滚动条
        lv.setVerticalScrollBarEnabled(false);

        lv.setCacheColorHint(Color.parseColor("#00000000"));
        // 让listView没有分割线
        lv.setDividerHeight(0);
        lv.setDivider(null);
        //lv.setId(10001);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox cb_select = (CheckBox) view.findViewById(R.id.check_select_popup);
                cb_select.setChecked(!cb_select.isChecked());
                //同时其他item的 checked 为false

            }
        });
        if(lv.getAdapter() != null){
            baseAdapter.notifyDataSetChanged();
        } else {
            lv.setAdapter(baseAdapter);
        }
        ScaleAnimation sa = new ScaleAnimation(1f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        sa.setDuration(300);
        lv.startAnimation(sa);


        mPopupWindows = new PopupWindow(lv, mRightTextView.getWidth() + DensityUtil.dip2px(mActivity, 40), DensityUtil.dip2px(mActivity,200));
        // 设置点击外部可以被关闭
        mPopupWindows.setOutsideTouchable(true);
        mPopupWindows.setBackgroundDrawable(new BitmapDrawable());

        // 设置popupWindow可以得到焦点
        mPopupWindows.setFocusable(true);
        //显示位置
        mPopupWindows.showAsDropDown(mRightTextView, DensityUtil.dip2px(mActivity, 20), DensityUtil.dip2px(mActivity, -5));


    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_text_search:
               this.validateDate();
                break;
            case R.id.record_text_delete:
                break;
            case R.id.record_search_begin_data:
                showDialog = new ShowDialog(mActivity);
                wheelMainView=new WheelMainView(mActivity);
                wheelMainView.setChangingListener(PersonalRecordFragment.this);
                showDialog.showDialog(wheelMainView,ShowDialog.TYPE_CENTER,mActivity.getWindowManager(),0.5f,0.6f);
                break;
            case R.id.record_search_end_data:
                showDialog = new ShowDialog(mActivity);
                wheelMainView=new WheelMainView(mActivity);
                wheelMainView.setChangingListener(new WheelMainView.OnChangingListener() {
                    @Override
                    public void onChanging(String changStr) {
                        mEditSearchEndDate.setText(changStr);
                    }
                });
                showDialog.showDialog(wheelMainView,ShowDialog.TYPE_CENTER,mActivity.getWindowManager(),0.5f,0.6f);
                break;
            case R.id.group_historry_list:
               openPersonalIntegration();
            default:
                break;
        }
    }


    private void openPersonalIntegration() {
        Intent intent=new Intent();
        intent.setClass(getActivity(),PersonalInfoActivty.class);
        intent.putExtra("ranking",record.getRanking());
        startActivity(intent);
    }



    @Override
    public void onStart() {
        super.onStart();
        thisShowView = 3;
    }

    /**
     * 验证查找日期期间的合法性
     */
    private boolean validateDate() {
        final String searchBeginDate = mEditSearchBeginDate.getText().toString().trim();
        final String searchEndDate = mEditSearchEndDate.getText().toString().trim();
        boolean isValidity = true;

        if (TextUtils.isEmpty(searchBeginDate)) {
            isValidity = false;
        }
        if (TextUtils.isEmpty(searchEndDate)) {
            isValidity = false;
        }
        if (!isValidity) {
            ToastUtil.showToast(R.string.record_data_empty_note);
            return isValidity;
        }

        /*if (!searchBeginDate.matches(DATE_REGEX)) {
            isValidity = false;
            mEditSearchBeginDate.setTextColor(mRedColor);
        } else {
            mEditSearchBeginDate.setTextColor(mBlackColor);
        }*/
       /* if (!searchEndDate.matches(DATE_REGEX)) {
            isValidity = false;
            mEditSearchEndDate.setTextColor(mRedColor);
        } else {
            mEditSearchEndDate.setTextColor(mBlackColor);
        }*/
        if (!isValidity) {
            ToastUtil.showToast(R.string.record_data_format_note);
            return isValidity;
        }

        try {
            long beginTimeSeconds = mDateFormat.parse(searchBeginDate).getTime() / 1000;
            long endTimeSeconds = mDateFormat.parse(searchEndDate).getTime() / 1000;
            if (endTimeSeconds <= beginTimeSeconds) {
                ToastUtil.showToast(R.string.end_date_error);
//                mEditSearchEndDate.setTextColor(mRedColor);
                return false;
            } else {
                mEditSearchBeginDate.setTextColor(mBlackColor);
            }

            viewParent = (ViewGroup) mActivity.findViewById(R.id.record_histogram_container);
            if(mIsBrokenLine) {
                ArrayList<String> histogramNameList = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();
                final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
                Date date = sdf.parse(searchBeginDate.substring(searchBeginDate.indexOf("/") + 1, searchBeginDate.length()));
                calendar.setTime(date);
                //Log.i(TAG, "sdf.format(date)-->" + sdf.format(calendar.getTime()));
                histogramNameList.add(sdf.format(calendar.getTime()));
                final int intervalDays = (int) ((endTimeSeconds - beginTimeSeconds) / (24 * 60 * 60));
                //Log.i(TAG, "intervalDays-->" + intervalDays);
                for (int i = 0; i < intervalDays; ++i) {
                    calendar.add(Calendar.DATE, 1);
                    histogramNameList.add(sdf.format(calendar.getTime()));
                    //Log.i(TAG, "sdf.format(date)-->" + sdf.format(calendar.getTime()));
                }
                viewParent.removeAllViews();
                mViewBrokenLine = new BrokenLineView(mActivity, histogramNameList);
                viewParent.addView(mViewBrokenLine);
            }

            ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER))
                    .getHistoryRecord(mEditSearchBeginDate.getText().toString().trim(),mEditSearchEndDate.getText().toString().trim()
                    ,AppContext.instance().loginUser.getUserId());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }


    private List<BrokenLineData> getTestData(int total) {
        List<BrokenLineData> dataList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            dataList.add(new BrokenLineData("", 0, 98));
        }
        return dataList;
    }
    class MyPopupAdappte extends CommonAdapter<String>{

        public MyPopupAdappte(Context context, List<String> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, String s) {
            ((TextView)holder.getView(R.id.text_select_popup)).setText(s);
            //默认 勾选第一项；
            if(holder.getPosition() == 0){
                ((CheckBox)holder.getView(R.id.check_select_popup)).setChecked(true);
            }
        }
    }


    private View getGridView(){

        if (gridView == null) {
            gridView = (TableLayout) LayoutInflater.from(getActivity()).inflate(R.layout.history_answer, null);
        } else {
            View child = gridView.getChildAt(0);
            gridView.removeAllViews();
            gridView.addView(child);

        }
        for (HisRecord record:hisRecords) {
            gridView.addView(createTableRow(record));
         };

        return gridView;
    }


    private TableRow createTableRow(HisRecord record){
        TableRow tableRow = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.history_item,null);
        ((TextView)tableRow.findViewById(R.id.matchId)).setText(Constant.getProjectName(record.getProjectId()));
        ((TextView)tableRow.findViewById(R.id.projectId)).setText(Constant.getGradeId(record.getGradeId()));
        ((TextView)tableRow.findViewById(R.id.startDate)).setText(record.getStartDate());
        ((TextView)tableRow.findViewById(R.id.rectime)).setText(record.getMemtime());
        ((TextView)tableRow.findViewById(R.id.memtime)).setText(record.getRectime());
        ((TextView)tableRow.findViewById(R.id.score)).setText(record.getScore());
        ((TextView)tableRow.findViewById(R.id.ranking)).setText(record.getRanking());
        ((TableRow)tableRow.findViewById(R.id.group_historry_list)).setOnClickListener(this);
        return  tableRow;
    }


    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     * 切换成绩视图浏览模式
     */
    /*private void switchBrowse() {
        if (mIsBrokenLine) {

            mTitleBarRightText = mActivity.getString(R.string.broken_line_graph);

            mActivity.findViewById(R.id.text_record_city).setVisibility(View.GONE);
            mActivity.findViewById(R.id.text_record_china).setVisibility(View.GONE);
            mActivity.findViewById(R.id.text_record_world).setVisibility(View.GONE);
            viewParent.removeAllViews();
            if (getGridView()!=null){
                viewParent.addView(getGridView());
            }

        } else {
            mTitleBarRightText = mActivity.getString(R.string.table_graph);

            mActivity.findViewById(R.id.text_record_city).setVisibility(View.VISIBLE);
            mActivity.findViewById(R.id.text_record_china).setVisibility(View.VISIBLE);
            mActivity.findViewById(R.id.text_record_world).setVisibility(View.VISIBLE);
            if (mViewBrokenLine != null) {
                viewParent.addView(mViewBrokenLine);
            }

        }

        mIsBrokenLine = !mIsBrokenLine;

        mActivity.setRightText(mTitleBarRightText);
    }
*/
    @Override
    public void onDestroyView() {
        super.onDestroy();
        if(null != service)
          service.unregistDataChangeListenerObj(this);
    }

    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.HISRECORD_OK:
                hisRecords = (List<HisRecord>) o;
                if (mIsBrokenLine) {

                } else {
                    viewParent.removeAllViews();
                    viewParent.addView(getGridView());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onChanging(String changStr) {
        mEditSearchBeginDate.setText(changStr);
    }
}
