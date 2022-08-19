package com.example.project_4_admin.Common;


import android.text.format.DateFormat;

import com.example.project_4_admin.Remote.APIService;
import com.example.project_4_admin.Remote.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class Common {
    public static String convertCodeToStatus(int status) {
        String rs = "";
        switch (status){
            case 0:
                rs = "Đơn hủy";
                break;
            case 1:
                rs = "Đơn chờ xác nhận";
                break;
            case 2:
                rs = "Đơn đã nhận";
                break;
            case 3:
                rs = "Đã thanh toán";
                break;
            default:
                break;
        }
        return rs;
    }

    public static int convertStatusToCode(int code){
        int status = 0;
        switch (code) {
            case 1:
                status = 2;
                break;
            case 2:
                status = 3;
                break;
            default:
                break;
        }
        return status;
    }

    private static final String BASE_URL = "https://fcm.googleapis.com/";

    public static APIService getFCMService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static String getDate(){
//        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
//        calendar.setTimeInMillis(time);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(now);
    }
}
