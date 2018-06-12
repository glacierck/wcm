package com.kongrongqi.shopmall.utils.permission;

import android.Manifest;

/**
 * 创建日期：2017/6/6 0006 on 14:34
 * 作者:penny
 */
public interface Permissions {

    /**
     * sms
     */
    String SEND_SMS = Manifest.permission.SEND_SMS;
    String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    String READ_SMS = Manifest.permission.READ_SMS;
    String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    /**
     * STORAGE
     */
    String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    /**
     * CONTACTS
     */
    String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    /**
     * phone
     */
    String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    String CALL_PHONE = Manifest.permission.CALL_PHONE;
    String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    String USE_SIP = Manifest.permission.USE_SIP;
    String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    /**
     *
     */
    String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    String CAMERA = Manifest.permission.CAMERA;
    String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
}