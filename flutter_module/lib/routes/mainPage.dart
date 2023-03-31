import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

import '../common/tools.dart';

class MainPage extends StatefulWidget {
  const MainPage({Key? key, required this.data}) : super(key: key);
  final Map<String, dynamic> data;

  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  late VoidCallback _removeListener;

  @override
  void initState() {
    super.initState();

    ///添加事件响应者,监听native发往flutter端的事件
    _removeListener =
        BoostChannel.instance.addEventListener("native_to_flutter_event", (key, arguments) async {
      print('flutter接收 key:${key}');
      print('flutter接收 arguments:${arguments}');
      // return null;
    });
  }

  @override
  void dispose() {
    super.dispose();
    _removeListener.call();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Main Page'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('get time：${widget.data['data']}'),
            if (widget.data['hasResult'])
              ElevatedButton(
                child: const Text('退出当前页面，返回参数给上一个Native页面'),
                onPressed: () {
                  // Navigator.of(context).pop({'retval' : 'I am from dart 11'});
                  BoostNavigator.instance.pop({'retval': 'I am from dart'});
                },
              ),
            ElevatedButton(
              child: const Text('打开原生界面'),
              onPressed: () {
                BoostNavigator.instance
                    .push("go_to_NativeActivity1") // Native页面路由
                    .then((value) => showToast('from native retval:$value'));
              },
            ),
            ElevatedButton(
              child: const Text('打开并传参给原生界面'),
              onPressed: () {
                BoostNavigator.instance.push(
                  "go_to_NativeActivity2",
                  withContainer: false,
                  arguments: {"msg2222": "hello,native boy"},
                  opaque: true,
                );
              },
            ),
            ElevatedButton(
              child: const Text('打开一个 flutter 界面'),
              onPressed: () async {
                final result = await BoostNavigator.instance.push('simplePage1',
                    withContainer: false, arguments: {"data": "hello,flutter boy"}, opaque: false);

                /// withContainer为 false 时，也可以使用原生的 Navigator
                // final result = await Navigator.of(context).pushNamed('simplePage1', arguments: {"data": "hello,flutter boy 1111"});
                showToast(result);
              },
            ),
            ElevatedButton(
              child: const Text('发送消息给 native'),
              onPressed: () async {
                BoostChannel.instance.sendEventToNative(
                    "flutter_to_native_event", {"msg5555555": "my ${DateTime.now().millisecondsSinceEpoch}"});
              },
            ),
          ],
        ),
      ),
    );
  }
}
