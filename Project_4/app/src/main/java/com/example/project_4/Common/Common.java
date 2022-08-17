package com.example.project_4.Common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

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
    public static int convertStatusToCode(String status) {
        int rs = 4;
        switch (status){
            case "Đơn hủy":
                rs = 0;
                break;
            case "Đơn chờ xác nhận":
                rs = 1;
                break;
            case "Đơn đã nhận":
                rs = 2;
                break;
            case "Đã thanh toán":
                rs = 3;
                break;
            default:
                break;
        }
        return rs;
    }
    public static Date convertStringToDate(String date){
        date = "";
        Date dateTime = null;
        try {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            dateTime = formatter.parse(date);

        }catch (ParseException e){
            e.printStackTrace();
        }
        return dateTime;
    }
}
