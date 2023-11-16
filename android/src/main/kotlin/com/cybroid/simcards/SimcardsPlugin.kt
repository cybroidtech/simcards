package com.cybroid.simcards

import androidx.annotation.NonNull

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

/** SimcardsPlugin */
class SimcardsPlugin: FlutterPlugin, MethodCallHandler, PluginRegistry.RequestPermissionsResultListener, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private val permissionCode : Int = 76

  private var currentActivity : Activity? = null
  private var context: Context? = null

  private var subscriptionManager : SubscriptionManager? = null

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "simcards")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "hasPermission") {
      // Check READ_PHONE_STATE already Granted
      result.success(hasPermission())
    } else if (call.method == "requestPermission") {
      // Request Request Permissions (READ_PHONE_STATE)
      requestPermission(result)
    } else if (call.method == "getSimCards") {
      // THIS PART HOLDS THE MAIN PLUGIN FUNCTIONALITY
      // Declare resultList
      val resultList = mutableListOf<HashMap<String, String?>>()
      val subscriptionInfos = subscriptionManager?.getActiveSubscriptionInfoList() ?: mutableListOf<SubscriptionInfo>()
      for (subInfo: SubscriptionInfo in subscriptionInfos) {
        resultList.add(
          hashMapOf<String, String?>(
            "carrierName" to subInfo.getCarrierName().toString(),
            "displayName" to subInfo.getDisplayName().toString(),
            "slotIndex" to subInfo.getSimSlotIndex().toString(),
            "number" to subInfo.getNumber(),
            "countryIso" to subInfo.getCountryIso(),
            "countryPhonePrefix" to ""
          )
        )
      }
      result.success(resultList)
    } else {
      result.notImplemented()
    }
  }

  private fun initSubscriptionManager() {
    subscriptionManager = try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context!!.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
      } else {
        SubscriptionManager.from(context!!)
      }
    } catch (_: Exception) {
      SubscriptionManager.from(context!!)
    }
  }

  private fun requestPermission(result: Result) {
    when {
      ContextCompat.checkSelfPermission(
        context!!,
        Manifest.permission.READ_PHONE_STATE
      ) == PackageManager.PERMISSION_GRANTED -> {
        result.success(true)
      }
      shouldShowRequestPermissionRationale(currentActivity!!, Manifest.permission.READ_PHONE_STATE) -> {
        result.error("Permission Denied", "This plugin requires READ_PHONE_STATE permission to work", Exception("This plugin requires READ_PHONE_STATE permission to work"))
      } else -> {
        ActivityCompat.requestPermissions(currentActivity!!, arrayOf(Manifest.permission.READ_PHONE_STATE), permissionCode)
      }
    }
  }

  private fun hasPermission(): Boolean {
    if (ContextCompat.checkSelfPermission(
      context!!,
      Manifest.permission.READ_PHONE_STATE
    ) == PackageManager.PERMISSION_GRANTED) {
      return true
    } else {
      return false
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ): Boolean {
    when (requestCode) {
      permissionCode -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
          initSubscriptionManager()
        }
        return true
      }
    }
    return false
  }

  override fun onDetachedFromActivity() {
    currentActivity == null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    currentActivity = binding.activity
    if (ContextCompat.checkSelfPermission(binding.activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
      initSubscriptionManager()
    }
    binding.addRequestPermissionsResultListener(this)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    currentActivity = binding.activity
    if (ContextCompat.checkSelfPermission(binding.activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
      initSubscriptionManager()
    }
    binding.addRequestPermissionsResultListener(this)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    currentActivity = null
  }

}


