package com.unipad.brain.words.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.words.bean.WordEntity;
import com.unipad.brain.words.dao.WordsService;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.io.mina.SocketThreadManager;
import com.unipad.utils.LogUtil;

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
    private RecyclerView wordRv;
    private List<WordEntity> mData;
    private WordRvAdapter wordRvAdapter;
    private GridLayoutManager manager;

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
        wordRv = (RecyclerView) mViewParent.findViewById(R.id.word_rv);
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

        mData = Arrays.asList(service.wordEntities);
//        adapter = new WordAdapter(mActivity, Arrays.asList(service.wordEntities), R.layout.list_item_random_words);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
//        listView.setLayoutParams(params);
//        listView.setColumnWidth(itemWidth);
//        listView.setHorizontalSpacing(10);
//        listView.setStretchMode(GridView.NO_STRETCH);
//        listView.setNumColumns(service.lines);
//        listView.setAdapter(adapter);
        mStubShade.setVisibility(View.GONE);

        manager = new GridLayoutManager(getActivity(), 20);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        wordRv.setLayoutManager(manager);
        wordRvAdapter = new WordRvAdapter();
        wordRv.setAdapter(wordRvAdapter);
        wordRv.setHasFixedSize(true);
    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);

        service.mode = 1;
        wordRvAdapter.notifyDataSetChanged();
    }

    @Override
    public void rememoryTimeToEnd(final int answerTime) {

        service.mode = 2;
        ///adapter.notifyDataSetChanged();
        wordRvAdapter.notifyDataSetChanged();
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

    class WordRvAdapter extends RecyclerView.Adapter<WordRvAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.list_item_random_words, parent,
                    false),new MyCustomEditTextListener());
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.textNum.setText(mData.get(position).getNumber() + "");
            switch (service.mode) {
                case 0:
                    holder.userAnswerEdit.setVisibility(View.GONE);
                    holder.textWord.setText(mData.get(position).getWord());
                    break;
                case 1:
                    holder.textWord.setVisibility(View.GONE);
                    holder.userAnswerEdit.setVisibility(View.VISIBLE);
                    holder.myCustomEditTextListener.updatePosition(position);
                    holder.userAnswerEdit.setText(mData.get(position).getAnswer());
                    holder.userAnswerEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            System.out.println(holder.getLayoutPosition() + "---" + holder.getAdapterPosition()
                                    + "----" + position + "--first==" + manager.findFirstVisibleItemPosition());
                            wordRv.smoothScrollToPosition(position + 20 < wordRvAdapter.getItemCount() ? position + 20 : wordRvAdapter.getItemCount() - 1);
                            if (position < wordRvAdapter.getItemCount() - 1) {
                                System.out.println("" + position + "--" + (wordRvAdapter.getItemCount() - 1));
                                wordRv.getChildAt(position + 1 - manager.findFirstVisibleItemPosition()).requestFocus();
                            }
                            return true;
                        }
                    });
//                    holder.userAnswerEdit.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//                            System.out.println("jinruafterTextChanged");
//                            mData.get(position).setAnswer(holder.userAnswerEdit.getText().toString().trim());
//                        }
//                    });
                    break;
                case 2:
                    holder.userAnswerEdit.setVisibility(View.GONE);
                    holder.textWord.setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(mData.get(position).getAnswer()) || !mData.get(position).getAnswer().equals(mData.get(position).getWord())) {
                        holder.textWord.setTextColor(getResources().getColor(R.color.red));
                    }
                    holder.textWord.setText(mData.get(position).getWord() + "/" + mData.get(position).getAnswer());
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textNum, textWord;
            EditText userAnswerEdit;
            MyCustomEditTextListener myCustomEditTextListener;

            public MyViewHolder(View itemView,MyCustomEditTextListener myCustomEditTextListener) {
                super(itemView);
                textNum = (TextView) itemView.findViewById(R.id.words_num);
                textWord = (TextView) itemView.findViewById(R.id.words);
                userAnswerEdit = (EditText) itemView.findViewById(R.id.words_answer);
                this.myCustomEditTextListener = myCustomEditTextListener;
                userAnswerEdit.addTextChangedListener(myCustomEditTextListener);
            }
        }

        private class MyCustomEditTextListener implements TextWatcher {
            private int position;

            public void updatePosition(int position) {
                this.position = position;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mData.get(position).setAnswer(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        }
    }
}
