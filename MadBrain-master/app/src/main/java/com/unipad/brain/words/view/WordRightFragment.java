package com.unipad.brain.words.view;

import android.content.Context;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.ListView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.words.bean.WordEntity;
import com.unipad.brain.words.dao.WordsService;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.common.adapter.ListGridAdapter;

/**
 * liupeng
 */
public class WordRightFragment extends BasicCommonFragment {
    private ListView listView;
    private WordAdapter adapter;
    private WordsService service;
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
        listView = (ListView) mViewParent.findViewById(R.id.word_listview);
        adapter = new WordAdapter(mActivity,R.layout.list_item_random_words);
        service = (WordsService) AppContext.instance().getService(Constant.WORDS_SERVICE);
        adapter.setData(service.words);
    }

    @Override
    public void memoryTimeToEnd() {
    }

    @Override
    public void rememoryTimeToEnd() {

    }

    private class WordAdapter extends ListGridAdapter<WordEntity>{

        public WordAdapter(Context context, int resId) {
            super(context, resId);
        }

        @Override
        public void bindView(View convertView, WordEntity info) {
            super.bindView(convertView, info);

        }
    }
}
