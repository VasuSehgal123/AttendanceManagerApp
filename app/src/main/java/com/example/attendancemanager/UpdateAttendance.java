package com.example.attendancemanager;

import java.util.HashMap;
import java.util.Map;

public class UpdateAttendance {
public  Map<String,Integer> list=new HashMap<>();

    public UpdateAttendance()
    {

    }
    public UpdateAttendance(Map<String,Integer> list)
    {
        this.list=list;
    }

}
