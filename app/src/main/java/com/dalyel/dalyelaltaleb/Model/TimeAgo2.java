package com.dalyel.dalyelaltaleb.Model;


import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo2 {

    public String covertTimeToText( Date pasTime) {

        String convTime = null;

        String prefix = "";
        String suffix = "منذ";

        Date nowTime = new Date();

        long dateDiff = nowTime.getTime() - pasTime.getTime();

        long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
        long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
        long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
        long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

        if (second < 60) {
            convTime = suffix +second+ " ثواني " ;
        } else if (minute < 60) {
            convTime = suffix+minute + " دقيقة ";
        } else if (hour < 24) {
            convTime = suffix+hour + " ساعة ";
        } else if (day >= 7) {
            if (day > 360) {
                convTime = suffix+(day / 360) + " سنة " ;
            } else if (day > 30) {
                convTime =  suffix+(day / 30) + " شهر " ;
            } else {
                convTime = suffix+ (day / 7) + " اسبوع ";
            }
        } else if (day < 7) {
            convTime = suffix+day+" يوم ";
        }

        return convTime;
    }

}