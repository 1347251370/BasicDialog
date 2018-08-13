package com.example.zhj.basicdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BasicDialog basicDialog = new BasicDialog(this, "我是dialog的内容", "确定", new BasicDialog.SingleClickListener() {
            @Override
            public void clickSingleBtn() {
                BasicDialog basicDialog1 = new BasicDialog(MainActivity.this, "我是dialog的内容", "确定", "取消", new BasicDialog.DoubleClickListener() {
                    @Override
                    public void clickLeftBtn() {
                        Toast.makeText(MainActivity.this,"点击了“确定”",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void clickRightBtn() {
                        Toast.makeText(MainActivity.this,"点击了“取消”",Toast.LENGTH_LONG).show();
                    }
                },MainActivity.this);
                basicDialog1.show();
            }
        },MainActivity.this);
        basicDialog.show();
    }
}
