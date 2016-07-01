package com.unipad.brain.personal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.brain.R;

public class PersonalInfoActivty extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_integration_layout);
        initdata();
    }

    private void initdata() {
        TextView text_myranking=(TextView)findViewById(R.id.text_myranking);
        ListView lv_integration=(ListView)findViewById(R.id.lv_integration);

    }

    @Override
    public void onClick(View v) {

    }
}
