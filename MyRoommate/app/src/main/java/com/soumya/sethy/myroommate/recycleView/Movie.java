package com.soumya.sethy.myroommate.recycleView;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Movie {
    private String title, NameSpilt, year;

    public Movie() {
    }

    public Movie(String title,/*String split,*/ String NameSpilt, String year) {
        this.title = title;
        this.NameSpilt =NameSpilt;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getNameSpilt() { return NameSpilt;   }

    public void setNameSpilt(String NameSpilt) {
        this.NameSpilt = NameSpilt;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }




}
