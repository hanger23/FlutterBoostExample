package com.flutter_boost.android.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.flutter_boost.android.demo.R;

import java.util.Objects;

public class NativeActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native1);

        setTitle("原生界面1");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // 退出当前页面，并返回参数给上一个Flutter页面
        // 实质是返回参数给上一个Flutter页面的容器activity，然后容器activity 通过 channel 发送给 Flutter页面
        findViewById(R.id.btn_ret_result).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("msg", "This message is from Native111");
            intent.putExtra("bool", true);
            intent.putExtra("int", 666);
            setResult(Activity.RESULT_OK, intent);  // 返回结果给dart
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}