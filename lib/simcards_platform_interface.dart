import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:simcards/sim_card.dart';

import 'simcards_method_channel.dart';

abstract class SimcardsPlatform extends PlatformInterface {
  /// Constructs a SimcardsPlatform.
  SimcardsPlatform() : super(token: _token);

  static final Object _token = Object();

  static SimcardsPlatform _instance = MethodChannelSimcards();

  /// The default instance of [SimcardsPlatform] to use.
  ///
  /// Defaults to [MethodChannelSimcards].
  static SimcardsPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SimcardsPlatform] when
  /// they register themselves.
  static set instance(SimcardsPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> hasPermission() {
    throw UnimplementedError('hasPermission() has not been implemented.');
  }

  Future<void> requestPermission() {
    throw UnimplementedError('requestPermission() has not been implemented.');
  }

  Future<List<SimCard>> getSimCards() {
    throw UnimplementedError('getSimCards() has not been implemented.');
  }

}
