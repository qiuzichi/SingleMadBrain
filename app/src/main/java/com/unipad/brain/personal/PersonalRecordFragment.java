package com.unipad.brain.personal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.dialog.ShowDialog;
import com.unipad.brain.home.bean.HisRecord;
import com.unipad.brain.personal.bean.BrokenLineData;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.brain.personal.view.BrokenLineView;
import com.unipad.brain.view.WheelMainView;
import com.unipad.common.Constant;
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

/**
 * 个人中心之历史成绩
 * Created by Wbj on 2016/4/27.
 */
public class PersonalRecordFragment extends PersonalCommonFragment implements View.OnClickListener, ShowDialog.OnShowDialogClick,
        WheelMainView.OnChangingListener,IDataObserver {
    private static final String TAG = PersonalRecordFragment.class.getSimpleName();
    private static final String DATE_REGEX = "\\d{4}/\\d{2}/\\d{2}";
    private Button mEditSearchBeginDate, mEditSearchEndDate;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
    private List<HisRecord> mRecordData;
    private List<HisRecord> mExerciseData;
    private ViewGroup viewParent;
    private HisRecord record;
    private PopupWindow mPopupWindows;
    private PopupWindow mPopupWindowsDate;
    private TextView mRightTextView;
    private TextView null_text;

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
        ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).registerObserver(HttpConstant.HISRECORD_OK, this);
        ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).registerObserver(HttpConstant.EXECISE_DATA, this);


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
        showSelectDialog(null);
    }

    /**
     * 弹出选择对话框
     */
    private void showSelectDialog(BaseAdapter baseAdapter) {
        //获取textview组件
        mRightTextView = mActivity.getmTextRight();
        View mPopupView = LayoutInflater.from(mActivity).inflate(R.layout.item_select_popup, null);
        RadioGroup mRadioGroup = (RadioGroup) mPopupView.findViewById(R.id.radio_group_popup);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(group.getCheckedRadioButtonId());

                mRightTextView.setText(rb.getText().toString());

                switch (group.getCheckedRadioButtonId()){
                    case R.id.radio_select_competition: //比赛模式
                        if(null != mRecordData){
                            hisRecords = mRecordData;
                            viewParent.removeAllViews();
                            viewParent.addView(getGridView());
                        }
                        break;
                    case R.id.radio_select_exercise: //练习模式
                        int requestPager = 1;
                        int totalItem = 10;
                        if(null == mExerciseData){
                            ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER))
                                    .getHistoryExerRecord(requestPager, totalItem);
                        }else {
                            hisRecords = mExerciseData;
                            viewParent.removeAllViews();
                            viewParent.addView(getGridView());
                        }
                        /*没有练习的历史成绩*/
                        if(hisRecords.size() == 0){
                            showNullData();
                        }

//                        showDialog = new ShowDialog(mActivity);
//                        View view = LayoutInflater.from(mActivity).inflate(R.layout.first_login_dialog, null);
//                        TextView tv_msg = (TextView) view.findViewById(R.id.txt_msg);
//                        ((TextView) view.findViewById(R.id.txt_pname)).setVisibility(View.GONE);
//                        tv_msg.setText(getString(R.string.exercise_not_open));
//                        tv_msg.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
//                        tv_msg.setGravity(Gravity.CENTER_HORIZONTAL);
//
//                        showDialog.showDialog(view, ShowDialog.TYPE_CENTER, mActivity.getWindowManager(), 0.2f, 0.5f);
//                        showDialog.setOnShowDialogClick(PersonalRecordFragment.this);
//                        showDialog.bindOnClickListener(view, new int[]{R.id.img_close});
                        break;
                }
                //当数据为空的时候；
                if( null == hisRecords){
                    gridView.setVisibility(View.GONE);

                }
                closePopup();
            }
        });

        ScaleAnimation sa = new ScaleAnimation(1f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        sa.setDuration(300);
        mPopupView.startAnimation(sa);


        mPopupWindows = new PopupWindow(mPopupView, mRightTextView.getWidth() + DensityUtil.dip2px(mActivity, 40), DensityUtil.dip2px(mActivity,100));
        // 设置点击外部可以被关闭
        mPopupWindows.setOutsideTouchable(true);
        mPopupWindows.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置popupWindow可以得到焦点
        mPopupWindows.setFocusable(true);
        //显示位置
        mPopupWindows.showAsDropDown(mRightTextView, - DensityUtil.dip2px(mActivity, 10), 10);
    }

    private void showSelectStartDatePopup(View positionView){

        ScaleAnimation sa = new ScaleAnimation(1f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        sa.setDuration(300);
        positionView.startAnimation(sa);


        mPopupWindowsDate = new PopupWindow(positionView, mEditSearchBeginDate.getWidth() + DensityUtil.dip2px(mActivity, 400), DensityUtil.dip2px(mActivity,300));
        // 设置点击外部可以被关闭
        mPopupWindowsDate.setOutsideTouchable(true);
        mPopupWindowsDate.setBackgroundDrawable(new BitmapDrawable());

        // 设置popupWindow可以得到焦点
        mPopupWindowsDate.setFocusable(true);
        //显示位置
        mPopupWindowsDate.showAsDropDown(mEditSearchBeginDate, - DensityUtil.dip2px(mActivity, 40), 5);
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
//                showDialog = new ShowDialog(mActivity);
                wheelMainView=new WheelMainView(mActivity);
                wheelMainView.setChangingListener(PersonalRecordFragment.this);
//                showDialog.showDialog(wheelMainView,ShowDialog.TYPE_BOTTOM,mActivity.getWindowManager(),0.5f,0.6f);

                showSelectStartDatePopup(wheelMainView);

                break;
            case R.id.record_search_end_data:
//                showDialog = new ShowDialog(mActivity);
                wheelMainView=new WheelMainView(mActivity);
                wheelMainView.setChangingListener(new WheelMainView.OnChangingListener() {
                    @Override
                    public void onChanging(String changStr) {
                        mEditSearchEndDate.setText(changStr);
                    }
                });
                showSelectStartDatePopup(wheelMainView);
//                showDialog.showDialog(wheelMainView,ShowDialog.TYPE_BOTTOM,mActivity.getWindowManager(),0.5f,0.6f);
                break;
            case R.id.group_historry_list:
               openPersonalIntegration((HisRecord)v.getTag());
            default:
                break;
        }
    }


    private void openPersonalIntegration(HisRecord hisRecord) {
        Intent intent=new Intent();
        intent.setClass(mActivity, PersonalInfoActivty.class);
        if(hisRecord == null)
            return;

        intent.putExtra("ranking", hisRecord.getRanking());
        intent.putExtra("projectId", hisRecord.getProjectId());
        mActivity.startActivity(intent);
    }



    @Override
    public void onStart() {
        super.onStart();
        thisShowView = 3;
    }

    //关闭弹出窗体
    private void closePopup(){

        if(mPopupWindows != null && mPopupWindows.isShowing()){
            mPopupWindows.dismiss();
        }

        if(mPopupWindowsDate != null && mPopupWindowsDate.isShowing()){
            mPopupWindowsDate.dismiss();
        }
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
                final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                Date date = sdf.parse(searchBeginDate.substring(searchBeginDate.indexOf("-") + 1, searchBeginDate.length()));
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
        TableRow tableRow1 = (TableRow) tableRow.findViewById(R.id.group_historry_list);
        tableRow1.setTag(record);
        tableRow1.setOnClickListener(this);
        return  tableRow;
    }


    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }


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
//          service.unregistDataChangeListenerObj(this);
          service.unRegisterObserve(HttpConstant.EXECISE_DATA,this);
          service.unRegisterObserve(HttpConstant.HISRECORD_OK,this);
    }

    @Override
    public void update(int key, Object o) {
        if(o != null){
            switch (key) {
                case HttpConstant.HISRECORD_OK:
                    hisRecords= (List<HisRecord>) o;
                   // hisRecords = mRecordData;
                    if (mIsBrokenLine) {

                    } else {
                        viewParent.removeAllViews();
                        viewParent.addView(getGridView());
                    }
                    break;

                case HttpConstant.EXECISE_DATA:
                   mExerciseData = (List<HisRecord>) o;
                    hisRecords = mExerciseData;
                    if (!mIsBrokenLine) {
                        viewParent.removeAllViews();
                        viewParent.addView(getGridView());
                    }
                    break;

                default:
                    break;
            }
        }else {
            showNullData();
        }
    }
    private void showNullData(){
        if(null == null_text){
            null_text = new TextView(mActivity);
            RelativeLayout.LayoutParams params = new  RelativeLayout.
                    LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            null_text.setGravity(Gravity.CENTER);
            null_text.setText(getString(R.string.hostory_null_data));
            null_text.setTextSize(25);
            null_text.setLayoutParams(params);
        }

        viewParent.removeAllViews();
        viewParent.addView(null_text);
    }

    @Override
    public void onChanging(String changStr) {
        mEditSearchBeginDate.setText(changStr);
    }

    @Override
    public void dialogClick(int id) {
        if(null != showDialog && showDialog.isShowing()){
            showDialog.dismiss();
        }
    }
}
