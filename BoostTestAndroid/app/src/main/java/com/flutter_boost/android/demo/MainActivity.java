package com.flutter_boost.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.flutter_boost.android.demo.activity.NativeContainerFlutterActivity1;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.FlutterBoostRouteOptions;

import java.util.HashMap;
import java.util.Map;

import static com.idlefish.flutterboost.containers.FlutterActivityLaunchConfigs.ACTIVITY_RESULT_KEY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FlutterBoostActivity:原生启动 flutter
        findViewById(R.id.btn_1).setOnClickListener(view -> {
            String[] options = {"无返回值", "有返回值"};
            new AlertDialog.Builder(this)
                    .setTitle("FlutterBoostActivity:原生启动 flutter ")
                    .setItems(options, (dialog, which) -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("data", System.currentTimeMillis() + " by FlutterBoostActivity");

                        FlutterBoostRouteOptions.Builder builder = new FlutterBoostRouteOptions.Builder()
                                .pageName("mainPage")
                                .opaque(false)
                                .arguments(map);

                        if (which == 0) {
                            map.put("hasResult", false);
                        } else if (which == 1) {
                            map.put("hasResult", true);
                            builder.requestCode(1000);
                        }

                        FlutterBoost.instance().open(builder.build());
                    }).show();
        });

        // FlutterBoostFragment:原生启动 flutter
        findViewById(R.id.btn_2).setOnClickListener(view -> {
            String[] options = {"无返回值", "有返回值"};
            new AlertDialog.Builder(this)
                    .setTitle("FlutterBoostFragment:原生启动 flutter")
                    .setItems(options, (dialog, which) -> {
                        Intent intent = new Intent(this, NativeContainerFlutterActivity1.class);
                        if (which == 0) {
                            intent.putExtra("hasResult", false);
                            startActivity(intent);
                        } else if (which == 1) {
                            intent.putExtra("hasResult", true);
                            startActivityForResult(intent, 1001);
                        }
                    }).show();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 || requestCode == 1001) {
            if (data == null) return;
            Map<String, Object> ret = (Map<String, Object>) data.getSerializableExtra(ACTIVITY_RESULT_KEY);
            Toast.makeText(this, "获取到：" + ret, Toast.LENGTH_SHORT).show();
        }
    }
}