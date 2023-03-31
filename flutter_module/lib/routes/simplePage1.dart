import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

class SimplePage1 extends StatelessWidget {
  const SimplePage1({required this.data});

  final String data;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Simple Page1'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('$data'),
            ElevatedButton(
              child: const Text('退出当前界面，传递参数给上一个Flutter页面'),
              onPressed: () async {
                // 这里也可以使用原生的 Navigator
                BoostNavigator.instance.pop({'retval': 'you are a flutter boy！！'});
              },
            ),
          ],
        ),
      ),
    );
  }
}
