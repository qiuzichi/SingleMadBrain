package com.unipad.brain.location;

import android.os.Bundle;
import android.view.View;

import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;

/**
 * Created by Wbj on 2016/5/24.
 */
public class LocationActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_aty);
    }

    @Override
    public void initData() {
        findViewById(R.id.title_bar_left_text).setOnClickListener(this);
        findViewById(R.id.title_bar_right_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_bar_left_text:
                finish();
                break;
            case R.id.title_bar_right_text:
                break;
            default:
                break;
        }
    }

}
