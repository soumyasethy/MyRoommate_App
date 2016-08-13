package com.soumya.sethy.myroommate.adapters;

import java.io.Serializable;

/**
 * Created by soumya on 7/6/2016.
 */

public class PersonAutoText implements Serializable {
    private String name;
    private String email;
    private String[] val;

    public PersonAutoText(String[] n/*, String e*/) {
        val = n;
        /*for (int i = 0; i < n.length ; i++) {
            name = n[i]; //email = e;
        }*/

         }
    public PersonAutoText(String n/*, String e*/) {
        name = n; //email = e;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() { return name; }
}