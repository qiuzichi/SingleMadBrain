package com.unipad.brain.absPic.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.absPic.bean.Figure;
import com.unipad.brain.absPic.dao.FigureService;
import com.unipad.brain.portraits.bean.Person;
import com.unipad.brain.portraits.control.HeadService;
import com.unipad.common.BasicFragment;
import com.unipad.common.CommonFragment;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gongkan on 2016/4/15.
 */
public class AbsFigureFragment extends BasicFragment {

    private GridView gridView;
    private FigureService service;
    private FigureAdapter adapter;

    private int current;
    private int preAnswer;
    private View buttonArea;
    private View allArea;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_abs_figure, container, false);
    Log.e("","absFigure");
        gridView = (GridView) v.findViewById(R.id.abs_figure_gridview);
        buttonArea = v.findViewById(R.id.button_area);
        allArea = v.findViewById(R.id.abs_figure_fragment);
        v.findViewById(R.id.answer_1).setOnClickListener(this);
        v.findViewById(R.id.answer_2).setOnClickListener(this);
        v.findViewById(R.id.answer_3).setOnClickListener(this);
        v.findViewById(R.id.answer_4).setOnClickListener(this);
        v.findViewById(R.id.answer_5).setOnClickListener(this);
        service = (FigureService) (AppContext.instance().getService(Constant.ABS_FIGURE));
        adapter = new FigureAdapter(getActivity(), service.allFigures, R.layout.list_item_abs_figure);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int visiblePosition = parent.getFirstVisiblePosition();
                View Preview = parent.getChildAt(current - visiblePosition);
                if (null != Preview) {
                    TextView tv = (TextView) Preview.findViewById(R.id.answer_num);
                    tv.setBackgroundColor(getResources().getColor(R.color.white));
                }
                TextView currTv = (TextView) view.findViewById(R.id.answer_num);
                currTv.setBackgroundColor(getResources().getColor(R.color.blue));
                preAnswer = current;
                current = position;
            }
        });
        gridView.setAdapter(adapter);
        //adapter = new HeadAdapter((Context) getActivity(), service.data, R.layout.list_portrait);

        // paraent = v.findViewById(R.id.portraits_fragment);
        current = gridView.getFirstVisiblePosition();
        setButtonArea();
        return v;
    }


    private void setButtonArea() {
        if (service.mode == 1) {
            buttonArea.setVisibility(View.VISIBLE);
        } else {
            buttonArea.setVisibility(View.GONE);
        }
    }

    @Override
    public void changeBg(int color) {
        allArea.setBackgroundColor(color);
    }

    @Override
    public void memoryTimeToEnd() {
        service.mode = 1;
        service.shuffle();
        setButtonArea();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void rememoryTimeToEnd() {
        service.mode = 2;
        setButtonArea();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void exitActivity() {
        AppContext.instance().clear(Constant.ABS_FIGURE);
        getActivity().finish();
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
        TextView currTv = (TextView) curr.findViewById(R.id.answer_num);
        currTv.setBackgroundColor(getResources().getColor(R.color.blue));
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

        public void convert(ViewHolder holder, final Figure figure) {
            ImageView headView = (ImageView) holder.getView(R.id.icon_absfigure);

            x.image().bind(headView, figure.getPath());
            Log.e("","path:"+figure.getPath());
            //Log.e("---", "path = " + person.getHeadPortraitPath() + ",name=" + person.getFirstName() + person.getLastName());
            final TextView orginNum = (TextView) holder.getView(R.id.orgin_num);
            final TextView answerNum = (TextView) holder.getView(R.id.answer_num);
            if (service.mode == 0) {
                orginNum.setVisibility(View.GONE);
                answerNum.setVisibility(View.GONE);
            } else if (service.mode == 1) {
                orginNum.setVisibility(View.GONE);
                answerNum.setVisibility(View.VISIBLE);
                if (holder.getPosition() == current) {
                    answerNum.setBackgroundColor(getResources().getColor(R.color.blue));
                }
            } else if (service.mode == 2) {
                orginNum.setVisibility(View.VISIBLE);
                answerNum.setVisibility(View.VISIBLE);
                orginNum.setText(""+figure.getRawId());
                if (figure.getRawId() == figure.getAnswerId()) {
                    answerNum.setTextColor(getResources().getColor(R.color.black));
                } else {
                    answerNum.setTextColor(getResources().getColor(R.color.red));
                }
            }
        }
    }
}
