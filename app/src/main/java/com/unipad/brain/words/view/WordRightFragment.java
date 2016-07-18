package com.unipad.brain.words.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.words.bean.WordEntity;
import com.unipad.brain.words.dao.WordsService;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.common.adapter.ListGridAdapter;
import com.unipad.io.mina.SocketThreadManager;
import com.unipad.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * liupeng
 */
public class WordRightFragment extends BasicCommonFragment {
    private GridView listView;
    private WordAdapter adapter;
    private WordsService service;
    private ViewStub mStubShade;
    private HorizontalScrollView scrollView;

    @Override
    public void onClick(View view) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.word_frg_right;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (GridView) mViewParent.findViewById(R.id.word_listview);
        service = (WordsService) AppContext.instance().getService(Constant.WORDS_SERVICE);
        mStubShade = (ViewStub) mViewParent.findViewById(R.id.view_shade);
        mStubShade.setVisibility(View.VISIBLE);
    }

    @Override
    public void startMemory() {
        mStubShade.setVisibility(View.GONE);
    }

    @Override
    public void pauseGame() {
        mStubShade.setVisibility(View.VISIBLE);
    }

    @Override
    public void reStartGame() {
        mStubShade.setVisibility(View.GONE);
    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (260 * service.lines * density);
        int itemWidth = (int) (250 * density);


        adapter = new WordAdapter(mActivity, Arrays.asList(service.wordEntities), R.layout.list_item_random_words);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        listView.setColumnWidth(itemWidth);
        listView.setHorizontalSpacing(10);
        listView.setStretchMode(GridView.NO_STRETCH);
        listView.setNumColumns(service.lines);
        listView.setAdapter(adapter);
        mStubShade.setVisibility(View.GONE);
    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);

        service.mode = 1;
        adapter.notifyDataSetChanged();
        mActivity.getCommonFragment().startRememoryTimeCount();
    }

    @Override
    public void rememoryTimeToEnd(final int answerTime) {

        service.mode = 2;
        adapter.notifyDataSetChanged();
        new Thread() {
            @Override
            public void run() {
                SocketThreadManager.sharedInstance().finishedGameByUser(mActivity.getMatchId(), service.getScore(), memoryTime, answerTime, service.getAnswerData());
            }
        }.start();

    }

    private class WordAdapter extends CommonAdapter<WordEntity> {


        public WordAdapter(Context context, List<WordEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, final WordEntity wordEntity) {
            if (wordEntity != null) {
                TextView textNum = holder.getView(R.id.words_num);
                TextView textWord = holder.getView(R.id.words);
                LogUtil.e("", "" + wordEntity.getNumber());
                textNum.setText("" + wordEntity.getNumber());
                final EditText userAnswerEdit = holder.getView(R.id.words_answer);
                switch (service.mode) {
                    case 0:
                        userAnswerEdit.setVisibility(View.GONE);
                        textWord.setText(wordEntity.getWord());
                        break;
                    case 1:
                        textWord.setVisibility(View.GONE);
                        userAnswerEdit.setVisibility(View.VISIBLE);
                        userAnswerEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                return false;
                            }
                        });
                        userAnswerEdit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                wordEntity.setAnswer(userAnswerEdit.getText().toString().trim());
                            }
                        });
                        break;
                    case 2:
                        userAnswerEdit.setVisibility(View.GONE);
                        textWord.setVisibility(View.VISIBLE);
                        if (TextUtils.isEmpty(wordEntity.getAnswer()) || !wordEntity.getAnswer().equals(wordEntity.getWord())) {
                            textWord.setTextColor(getResources().getColor(R.color.red));
                        }
                        textWord.setText(wordEntity.getWord() + "/" + wordEntity.getAnswer());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
