
import 'package:simcards/sim_card.dart';

import 'simcards_platform_interface.dart';

class Simcards {
  Future<String?> getPlatformVersion() {
    return SimcardsPlatform.instance.getPlatformVersion();
  }

  /// Requests the `READ_PHONE_STATE` permission
  Future<void> requestPermission() {
    return SimcardsPlatform.instance.requestPermission();
  }

  /// Checks whether the `READ_PHONE_STATE` Permission is granted
  /// 
  /// **Note**: 
  /// - *Call this function before the `getSimCards` function
  /// to ensure the permission is granted*
  Future<bool> hasPermission() {
    return SimcardsPlatform.instance.hasPermission();
  }

  /// Returns Available SimCards
  /// 
  /// **Note**: 
  /// - *`READ_PHONE_STATE` Permission is Required*
  Future<List<SimCard>> getSimCards() {
    return SimcardsPlatform.instance.getSimCards();
  }
}
