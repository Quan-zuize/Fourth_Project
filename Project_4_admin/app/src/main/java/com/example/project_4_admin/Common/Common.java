package com.example.project_4_admin.Common;

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
            case 2:
                status = 2;
                break;
            case 3:
                status = 3;
                break;
            default:
                break;
        }
        return status;
    }
}
