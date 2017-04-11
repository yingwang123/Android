package com.example.user.myapplication.activies;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.R;
//git remote rm origin 删除远程仓库连接

public class Intent1Activity extends ActionBarActivity implements View.OnClickListener {

    private Button intentAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent1);
        initView();
        registerListener();

    }

    private void initView() {
        intentAlarm = (Button) findViewById(R.id.btn_intentAlarm);
    }

    private void registerListener() {
        intentAlarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_intentAlarm:
                createAlarm("我的小闹钟",2,30);
                break;
        }
    }

    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
