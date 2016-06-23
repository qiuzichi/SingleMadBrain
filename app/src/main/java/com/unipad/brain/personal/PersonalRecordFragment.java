package com.unipad.brain.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unipad.brain.R;
import com.unipad.brain.personal.bean.BrokenLineData;
import com.unipad.brain.personal.view.BrokenLineView;
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
public class PersonalRecordFragment extends PersonalCommonFragment {
    private static final String TAG = PersonalRecordFragment.class.getSimpleName();
    private static final String DATE_REGEX = "\\d{4}/\\d{2}/\\d{2}";
    private EditText mEditSearchBeginDate, mEditSearchEndDate;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private BrokenLineView mViewBrokenLine;
    /**
     * 默认以以折线图的形式显示成绩视图
     */
    private boolean mIsBrokenLine = true;
    private int mRedColor, mBlackColor;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.table_graph);
        mRedColor = mActivity.getResources().getColor(R.color.red);
        mBlackColor = mActivity.getResources().getColor(R.color.black);

        mEditSearchBeginDate = (EditText) mActivity.findViewById(R.id.record_search_begin_data);
        mEditSearchEndDate = (EditText) mActivity.findViewById(R.id.record_search_end_data);
        mActivity.findViewById(R.id.record_text_search).setOnClickListener(this);
        mActivity.findViewById(R.id.record_text_delete).setOnClickListener(this);

        validateDate();
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_frg_record;
    }

    @Override
    public void clickTitleBarRightText() {
        this.switchBrowse();
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
            default:
                break;
        }
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

            mViewBrokenLine = new BrokenLineView(mActivity, histogramNameList);
            ViewGroup viewGroup = (ViewGroup) mActivity.findViewById(R.id.record_histogram_container);
            viewGroup.addView(mViewBrokenLine);
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

    /**
     * 切换成绩视图浏览模式
     */
    private void switchBrowse() {
//        mViewBrokenLine.setVisibility(View.VISIBLE);
        if (mIsBrokenLine) {
            mTitleBarRightText = mActivity.getString(R.string.broken_line_graph);

            mActivity.findViewById(R.id.text_record_city).setVisibility(View.GONE);
            mActivity.findViewById(R.id.text_record_china).setVisibility(View.GONE);
            mActivity.findViewById(R.id.text_record_world).setVisibility(View.GONE);

            if (mViewBrokenLine != null) {
                mViewBrokenLine.setVisibility(View.GONE);
            }
        } else {
            mTitleBarRightText = mActivity.getString(R.string.table_graph);

            mActivity.findViewById(R.id.text_record_city).setVisibility(View.VISIBLE);
            mActivity.findViewById(R.id.text_record_china).setVisibility(View.VISIBLE);
            mActivity.findViewById(R.id.text_record_world).setVisibility(View.VISIBLE);

            if (mViewBrokenLine != null) {
                mViewBrokenLine.setVisibility(View.VISIBLE);
           }
        }

        mIsBrokenLine = !mIsBrokenLine;

        mActivity.setRightText(mTitleBarRightText);
    }

}
