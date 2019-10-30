package com.example.zj.androidstudy.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.zj.androidstudy.BuildConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

import static android.content.Context.TELECOM_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created on 2019-10-28.
 */
public class AppUtil {

  @SuppressLint("MissingPermission")
  public static SmsManager getSmsManager(Activity context, String phoneNumber) {
    if (TextUtils.isEmpty(phoneNumber)) {
      return SmsManager.getDefault();
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
      SubscriptionManager subscriptionManager =
          (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
      if (subscriptionManager.getActiveSubscriptionInfoCount() >= 1) {
        List<SubscriptionInfo> localList = subscriptionManager.getActiveSubscriptionInfoList();
        for (SubscriptionInfo info : localList) {
          if (phoneNumber.equals(info.getNumber())) {
            return SmsManager.getSmsManagerForSubscriptionId(info.getSubscriptionId());
          }
        }
      }
    }
    return SmsManager.getDefault();
  }

  /**
   * 获取Sim卡中的信息
   * 能够知道手机中有几张卡和卡的基本信息
   */
  @SuppressLint("MissingPermission")
  @NonNull
  @Deprecated
  public static HashMap<String, Object> getSimCardInfo(Context context) {
    HashMap<String, Object> totalSimCardInfo = new HashMap<>();
    try {
      TelecomManager telecomManager = null;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
          (telecomManager = (TelecomManager) context.getSystemService(TELECOM_SERVICE)) != null) {
        List<PhoneAccountHandle> callCapablePhoneAccounts = telecomManager.getCallCapablePhoneAccounts();
        List<Map<String, String>> phoneAccountInfo = new ArrayList<>();
        if (callCapablePhoneAccounts != null && !callCapablePhoneAccounts.isEmpty()) {
          for (PhoneAccountHandle handle : callCapablePhoneAccounts) {
            PhoneAccount phoneAccount = telecomManager.getPhoneAccount(handle);
            HashMap<String, String> map = new HashMap<>();
            map.put("id", handle.getId());//sim卡id号，代表手机上插的第几张sim卡
            map.put("lable", phoneAccount.getLabel().toString());//运营商的lable
            map.put("shortDescription", phoneAccount.getShortDescription().toString());
            map.put("phoneNumber", telecomManager.getLine1Number(handle));//sim卡手机号，拿不到时为空
            phoneAccountInfo.add(map);
          }
        }
        totalSimCardInfo.put("simCardInfoList", phoneAccountInfo);
      }

      TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
      if (telephonyManager != null) {
        totalSimCardInfo.put("subscribeId", telephonyManager.getSubscriberId());
        totalSimCardInfo.put("simOperatorName", telephonyManager.getSimOperatorName());
        totalSimCardInfo.put("simOperator", telephonyManager.getSimOperator());
        totalSimCardInfo.put("simCountryIso", telephonyManager.getSimCountryIso());
        totalSimCardInfo.put("usePhoneNumber", telephonyManager.getLine1Number());
      }
    } catch (Exception e) {
      if (BuildConfig.DEBUG) {
        throw e;
      }
    }
    return totalSimCardInfo;
  }
}
