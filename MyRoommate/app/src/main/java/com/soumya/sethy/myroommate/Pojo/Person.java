package com.soumya.sethy.myroommate.Pojo;

/**
 * Created by soumya on 6/11/2016.
 */
public class Person {


    private String name;
    private String phone_num;

    public Person() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}
