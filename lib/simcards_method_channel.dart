import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:simcards/sim_card.dart';

import 'simcards_platform_interface.dart';

/// An implementation of [SimcardsPlatform] that uses method channels.
class MethodChannelSimcards extends SimcardsPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('simcards');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  /// Checks whether the `READ_PHONE_STATE` Permission is granted
  ///
  /// **Note**:
  /// - *Call this function before the `getSimCards` function
  /// to ensure the permission is granted*
  @override
  Future<bool> hasPermission() async {
    bool? hasPermission =
        await methodChannel.invokeMethod<bool>('hasPermission');
    return hasPermission ?? false;
  }

  /// Requests the `READ_PHONE_STATE` permission
  @override
  Future<void> requestPermission() async {
    await methodChannel.invokeMethod('requestPermission');
  }

  /// Returns Available SimCards
  ///
  /// **Note**:
  /// - *`READ_PHONE_STATE` Permission is Required*
  @override
  Future<List<SimCard>> getSimCards() async {
    List<SimCard> simCards = [];
    final simCardsList = await methodChannel
        .invokeListMethod("getSimCards");
    for (var simJson in simCardsList ?? []) {
      simCards.add(SimCard.fromMap(Map<String, dynamic>.from(simJson)));
    }
    return simCards;
  }
}
