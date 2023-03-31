import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_module/routes/mainPage.dart';
import 'package:flutter_module/routes/myHomePage.dart';
import 'package:flutter_module/routes/simplePage1.dart';

void main() {
  ///添加全局生命周期监听类
  PageVisibilityBinding.instance.addGlobalObserver(AppLifecycleObserver());

  CustomFlutterBinding();
  runApp(MyApp());
}

///全局生命周期监听
class AppLifecycleObserver with GlobalPageVisibilityObserver {
  @override
  void onBackground(Route route) {
    super.onBackground(route);
    print("AppLifecycleObserver - onBackground：${route.settings.name}");
  }

  @override
  void onForeground(Route route) {
    super.onForeground(route);
    print("AppLifecycleObserver - onForground：${route.settings.name}");
  }

  /// 对标Android onCreate
  @override
  void onPagePush(Route route) {
    super.onPagePush(route);
    print("AppLifecycleObserver - onPagePush：${route.settings.name}");
  }

  /// 对标Android onDestroy
  @override
  void onPagePop(Route route) {
    super.onPagePop(route);
    print("AppLifecycleObserver - onPagePop：${route.settings.name}");
  }

  /// 对标Android onStop
  @override
  void onPageHide(Route route) {
    super.onPageHide(route);
    print("AppLifecycleObserver - onPageHide：${route.settings.name}");
  }

  /// 对标Android onResume
  @override
  void onPageShow(Route route) {
    super.onPageShow(route);
    print("AppLifecycleObserver - onPageShow：${route.settings.name}");
  }
}

class CustomFlutterBinding extends WidgetsFlutterBinding with BoostFlutterBinding {}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Map<String, FlutterBoostRouteFactory> routerMap = {
    '/': (RouteSettings settings, String? uniqueId) {
      return MaterialPageRoute(
          settings: settings,
          builder: (_) {
            return MyHomePage(
              title: "主页",
            );
          });
    },
    'mainPage': (RouteSettings settings, String? uniqueId) {
      return MaterialPageRoute(
          settings: settings,
          builder: (_) {
            // Map<String, dynamic> map = jsonDecode(jsonEncode(settings.arguments)) ;
            Map<String, dynamic> map = settings.arguments as Map<String, dynamic>;
            return MainPage(
              data: map,
            );
          });
    },
    'simplePage1': (settings, uniqueId) {
      return MaterialPageRoute(
          settings: settings,
          builder: (_) {
            Map<String, dynamic> map = settings.arguments as Map<String, dynamic>;
            String data = map['data'] as String;
            return SimplePage1(
              data: data,
            );
          });
    },
  };

  Route<dynamic>? routeFactory(RouteSettings settings, String? uniqueId) {
    FlutterBoostRouteFactory? func = routerMap[settings.name];
    if (func == null) return null;
    return func(settings, uniqueId);
  }

  Widget appBuilder(Widget home) {
    return MaterialApp(
      home: home,
      debugShowCheckedModeBanner: true,
      ///必须加上builder参数，否则showDialog等会出问题
      builder: (_, __) {
        return home;
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return FlutterBoostApp(
      routeFactory,
      appBuilder: appBuilder,
    );
  }
}
