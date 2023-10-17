package com.example.ttcn2etest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyUtils {
    public static Date convertDateFromString(String dateStr, String dateTimeFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(dateStr);
    }

    public static Date validateDateFromString(String dateStr, String dateTimeFormat) throws ParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
            simpleDateFormat.setLenient(false);
            return simpleDateFormat.parse(dateStr);
        }catch (ParseException e){
            return null;
        }
    }

    public static String convertDateToString(Date inputDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(inputDate);
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(index));
        }
        return randomString.toString();
    }
}
