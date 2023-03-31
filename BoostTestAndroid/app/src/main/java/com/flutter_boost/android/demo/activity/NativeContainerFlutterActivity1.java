package com.flutter_boost.android.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.flutter_boost.android.demo.R;
import com.idlefish.flutterboost.EventListener;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.ListenerRemover;
import com.idlefish.flutterboost.containers.FlutterBoostFragment;

import java.util.HashMap;
import java.util.Map;


/*
FlutterFragmentActivity
 */
public class NativeContainerFlutterActivity1 extends FragmentActivity {

    private FlutterBoostFragment flutterBoostFragment;
    private ListenerRemover remover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_container_flutter1);

        /*************************************** 加载flutter ***********************************************/

        boolean hasResult = getIntent().getBooleanExtra("hasResult", false);

        Map<String, Object> map = new HashMap<>();
        map.put("data", System.currentTimeMillis() + " by FlutterBoostFragment");
        map.put("hasResult", hasResult);

        flutterBoostFragment = new FlutterBoostFragment.CachedEngineFragmentBuilder()
                .url("mainPage")
                .urlParams(map)
                .build();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.my_container, flutterBoostFragment)
                .commit();
        /**************************************************************************************************/

        /*************************************** 自定义事件传递 ***********************************************/
        initEventListener();
        findViewById(R.id.btn_sendToFlutter).setOnClickListener(v -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("msg444", "you " + System.currentTimeMillis());
            FlutterBoost.instance().sendEventToFlutter("native_to_flutter_event", map1);
        });
        /**************************************************************************************************/

    }

    private void initEventListener() {
        remover = FlutterBoost.instance().addEventListener("flutter_to_native_event", new EventListener() {
            @Override
            public void onEvent(String key, Map<String, Object> args) {
                System.out.println("native接收 key:" + key);
                System.out.println("native接收 args:" + args);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除监听
        remover.remove();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        flutterBoostFragment.onActivityResult(requestCode, resultCode, data);
    }
}