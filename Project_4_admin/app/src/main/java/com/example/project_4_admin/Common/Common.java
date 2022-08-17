package com.example.project_4_admin.Common;


import com.example.project_4_admin.Remote.APIService;
import com.example.project_4_admin.Remote.RetrofitClient;

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
}
