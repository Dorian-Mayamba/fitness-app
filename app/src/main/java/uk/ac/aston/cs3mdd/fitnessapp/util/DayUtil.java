package uk.ac.aston.cs3mdd.fitnessapp.util;

import android.os.Build;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayUtil {

    private static String currentDay;

    private static final Calendar cal = Calendar.getInstance();

    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("EEEE");
    public static String [] getDays(){
        return new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }

    public static String getCurrentDayDisplayName(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            currentDay = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
        }else{
            cal.setTime(new Date());
            currentDay = DATE_FORMATTER.format(cal.getTime());
        }
        return currentDay;
    }

    public static int getDayNum(){
        int currentDayIndex = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            LocalDate today = LocalDate.now();
            currentDayIndex = today.getDayOfWeek().getValue();
        }else{
            cal.setTime(new Date());
            currentDayIndex = cal.get(Calendar.DAY_OF_WEEK);
        }
        return currentDayIndex -1;
    }
}
