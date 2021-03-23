package com.cos.javagg.champ;


import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

//xml에 데이터 바인딩 하여 값을 원하는대로 변경
public class Calcu {
    private static final String TAG = "cal";

    // 골드입력
    public static String getGold(long gold){
        String goldK = String.format("%.1fK", ((double)gold/1000.0));
        return goldK;
    }

    // CS 입력
    public static String getCS(long cs, long duration){
        String csData = ""+cs+"("+(cs/(duration/60))+")";
        return csData;
    }

    // 평점 계산
    public static String getGrade (long kill, long death, long assist) {

        if(death == 0){
            return "Perfect 평점";
        }

        double killDouble = (double) kill;
        double deathDouble = (double) death;
        double assistDouble = (double) assist;

        double grade = (killDouble + assistDouble) / deathDouble;

        return String.format("%.2f : 1 평점", grade);
    }

    // 숫자 시간을 분과 초로 변경
    public static String getDuration(long duration) {

        long minutes = duration / 60;
        long seconds = duration % 60;

        return String.format("%02d:%02d",minutes,seconds);
    }


    // 타임스탬프 시간을 날짜로 변경
    public static String getDate(Timestamp timestamp) {
        if(timestamp == null){
            return "9999.12.31";
        }

        long ts = timestamp.getTime();

        if(ts + 86400000 > System.currentTimeMillis()){
            long temp = System.currentTimeMillis() - ts;
            if(temp < 60000){
                return temp/1000+"초 전";
            } else if(temp < 3600000){
                return temp/60000+"분 전";
            } else{
                return temp/3600000+"시간 전";
            }
        }

        Date date = new Date(ts);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String dateString = sdf.format(date);

        return dateString;
    }

    // 롱 시간을 날짜로 변경
    public static String getCreation(long creation) {

        if(creation + 86400000 > System.currentTimeMillis()){
            long temp = System.currentTimeMillis() - creation;
            if(temp < 60000){
                return temp/1000+"초 전";
            } else if(temp < 3600000){
                return temp/60000+"분 전";
            } else{
                return temp/3600000+"시간 전";
            }
        }

        Date date = new Date(creation);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String creationString = sdf.format(date);

        return creationString;
    }

}
