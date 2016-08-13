package com.soumya.sethy.myroommate.fragments;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.firebase.client.Firebase;
import com.mxn.soul.flowingdrawer_core.MenuFragment;
import com.nispok.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.roger.catloadinglibrary.CatLoadingView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.adapters.CheckListAdapter;
import com.soumya.sethy.myroommate.adapters.ContactsCompletionView;
import com.soumya.sethy.myroommate.adapters.CustomListAdapter;
import com.soumya.sethy.myroommate.adapters.PersonAutoText;
import com.soumya.sethy.myroommate.adapters.RoommateName;
import com.soumya.sethy.myroommate.config.config;
import com.soumya.sethy.myroommate.db.DbHelper;
import com.soumya.sethy.myroommate.models.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressLint("SimpleDateFormat")
public class AddExpensesMenuFragment extends MenuFragment implements DatePickerDialogFragment.DatePickerDialogHandler {

    DbHelper db;
    String item, item_type;
    MaterialEditText item_et, item_typ_et, amount;
    Button paid_btn, equal_btn, btn;
    ImageView add, back_btn;
    FlowLayout flow_layout;
    ListView myList;
    Double sum = 0.0;
    int src_transaction_dt;
    int day, month, year;
    CatLoadingView mView;
    TextView date_tv;
    ShimmerTextView textView2;
    boolean changed_date = false;
    Calendar cal;
    String nameSplit;
    MaterialAutoCompleteTextView AutoCompleteTextName;
    ////////////////////////////////
    ContactsCompletionView completionView;
    ArrayAdapter adapter;
    ArrayAdapter<PersonAutoText> personAutoTextArrayAdapter;
    TextView Next;
    ListView lv;
    ////////////////////////////////
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2fragment, container, false);
        Firebase.setAndroidContext(getActivity());
        cal = Calendar.getInstance();
        db = new DbHelper(getActivity());
        lv = (ListView) v.findViewById(R.id.listView);
        //multiAutoCompleteTextName.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        mView = new CatLoadingView();
        //mView.show(getActivity().getSupportFragmentManager(), "");
        textView2 = (ShimmerTextView) v.findViewById(R.id.textView2);

        Shimmer shimmer = new Shimmer();
        shimmer.start(textView2);

        date_tv = (TextView) v.findViewById(R.id.date_tv);
        date_tv.setText(getDateTime());
        date_tv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DatePickerBuilder dpb = new DatePickerBuilder()
                        .setFragmentManager(getChildFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment)
                        .setTargetFragment(AddExpensesMenuFragment.this);
                dpb.show();

            }
        });


        item_et = (MaterialEditText) v.findViewById(R.id.item);
        item_typ_et = (MaterialEditText) v.findViewById(R.id.item_typ);
        amount = (MaterialEditText) v.findViewById(R.id.amount);
        add = (ImageView) v.findViewById(R.id.add_ex);

        equal_btn = (Button) v.findViewById(R.id.equal_btn);
        paid_btn = (Button) v.findViewById(R.id.paid_btn);

        flow_layout = (FlowLayout) v.findViewById(R.id.flow_layout);
        //adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, MyHome.Roommate_Name.split(","));
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, MyHome.Roommate_Name.split(","));
        AutoCompleteTextName = (MaterialAutoCompleteTextView) v.findViewById(R.id.multiAutoCompleteTextName);
        AutoCompleteTextName.setAdapter(adapter);
        //addChildTo(flow_layout, MyHome.Roommate_Name);
        back_btn = (ImageView) v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Tab3Fragment.dialogFragment.dismiss();
            }
        });

        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Firebase ref = new Firebase(config.FIREBASE_URL);
                nameSplit = completionView.getObjects().toString();
                nameSplit = nameSplit.replace(" ", "");
                nameSplit = nameSplit.substring(1, nameSplit.length() - 1);
                // Toast.makeText(getActivity(),nameSplit,1000).show();
                Map<String, Map<String, String>> all_expensesObj = new HashMap<String, Map<String, String>>();

                item = item_et.getText().toString().trim();
                item_type = item_typ_et.getText().toString().trim();

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                /*hh:mm aa */"yyyyMMdd", Locale.getDefault());
                Date today = cal.getInstance().getTime();
                src_transaction_dt = Integer.valueOf(dateFormat.format(today));

                // Getting Separate Payment Details of all user
                String Paid = "", paidName = "";
                try {
                    db.insertDetail_table(item, item_type, src_transaction_dt);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Getting to be spilt details
                /*for (RoommateName p : CheckListAdapter.getBox()) {*/
                for (String Name : MyHome.Roommate_Name.split(",")/*nameSplit.split(",")*/) {
                    String status = "N";
                    /*if (p.box) {
                        status = "Y";
                    } else {
                        status = "N";
                    }*/

                    try {

                        ArrayList list = new ArrayList(Arrays.asList(nameSplit.split(",")));
                        if (list.contains(Name.trim())) {
                            status = "Y";
                        } else {
                            status = "N";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    paidName = /*p.name*/Name;
                    Paid = CustomListAdapter.data.get(paidName);
                    if (Paid == null) {
                        Paid = "0";
                    }
                    int _id = 0;
                    try {
                        _id = db.get_id();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    try {
                        db.insertAmount_table(_id, Double.parseDouble(Paid),
                                paidName, status);
                        ////////////////Adding//////////
                        //Creating firebase object
                        Map<String, String> expensesObj = new HashMap<String, String>();
                        expensesObj.put("item", item);
                        expensesObj.put("item_type", item_type);
                        expensesObj.put("src_transaction_dt", String.valueOf(src_transaction_dt));
                        expensesObj.put("Paid", Paid);
                        //expensesObj.put("paidName",paidName);
                        expensesObj.put("status", status);
                        all_expensesObj.put(paidName, expensesObj);
                        //Storing values to firebase


                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
                int _id = 0;
                try {
                    _id = db.get_id();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                ref.child("Expenses").child(String.valueOf(_id)).setValue(all_expensesObj);
               // Tab3Fragment.dialogFragment.dismiss();
                ;
                //Toast.makeText(getActivity(), "Expenses Added", 1000).show();
                Snackbar.with(getActivity()) // context
                        .text("Expenses Added")
                        .textColor(Color.WHITE) // change the text color
                        .textTypeface(Typeface.DEFAULT_BOLD) // change the text font
                        .color(Color.parseColor("#ff69b4")) // change the background color
                        // text to display
                        .show(getActivity()); // activity where it is displayed
            }
        });
        paid_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //showPopup(v, MyHome.Roommate_Name.split(","));
                addChildTo(flow_layout, MyHome.Roommate_Name + "Multiple People");

            }
        });
        equal_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                showCheckPopup(v, MyHome.Roommate_Name.split(","));

            }
        });
        ArrayList<PersonAutoText> Target = new ArrayList<PersonAutoText>();
        String[] temp_list = MyHome.Roommate_Name.split(",");
        for (int i = 0; i < temp_list.length; i++) {
            Target.add(new PersonAutoText(temp_list[i]));
        }

        //////////////////////////////////////////////////////////////////
        personAutoTextArrayAdapter = new ArrayAdapter<PersonAutoText>
                (getActivity(), android.R.layout.simple_list_item_1, Target);
       /* FilteredArrayAdapter filteredArrayAdapter = new FilteredArrayAdapter<PersonAutoText>(getActivity(), android.R.layout.simple_list_item_1, Target) {
            @Override
            protected boolean keepObject(PersonAutoText obj, String mask) {
                mask = mask.toLowerCase();
                return obj.getName().toLowerCase().startsWith(mask) || obj.getEmail().toLowerCase().startsWith(mask);
            }


        };
*/


        completionView = (ContactsCompletionView) v.findViewById(R.id.searchView);
        completionView.setPrefix("Split with: ");
        //completionView.addObject(new PersonAutoText("Soumya"));
        //completionView.addObject(new PersonAutoText("Milan"));


        completionView.setAdapter(personAutoTextArrayAdapter);
        completionView.setDropDownHeight(0);

        completionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky is clicked
                lv.setVisibility(View.VISIBLE);
                nameSplit = completionView.getObjects().toString();
                nameSplit = nameSplit.substring(1, nameSplit.length() - 1);
                //Toast.makeText(getActivity(),nameSplit,1000).show();
            }
        });

        lv.setAdapter(personAutoTextArrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                String valueAddSearchBar = adapter.getItemAtPosition(position).toString();
                completionView.addObject(new PersonAutoText(valueAddSearchBar));

                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
            }
        });
        /**
         * Enabling Search Filter
         * */
        completionView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                lv.setVisibility(View.VISIBLE);
                personAutoTextArrayAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        Next = (TextView) v.findViewById(R.id.Next);
        Next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky is clicked
                lv.setVisibility(View.GONE);
                item_et.requestFocus();
            }
        });
        /////////////////////////////////////////////////////////////////
        return v;
    }

    private void addChildTo(final FlowLayout flowLayout, String alldata) {
        flowLayout.removeAllViews();
        final String[] temp_list = alldata.split(",");
        for (int j = 0; j < temp_list.length; j++) {

            btn = new Button(getActivity(), null, android.R.attr.buttonStyleSmall);
            btn.setHeight(dp2px(18));
            btn.setTextSize(10);
            btn.setTextColor(Color.BLACK);
            // btn.setTextColor(getResources().getColorStateList(R.color.checkable_text_color));
            // btn.setBackgroundResource(R.drawable.checkable_background);
            btn.setText(temp_list[j]);
            btn.setTag(temp_list[j]);
            flowLayout.addView(btn);

            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // v.setTextColor(Color.WHITE);
                    v.setBackgroundColor(Color.parseColor(Tab3Fragment.randomColor()));
                    // TODO Auto-generated method stub
                    String criteria = v.getTag().toString();

                    paid_btn.setText("Paid By : " + criteria);
                    if (criteria.equalsIgnoreCase("Multiple People")) {
                        showPopup(v, temp_list);
                    } else {
                        try {
                            CustomListAdapter.data.put(criteria,
                                    String.valueOf(amount.getText().toString()));

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                    flowLayout.removeAllViews();

                }
            });

        }
    }

    public int dp2px(int dpValue) {
        return (int) (dpValue * getResources().getDisplayMetrics().density);
    }

    public void showPopup(View anchorView, final String[] temp_list) {

        View popupView = getActivity().getLayoutInflater().inflate(
                R.layout.popup_lvt, null);
        Button Save = (Button) popupView.findViewById(R.id.button1);
        Button Cancel = (Button) popupView.findViewById(R.id.button2);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        ListView myList = (ListView) popupView.findViewById(R.id.listView1);
        //  myList.setItemsCanFocus(true);
        //myList.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        final CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                temp_list);

        myList.setAdapter(adapter);
        myList.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        // If the PopupWindow should be focusable


        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        Cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });
        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under
        // anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0],
                location[1] + anchorView.getHeight());
        popupWindow.setFocusable(true);
        popupWindow.update();
        Save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sum = 0.0;
                // TODO Auto-generated method stub
                for (int i = 0; i < temp_list.length; i++) {
                    String str = CustomListAdapter.data.get(temp_list[i]);
                    // Toast.makeText(getActivity(), str, 1000).show();
                    try {
                        sum += Double.parseDouble(str);
                    } catch (Exception e) {
                        // TODO: handle exception
                        // Toast.makeText(getActivity(), e.toString(),
                        // 1000).show();
                    }
                    amount.setText(String.valueOf(sum));
                    popupWindow.dismiss();
                }
            }
        });
    }

    public void showCheckPopup(View anchorView, final String[] temp_list) {
        ArrayList<RoommateName> roommateName = new ArrayList<RoommateName>();
        final CheckListAdapter boxAdapter = new CheckListAdapter(getActivity(),
                roommateName);
        for (String name : temp_list) {
            roommateName.add(new RoommateName(name, false));
        }
        View popupView = getActivity().getLayoutInflater().inflate(
                R.layout.check_popup, null);
        ImageButton done = (ImageButton) popupView.findViewById(R.id.check_done);
        final PopupWindow chck_popupWindow = new PopupWindow(popupView,
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        ListView lvMain = (ListView) popupView.findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);
        lvMain.setItemsCanFocus(true);
        chck_popupWindow.setFocusable(true);
        chck_popupWindow.setBackgroundDrawable(new ColorDrawable());
        int location[] = new int[2];
        anchorView.getLocationOnScreen(location);
        chck_popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());
        done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*
                 * String result = ""; for (RoommateName p :
				 * boxAdapter.getBox()) { if (p.box) { result += "\n" + p.name;
				 * } Toast.makeText(getActivity(), result, 1000).show(); }
				 */
                chck_popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        date_tv.setText(getString(R.string.date_picker_result_value, year, monthOfYear, dayOfMonth));
        day = dayOfMonth;//dpResult.getDayOfMonth();
        month = monthOfYear;//dpResult.getMonth();
        this.year = year;//dpResult.getYear();
        // create a calendar

        Date date = new Date(year, monthOfYear, dayOfMonth);
        cal.setTime(date);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                /*hh:mm aa */"dd MMM yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
