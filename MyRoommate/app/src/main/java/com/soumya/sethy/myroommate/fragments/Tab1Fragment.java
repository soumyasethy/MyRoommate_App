package com.soumya.sethy.myroommate.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.nispok.snackbar.Snackbar;
//import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.soumya.sethy.myroommate.Activity.MainActivityApplicationLeftDraw;
import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.Pojo.Person;
import com.soumya.sethy.myroommate.config.config;
import com.soumya.sethy.myroommate.db.DbHelper;

public class Tab1Fragment extends Fragment {
    DbHelper db;
    String phone_num, name;
    MaterialEditText name_et, phn;
    ImageView add;
    boolean res;
    ShimmerTextView textView1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab1fragment, container, false);
        Firebase.setAndroidContext(getActivity());
        db = new DbHelper(getActivity());
        Cursor c = db.getAllUser();


        if (c != null) {
            if (c.moveToFirst()) {
                MyHome.Roommate_Name = "";
                do {
                    String Name_temp = (c.getString(c.getColumnIndex("name")));
                    MyHome.Roommate_Name = Name_temp + "," + MyHome.Roommate_Name;
                } while (c.moveToNext());
            }
        }//mView.dismiss();


        textView1 = (ShimmerTextView)v.findViewById(R.id.textView1);
        Shimmer shimmer = new Shimmer();
        shimmer.start(textView1);

        name_et = (MaterialEditText) v.findViewById(R.id.name);
        phn = (MaterialEditText) v.findViewById(R.id.phn);
        add = (ImageView) v.findViewById(R.id.add_roomate);
        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!name_et.getText().toString().equalsIgnoreCase("") && !phn.getText().toString().equalsIgnoreCase("")) {
                    name = name_et.getText().toString().trim();
                    phone_num = phn.getText().toString().trim();

                    ////////////////Adding//////////
                    //Creating firebase object
                    Firebase ref = new Firebase(config.FIREBASE_URL);

                    //Creating Person object
                    Person person = new Person();

                    //Adding values
                   // person.setName(name);
                    person.setPhone_num(phone_num);
                    //Storing values to firebase
                    ref.child("Person").child(name).setValue(person);

                    res = db.insertUser_table(phone_num, name);
                    if (res) {
                        //Toast.makeText(getActivity(), "Added", 1000).show();
                       // Tab3Fragment.dialogFragment1.dismiss();;
                        Snackbar.with(getActivity()) // context
                                .text("Roommate Added")
                                .textColor(Color.WHITE) // change the text color
                                .textTypeface(Typeface.DEFAULT_BOLD) // change the text font
                                .color(Color.parseColor("#ff69b4")) // change the background color
                                // text to display
                                .show(getActivity()); // activity where it is displayed
                        //MyHome.tabHost.setCurrentTab(1);
                       // MyHome.tabHost.setCurrentTab(0);
                        name_et.setText("");
                        phn.setText("");
                        new Handler().postDelayed(((MainActivityApplicationLeftDraw) getActivity()).BackToHome(),200);


                    }
                }
                else {
                    Snackbar.with(getActivity()) // context
                            .text("Please Enter Properly!")
                            .textColor(Color.WHITE) // change the text color
                            .textTypeface(Typeface.DEFAULT_BOLD) // change the text font
                            .color(Color.parseColor("#ff69b4")) // change the background color
                            // text to display
                            .show(getActivity()); // activity where it is displayed

                }

            }
        });
        //db.insertUser_table(phone_num, name);
        return v;
    }
}
