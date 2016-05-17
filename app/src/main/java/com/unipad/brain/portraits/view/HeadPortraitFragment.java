package com.unipad.brain.portraits.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.common.BasicFragment;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.brain.portraits.bean.Person;
import com.unipad.brain.portraits.control.HeadService;

import org.xutils.x;

import java.util.List;

/**
 * Created by gongkan on 2016/4/11.
 */
public class HeadPortraitFragment extends BasicFragment{
    private HeadAdapter adapter;
    private GridView mListView;
    private HeadService service;
    private View paraent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.portrait_fragment, container, false);

        mListView = (GridView) v.findViewById(R.id.gridview);

        service = (HeadService) (AppContext.instance().getService(Constant.HEADSERVICE));
        adapter = new HeadAdapter((Context) getActivity(), service.data, R.layout.list_portrait);
        paraent = v.findViewById(R.id.portraits_fragment);
        mListView.setAdapter(adapter);

        return v;
    }

    @Override
    public void changeBg(int color) {
        paraent.setBackgroundColor(color);
    }

    @Override
    public void memoryTimeToEnd() {
        showAnserView();
    }

    private void showAnserView() {
        service.mode = 1;
        service.shuffData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void rememoryTimeToEnd() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppContext.instance().clear(Constant.HEADSERVICE);
    }

    private class HeadAdapter extends CommonAdapter<Person> {


        public HeadAdapter(Context context, List<Person> datas, int layoutId) {
            super(context, datas, layoutId);
            Log.e("", "size=" + mDatas.size());
        }


        /**
         * @param holder
         * @param person
         */
        @Override

        public void convert(ViewHolder holder, final Person person) {
            ImageView headView = (ImageView) holder.getView(R.id.icon_head);

            x.image().bind(headView, person.getHeadPortraitPath());
            Log.e("---", "path = " + person.getHeadPortraitPath() + ",name=" + person.getFirstName() + person.getLastName());
            final EditText firstName = (EditText) holder.getView(R.id.first_name);
            final EditText lastName = (EditText) holder.getView(R.id.last_name);
            TextView holeName = (TextView) holder.getView(R.id.name_text);
            if (service.mode == 1) {
                holeName.setVisibility(View.GONE);
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                firstName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }


                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        person.setAnswerLastName(firstName.getText().toString().trim());
                    }
                });
                lastName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        person.setAnswerLastName(lastName.getText().toString().trim());
                    }
                });
            } else if (service.mode == 0) {
                firstName.setVisibility(View.GONE);
                lastName.setVisibility(View.GONE);
                holeName.setVisibility(View.VISIBLE);
                holeName.setText(person.getFirstName() + "·" + person.getLastName());
            }
        }
    }
}
