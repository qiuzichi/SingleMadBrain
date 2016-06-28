package com.unipad.brain.personal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.brain.R;

public class PersonalInfoActivty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_integration_layout);
        intdata();
    }

    private void intdata() {
        TextView text_myranking=(TextView)findViewById(R.id.text_myranking);
        ListView lv_integration=(ListView)findViewById(R.id.lv_integration);

    }
}
