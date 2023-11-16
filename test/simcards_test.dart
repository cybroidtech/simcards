import 'package:flutter_test/flutter_test.dart';
import 'package:simcards/sim_card.dart';
import 'package:simcards/simcards.dart';
import 'package:simcards/simcards_platform_interface.dart';
import 'package:simcards/simcards_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockSimcardsPlatform
    with MockPlatformInterfaceMixin
    implements SimcardsPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<List<SimCard>> getSimCards() => Future.value(<SimCard>[]);

  @override
  Future<bool> hasPermission() => Future.value(true);

  @override
  Future<void> requestPermission() async {
    await Future.delayed(Duration.zero);
  }
}

void main() {
  final SimcardsPlatform initialPlatform = SimcardsPlatform.instance;

  test('$MethodChannelSimcards is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSimcards>());
  });

  test('getPlatformVersion', () async {
    Simcards simcardsPlugin = Simcards();
    MockSimcardsPlatform fakePlatform = MockSimcardsPlatform();
    SimcardsPlatform.instance = fakePlatform;

    expect(await simcardsPlugin.getPlatformVersion(), '42');
  });
}
