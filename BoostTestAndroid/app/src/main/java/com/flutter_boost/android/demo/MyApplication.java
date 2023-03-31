package com.flutter_boost.android.demo;

import android.app.Application;
import android.content.Intent;

import com.flutter_boost.android.demo.activity.NativeActivity1;
import com.flutter_boost.android.demo.activity.NativeActivity2;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.FlutterBoostDelegate;
import com.idlefish.flutterboost.FlutterBoostRouteOptions;
import com.idlefish.flutterboost.containers.FlutterBoostActivity;

import java.util.Map;

import io.flutter.embedding.android.FlutterActivityLaunchConfigs;
import io.flutter.embedding.engine.FlutterEngine;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FlutterBoost.instance().setup(this, new FlutterBoostDelegate() {
            @Override
            public void pushNativeRoute(FlutterBoostRouteOptions options) {
                System.out.println("flutter to native requestCode：" + options.requestCode());
                if ("go_to_NativeActivity1".equals(options.pageName())) {
                    Intent intent = new Intent(FlutterBoost.instance().currentActivity(), NativeActivity1.class);
                    FlutterBoost.instance().currentActivity().startActivityForResult(intent, options.requestCode());
                } else if ("go_to_NativeActivity2".equals(options.pageName())) {
                    Map<String, Object> map = options.arguments();
                    Intent intent = new Intent(FlutterBoost.instance().currentActivity(), NativeActivity2.class);
                    intent.putExtra("data", (String) map.get("msg2222"));
                    FlutterBoost.instance().currentActivity().startActivity(intent);
                }
            }

            @Override
            public void pushFlutterRoute(FlutterBoostRouteOptions options) {
                Intent intent = new FlutterBoostActivity.CachedEngineIntentBuilder(FlutterBoostActivity.class)
                        .backgroundMode(options.opaque() ? FlutterActivityLaunchConfigs.BackgroundMode.opaque : FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                        .destroyEngineWithActivity(false)
                        .uniqueId(options.uniqueId())
                        .url(options.pageName())
                        .urlParams(options.arguments())
                        .build(FlutterBoost.instance().currentActivity());

                if (options.requestCode() == 0) {
                    FlutterBoost.instance().currentActivity().startActivity(intent);
                } else {
                    FlutterBoost.instance().currentActivity().startActivityForResult(intent, options.requestCode());
                }
            }
        }, engine -> {

        });
    }
}
