package com.unipad.brain.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
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
import com.unipad.http.HitopHistRecord;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
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
public class PersonalRecordFragment extends PersonalCommonFragment implements IDataObserver
,WheelMainView.OnChangingListener{
    private static final String TAG = PersonalRecordFragment.class.getSimpleName();
    private static final String DATE_REGEX = "\\d{4}/\\d{2}/\\d{2}";
    private EditText mEditSearchBeginDate, mEditSearchEndDate;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private BrokenLineView mViewBrokenLine;
    /**
     * 默认以以折线图的形式显示成绩视图
     */
    private boolean mIsBrokenLine = false;
    private int mRedColor, mBlackColor;
    private TableLayout gridView;
    private List<HisRecord> hisRecords;
    private ViewGroup viewParent;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mTitleBarRightText = mActivity.getString(R.string.table_graph);
        mRedColor = mActivity.getResources().getColor(R.color.red);
        mBlackColor = mActivity.getResources().getColor(R.color.black);
        (mEditSearchBeginDate = (EditText) mActivity.findViewById(R.id.record_search_begin_data)).setOnClickListener(this);
        (mEditSearchEndDate = (EditText) mActivity.findViewById(R.id.record_search_end_data)).setOnClickListener(this);
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

    @Override
    public void clickTitleBarRightText() {
//       this.switchBrowse();
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
                break;
            case R.id.record_search_end_data:
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

        if (!searchBeginDate.matches(DATE_REGEX)) {
            isValidity = false;
            mEditSearchBeginDate.setTextColor(mRedColor);
        } else {
            mEditSearchBeginDate.setTextColor(mBlackColor);
        }
        if (!searchEndDate.matches(DATE_REGEX)) {
            isValidity = false;
            mEditSearchEndDate.setTextColor(mRedColor);
        } else {
            mEditSearchEndDate.setTextColor(mBlackColor);
        }
        if (!isValidity) {
            ToastUtil.showToast(R.string.record_data_format_note);
            return isValidity;
        }

        try {
            long beginTimeSeconds = mDateFormat.parse(searchBeginDate).getTime() / 1000;
            long endTimeSeconds = mDateFormat.parse(searchEndDate).getTime() / 1000;
            if (endTimeSeconds <= beginTimeSeconds) {
                ToastUtil.showToast(R.string.end_date_error);
                mEditSearchEndDate.setTextColor(mRedColor);
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
                    .getHistoryRecord(mEditSearchBeginDate.getText().toString().trim(),mEditSearchEndDate.getText().toString().trim());
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
        ((TextView)tableRow.findViewById(R.id.rectime)).setText(record.getRectime());
        ((TextView)tableRow.findViewById(R.id.memtime)).setText(record.getMemtime());
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
    public void onDestroy() {
        super.onDestroy();
        ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).unRegisterObserve(HttpConstant.HISRECORD_OK,this);
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

    }
}
