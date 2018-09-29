package com.tendcloud.dynamictddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tendcloud.dynamictddemo.util.LoadUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/**
        TCAgent.LOG_ON=true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this, "4735F4BF9074472FB3AB019786056828", "honghualong_test");
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
        TCAgent.setReportUncaughtExceptions(true);
       // TCAgent.onViewItem("007", "家电", "电视机", 4999);
        TCAgent.onEvent(MainActivity.this, "777777777");
        TCAgent.onEvent(MainActivity.this, "2222222222222", "testtesttest");

 **/
        LoadUtil.getInstance(MainActivity.this).init();
    }

    //TD自定义事件

}
