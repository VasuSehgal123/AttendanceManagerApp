package com.example.attendancemanager;

public class CardItems {
    private String present,total,absent;
    int abs;
    String subject,percentage;



    public CardItems(int pres, int tot,  String sub) {
        present = String.valueOf(pres);
       total = String.valueOf(tot);

        subject = sub;
         abs=tot-pres;
         absent=String.valueOf(abs);

    }


    public String getPresent() {
        return present;
    }

    public String getTotal() {
        return total;
    }

    public String getAbsent() {
        absent=total; return absent;
    }

    public String getPercentage() {
        percentage="%";
        return percentage;
    }

    public String getSubject() {
        return subject;
    }
}
