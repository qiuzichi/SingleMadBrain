package com.unipad.brain.portraits.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.R;
import com.unipad.brain.portraits.control.HeadService.INotifyInitData;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.brain.portraits.bean.Person;
import com.unipad.brain.portraits.control.HeadService;
import com.unipad.io.mina.SocketThreadManager;

import org.xutils.x;

import java.util.List;

/**
 * Created by gongkan on 2016/4/11.
 */
public class HeadPortraitFragment extends BasicCommonFragment {
    private HeadAdapter adapter;
    private GridView mListView;


    public HeadPortraitFragment(AbsBaseGameService service) {
        super(service);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = (GridView) mViewParent.findViewById(R.id.gridview);

        service = (HeadService) (AppContext.instance().getService(Constant.HEADSERVICE));

        adapter = new HeadAdapter(mActivity, ((HeadService)service).data, R.layout.list_portrait);
        mListView.setAdapter(adapter);

    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        adapter.notifyDataSetChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SocketThreadManager.sharedInstance().downLoadQuestionOK(mActivity.getMatchId());
            }
        }).start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.portrait_fragment;
    }

    @Override
    public void memoryTimeToEnd() {
        showAnserView();
    }

    private void showAnserView() {
        service.mode = 1;
        ((HeadService)service).shuffData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startGame() {


    }

    @Override
    public void rememoryTimeToEnd() {
        service.mode = 2;
        adapter.notifyDataSetChanged();
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
        public void convert(final ViewHolder holder, final Person person) {
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
                        person.setAnswerFirstName(firstName.getText().toString().trim());
                    }
                });
                lastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        person.setAnswerLastName(lastName.getText().toString().trim());
                        int visiblePosition = mListView.getFirstVisiblePosition();
                        View Preview = mListView.getChildAt(holder.getPosition() + 1 - visiblePosition);
                        if (null != Preview) {
                            EditText firstName = (EditText) Preview.findViewById(R.id.first_name);
                            firstName.requestFocus();
                        }
                        return false;
                    }
                });

            } else if (service.mode == 0) {
                firstName.setVisibility(View.GONE);
                lastName.setVisibility(View.GONE);
                holeName.setVisibility(View.VISIBLE);
                holeName.setText(person.getFirstName() + "·" + person.getLastName());
            } else if (service.mode == 2) {
                lastName.setVisibility(View.GONE);
                firstName.setVisibility(View.GONE);
                holeName.setVisibility(View.VISIBLE);
                TextView answerHoleName = (TextView) holder.getView(R.id.answer_name_text);
                if (!person.isAnswerRight()) {
                    answerHoleName.setTextColor(mContext.getResources().getColor(R.color.red));
                }
                holeName.setText(person.getFirstName() + "·" + person.getLastName());
                answerHoleName.setVisibility(View.VISIBLE);
                answerHoleName.setText(person.getAnswerFirstName() + "·" + person.getAnswerLastName());
            }
        }
    }
}
