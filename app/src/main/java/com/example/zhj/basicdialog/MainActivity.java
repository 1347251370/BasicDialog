package com.example.zhj.basicdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BasicDialog basicDialog = new BasicDialog(this,"我是dialog的内容","确定",MainActivity.this);
        basicDialog.show();
    }
}
