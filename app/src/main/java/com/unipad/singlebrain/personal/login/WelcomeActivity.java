package com.unipad.singlebrain.personal.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.os.CountDownTimer;
import com.unipad.singlebrain.BasicActivity;
import com.unipad.singlebrain.R;

/**欢迎界面
 * Created by Jianglu on 2016/4/14.
 */
public class WelcomeActivity extends BasicActivity implements View.OnClickListener{


    private TextView txt_jump;
    private Boolean isClose = false;
    //欢迎界面 停留时间；毫秒
    private final int maxLontTime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }



    @Override
    public void initData() {
        txt_jump = (TextView) findViewById(R.id.txt_jump_over);
        txt_jump.setOnClickListener(this);
       /* 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        因此，设置间隔的时候，默认减去了10ms，从而减去误差。
         经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常*/
        new CountDownTimer(maxLontTime, 1000 - 10) {
            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                txt_jump.setText(((time + 15) / 1000)
                        + getString(R.string.jump_over));
                Log.d("CountDownButtonHelper", "time = " + (time) + " text = "
                        + ((time + 15) / 1000));
            }

            @Override
            public void onFinish() {
                txt_jump.setText("0 " + getString(R.string.jump_over));
                if(!isClose){
                    closeView();
                }
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_jump_over:
                closeView();
                break;
            default:
                break;
        }
    }

    private void closeView() {
        //点击关闭 就会关闭界面 开启登录界面
        isClose = true;
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
