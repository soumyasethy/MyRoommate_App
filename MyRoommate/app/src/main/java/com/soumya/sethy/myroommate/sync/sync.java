package com.soumya.sethy.myroommate.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.soumya.sethy.myroommate.Pojo.Person;
import com.soumya.sethy.myroommate.config.config;
import com.soumya.sethy.myroommate.db.DbHelper;

/**
 * Created by soumya on 6/13/2016.
 */
public class sync extends AsyncTask<String, String, String> {
    Context context;
    DbHelper db;

    public sync(Context context) {
        this.context = context;
        db = new DbHelper(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint({"SimpleDateFormat", "NewApi"})
    @Override
    protected String doInBackground(String... aurl) {
        Firebase.setAndroidContext(context);
        System.out.println("<<==Inside doinBackground==>>");
        //Value event listener for realtime data update
        Firebase ref_per = new Firebase(config.PERSON_URL);
        ref_per.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // System.out.println(snapshot.getValue());
                System.out.println("There are : " + snapshot.getChildrenCount() + " Roommates");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Person post = postSnapshot.getValue(Person.class);
                    System.out.println(postSnapshot.getKey() + " - " + post.getPhone_num());
                    db.insertUser_table(post.getPhone_num(), postSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        Firebase ref_exp_id = new Firebase(config.EXPENSES_URL);
        ref_exp_id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // System.out.println(snapshot.getValue());
                System.out.println("There are : " + snapshot.getChildrenCount() + " expenses");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    System.out.println(postSnapshot.getKey());
                    final int _id = Integer.parseInt(postSnapshot.getKey());
                    //calling individual call....
                    Firebase ref_exp = new Firebase(config.EXPENSES_URL + postSnapshot.getKey() + "/");
                    ref_exp.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            // System.out.println(snapshot.getValue());
                            System.out.println("There are : " + snapshot.getChildrenCount() + " expenses");
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                String name = postSnapshot.getKey();
                                postSnapshot.getValue();
                                String Paid = postSnapshot.child("Paid").getValue(String.class);
                                String item = postSnapshot.child("item").getValue(String.class);
                                String Item_type = postSnapshot.child("Item_type").getValue(String.class);
                                String src_transaction_dt = postSnapshot.child("src_transaction_dt").getValue(String.class);
                                String status = postSnapshot.child("status").getValue(String.class);
                                try {
                                    db.insertDetail_table(item, Item_type, Integer.parseInt(src_transaction_dt));
                                    db.insertAmount_table(_id, Double.valueOf(Paid),
                                            name, status);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Error in sync -->" + e);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        return null;

    }

    protected void onProgressUpdate(String... progress) {
        System.out.println("synced");
    }

    @Override
    protected void onPostExecute(String unused) {
    }

}