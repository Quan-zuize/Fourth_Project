package com.example.project_4.Common;

import com.example.project_4.Remote.APIService;
import com.example.project_4.Remote.RetrofitClient;

public class Common {
    private static final String BASE_URL = "https://fcm.googleapis.com/";

    public static APIService getFCMService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

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
}
