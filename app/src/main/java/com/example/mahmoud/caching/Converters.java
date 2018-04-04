package com.example.mahmoud.caching;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by mahmoud on 27/03/18.
 */

public class Converters {

    @TypeConverter
    public static Long fromDateToTimeStamp(Date date){
        return  date==null? null:date.getTime();
    }


    @TypeConverter
    public static Date fromTimeToDate(Long time){
        return time==null? null: new Date(time);
    }

}
