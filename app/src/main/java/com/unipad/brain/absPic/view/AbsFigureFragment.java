package com.unipad.brain.absPic.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.absPic.bean.Figure;
import com.unipad.brain.absPic.dao.FigureService;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.io.mina.SocketThreadManager;

import org.xutils.x;

import java.util.List;

/**
 * Created by gongkan on 2016/4/15.
 */
public class AbsFigureFragment extends BasicCommonFragment {
    private GridView gridView;
    private FigureService service;
    private FigureAdapter adapter;

    private int current;
    private int preAnswer;
    private View buttonArea;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = (GridView) mViewParent.findViewById(R.id.abs_figure_gridview);
        buttonArea = mViewParent.findViewById(R.id.button_area);
        mViewParent.findViewById(R.id.answer_1).setOnClickListener(this);
        mViewParent.findViewById(R.id.answer_2).setOnClickListener(this);
        mViewParent.findViewById(R.id.answer_3).setOnClickListener(this);
        mViewParent.findViewById(R.id.answer_4).setOnClickListener(this);
        mViewParent.findViewById(R.id.answer_5).setOnClickListener(this);
        service = (FigureService) (AppContext.instance().getService(Constant.ABS_FIGURE));
        adapter = new FigureAdapter(mActivity, service.allFigures, R.layout.list_item_abs_figure);
        gridView.setAdapter(adapter);
        current = gridView.getFirstVisiblePosition();
        setButtonArea();
    }

    private void setButtonArea() {
        if (service.mode == 1) {
            buttonArea.setVisibility(View.VISIBLE);
        } else {
            buttonArea.setVisibility(View.GONE);
        }
    }

    /**
     *
     */
    @Override
    public void memoryTimeToEnd(int memory) {

        service.mode = 1;
        current = 0;
        service.shuffle();
        setButtonArea();
        adapter.notifyDataSetChanged();
        mActivity.getCommonFragment().startRememoryTimeCount();
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        service.mode = 2;
        setButtonArea();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppContext.instance().clearService(service);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_abs_figure;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer_1:
                setText(1);
                break;
            case R.id.answer_2:
                setText(2);
                break;
            case R.id.answer_3:
                setText(3);
                break;
            case R.id.answer_4:
                setText(4);
                break;
            case R.id.answer_5:
                setText(5);
                break;

        }
    }

    private void setText(int i) {
        int visiblePosition = gridView.getFirstVisiblePosition();
        View pre = gridView.getChildAt(current - visiblePosition);
        if (null != pre) {
            TextView tv = (TextView) pre.findViewById(R.id.answer_num);
            tv.setBackgroundColor(getResources().getColor(R.color.white));
            tv.setText(String.valueOf(i));
            adapter.getItem(current).setAnswerId(i);
        }
        preAnswer = current;
        current++;
        View curr = gridView.getChildAt(current - visiblePosition);
        if (curr != null) {
            TextView currTv = (TextView) curr.findViewById(R.id.answer_num);
            currTv.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }

    @Override
    public void initDataFinished() {
        adapter.notifyDataSetChanged();

    }

    private class FigureAdapter extends CommonAdapter<Figure> {
        public FigureAdapter(Context context, List<Figure> datas, int layoutId) {
            super(context, datas, layoutId);
        }


        /**
         * @param holder
         * @param figure
         */
        @Override

        public void convert(final ViewHolder holder, final Figure figure) {
            ImageView headView = (ImageView) holder.getView(R.id.icon_absfigure);

            x.image().bind(headView, figure.getPath());
            Log.e("", "path:" + figure.getPath());
            //Log.e("---", "path = " + person.getHeadPortraitPath() + ",name=" + person.getFirstName() + person.getLastName());
            final TextView orginNum = (TextView) holder.getView(R.id.orgin_num);
            final TextView answerNum = (TextView) holder.getView(R.id.answer_num);
            if (service.mode == 0) {
                orginNum.setVisibility(View.GONE);
                answerNum.setVisibility(View.GONE);
            } else if (service.mode == 1) {
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int visiblePosition = gridView.getFirstVisiblePosition();
                        View Preview = gridView.getChildAt(current - visiblePosition);
                        if (null != Preview) {
                            TextView tv = (TextView) Preview.findViewById(R.id.answer_num);
                            tv.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                       answerNum.setBackgroundColor(getResources().getColor(R.color.blue));
                        preAnswer = current;
                        current = holder.getPosition();

                    }
                });
                orginNum.setVisibility(View.GONE);
                answerNum.setVisibility(View.VISIBLE);
                if (holder.getPosition() == current) {
                    answerNum.setBackgroundColor(getResources().getColor(R.color.blue));
                }
            } else if (service.mode == 2) {
                orginNum.setVisibility(View.VISIBLE);
                answerNum.setVisibility(View.VISIBLE);
                orginNum.setText("" + figure.getRawId());
                holder.getConvertView().setOnClickListener(null);
                answerNum.setBackgroundColor(getResources().getColor(R.color.white));
                if (figure.getRawId() == figure.getAnswerId()) {
                    answerNum.setTextColor(getResources().getColor(R.color.black));
                } else {
                    answerNum.setTextColor(getResources().getColor(R.color.red));
                }
            }
        }
    }
}
