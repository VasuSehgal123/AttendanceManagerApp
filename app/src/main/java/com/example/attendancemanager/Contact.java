package com.example.attendancemanager;

import java.util.ArrayList;

public class Contact {
    private String mName,total,absent,present,percentage;


    public Contact(String name,int tot,int pre) {
        mName = name;
        total=String.valueOf(tot);
        absent=String.valueOf(tot-pre);
        present=String.valueOf(pre);
        percentage=String.format("%.2f",(double)pre/tot*100)+"%";
    }

    public String getName() {
        return mName;
    }


    public String getTotal() {
        return total;
    }

    public String getAbsent() {
        return absent;
    }

    public String getPresent() {
        return present;
    }

    public String getPercentage() {
        return percentage;
    }
}
