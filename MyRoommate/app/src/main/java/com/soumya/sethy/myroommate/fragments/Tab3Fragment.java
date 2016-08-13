package com.soumya.sethy.myroommate.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.soumya.sethy.myroommate.Activity.MainActivityApplicationLeftDraw;
import com.soumya.sethy.myroommate.Pojo.DataObject_CardsView;
import com.soumya.sethy.myroommate.Pojo.UserBillDetails;
import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.adapters.MyRecyclerViewAdapter;
import com.soumya.sethy.myroommate.adapters.UsersAdapter;
import com.soumya.sethy.myroommate.db.DbHelper;
import com.soumya.sethy.myroommate.recycleView.Movie;
import com.soumya.sethy.myroommate.recycleView.MoviesAdapter;

import net.colindodd.gradientlayout.GradientFrameLayout;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Tab3Fragment extends Fragment {
    public static Tab2Fragment dialogFragment;
    public static Tab1Fragment dialogFragment1;
    static UsersAdapter adapter;
    static ListView lv;
    static String[] androidColors = {"#55B3C2", "#64A4A4", "#66D7B9", "#FDBC7D", "#3F51B5", "#3949AB", "#EF5350", "#4050B4", "#FF33B5E5", "#FFAA66CC", "#FF99CC00", "#FFFFBB33", "#FFFF4444", "#FF0099CC", "#FF9933CC", "#FF669900", "#FFFF8800", "#FFCC0000"};
    DbHelper dbhelper;
    ArrayList<UserBillDetails> arrayOfUsers;
    UserBillDetails newUser;
    Cursor c;
    Runnable action;
    FloatingActionButton fab, add_roomate;
    PieChart mPieChart;
    BarChart mBarChart;
    SlidingUpPanelLayout mLayout;
    NestedScrollView mScrollView;
    Button btn;
    private RecyclerView mRecyclerView, detailRecycleView;
    private RecyclerView.Adapter mAdapter, mAdapterDetails;
    private RecyclerView.LayoutManager mLayoutManager, mLayoutManagerDetails;
    private ArrayList<String> historyList, historyListDetails;
    ImageView arrow;
    public static String randomColor() {
        int idx = new Random().nextInt(androidColors.length);
        String random = (androidColors[idx]);
        return random;
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (mLayout != null) {
            if (mLayout.getPanelState() == PanelState.HIDDEN) {
                item.setTitle(R.string.action_show);
            } else {
                item.setTitle(R.string.action_hide);
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_toggle: {
                if (mLayout != null) {
                    if (mLayout.getPanelState() != PanelState.HIDDEN) {
                        mLayout.setPanelState(PanelState.HIDDEN);
                        item.setTitle(R.string.action_show);
                    } else {
                        mLayout.setPanelState(PanelState.COLLAPSED);
                        item.setTitle(R.string.action_hide);
                    }
                }
                return true;
            }
            case R.id.action_anchor: {
                if (mLayout != null) {
                    if (mLayout.getAnchorPoint() == 1.0f) {
                        mLayout.setAnchorPoint(0.7f);
                        mLayout.setPanelState(PanelState.ANCHORED);
                        item.setTitle(R.string.action_anchor_disable);
                    } else {
                        mLayout.setAnchorPoint(1.0f);
                        mLayout.setPanelState(PanelState.COLLAPSED);
                        item.setTitle(R.string.action_anchor_enable);
                    }
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }*/

    /////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3fragment, container, false);

        final GradientFrameLayout layout = (GradientFrameLayout) v.findViewById(R.id.root);
        layout.setGradientBackgroundConfig(Color.parseColor("#222222"), Color.parseColor("#222222"), GradientDrawable.Orientation.BL_TR);


        dbhelper = new DbHelper(getActivity());
        historyList = new ArrayList<String>();
        historyListDetails = new ArrayList<String>();
        // Construct the data source
        arrayOfUsers = new ArrayList<UserBillDetails>();
        // Create the adapter to convert the array to views
        adapter = new UsersAdapter(getActivity(), arrayOfUsers);
        // Attach the adapter to a ListView
        lv = (ListView) v.findViewById(R.id.listView1);
        arrow = (ImageView)v.findViewById(R.id.arrow);
        fab = (FloatingActionButton) v.findViewById(R.id.pink_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* FragmentManager fm = getFragmentManager();
                dialogFragment = new Tab2Fragment();
                dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar);
                dialogFragment.show(fm, "Add Expenses");*/
                new Handler().postDelayed(
                        ((MainActivityApplicationLeftDraw) getActivity()).openExpensesRunnable(),
                        200);


            }
        });
        add_roomate = (FloatingActionButton) v.findViewById(R.id.aad_roommate);
        add_roomate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* FragmentManager fm = getFragmentManager();
                dialogFragment1 = new Tab1Fragment();
                dialogFragment1.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar);
                dialogFragment1.show(fm, "Add Roommate");*/
                new Handler().postDelayed(
                        ((MainActivityApplicationLeftDraw) getActivity()).openAddRoommateRunnable(),
                        200);
            }
        });
        mPieChart = (PieChart) v.findViewById(R.id.piechart);
        mBarChart = (BarChart) v.findViewById(R.id.barchart);
        /*Tooltip.make(getActivity(),
                new Tooltip.Builder(101)
                        .anchor(mPieChart, Tooltip.Gravity.BOTTOM)
                        .closePolicy(new Tooltip.ClosePolicy()
                                .insidePolicy(true, false)
                                .outsidePolicy(true, false), 3000)
                        .activateDelay(800)
                        .showDelay(300)
                        .text("Your Expenses Chart!")
                        .maxWidth(500)
                        .withArrow(true)
                        .withOverlay(true)
                        //.typeface(mYourCustomFont)
                        .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                        .build()
        ).show();*/
        updatedData();
        mScrollView = (NestedScrollView) v.findViewById(R.id.sv);
        mScrollView.isSmoothScrollingEnabled();
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //replace this line to scroll up or down
                //mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                mScrollView.smoothScrollTo(0, mPieChart.getTop());
            }
        }, 100L);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);


        /*((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(getActivity(),position,1000).show();
            }
        });*/


        mLayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

              if (newState.toString().equalsIgnoreCase("COLLAPSED")|| newState.toString().equalsIgnoreCase("ANCHORED"))
              {
                  arrow.setBackgroundResource(R.drawable.arrow_up);
              }
              else
              {
                  arrow.setBackgroundResource(R.drawable.arrow_dwn);
              }

            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                arrow.setBackgroundResource(R.drawable.arrow_up);
            }
        });

        TextView t = (TextView) v.findViewById(R.id.name);
        t.setText(Html.fromHtml(getString(R.string.history)));

        updateDetailedList(v);


        return v;
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "Resume Tab4");
        super.onResume();
        mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //replace this line to scroll up or down
                //mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                mScrollView.smoothScrollTo(0, mPieChart.getTop());
            }
        }, 100L);
        //updatedData();
    }

    public void updatedData() {


        adapter.clear();
        newUser = new UserBillDetails("NAME", "AMOUNT");
        adapter.add(newUser);

        Cursor c1 = null;
        try {
            c1 = dbhelper.getExpense();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (c1 != null) {
            c1.moveToFirst();
            if (c1.getCount() != 0) {

                for (int i = 0; i < c1.getCount(); i++) {
                    String Name = (c1.getString(c1
                            .getColumnIndex("_id")));
                    String Amount = (c1.getString(c1
                            .getColumnIndex("AMOUNT")));

                    // Adding values to the ArrayList

                    newUser = new UserBillDetails(Name, Amount);
                    adapter.add(newUser);
                    historyList.add(Name + ":" + Amount);
                    Name = Float.parseFloat(Amount) >= 0 ? "+" + Name :"-" + Name;
                    mPieChart.addPieSlice(new PieModel(Name, Math.abs(Float.parseFloat(Amount)), Color.parseColor(randomColor())));
                    //mBarChart.addBar(new BarModel(Name, Math.abs(Float.parseFloat(Amount)), Color.parseColor(randomColor())));

                    c1.moveToNext();
                }
            }
        }
        // adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        mPieChart.startAnimation();
        //mBarChart.startAnimation();

    }

    private ArrayList<DataObject_CardsView> getDataSet() {
        ArrayList results = new ArrayList<DataObject_CardsView>();
      //  historyList.add("More Details->" + ":0");

        for (int index = 0; index < historyList.size(); index++) {
            String str = historyList.get(index);
            String[] rand_str = str.split(":");
            DataObject_CardsView obj = new DataObject_CardsView(rand_str[0], rand_str[1]);
            results.add(index, obj);
        }

        return results;
    }

    public void updateDetailedList(View v) {
        List<Movie> movieList = new ArrayList<>();
        RecyclerView recyclerView;
        MoviesAdapter mAdapter;
        mAdapter = new MoviesAdapter(movieList);
        recyclerView = (RecyclerView) v.findViewById(R.id.detailRecycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        mAdapter.notifyDataSetChanged();
        Cursor c1 = null;
        try {
            c1 = dbhelper.getAllDetails();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (c1 != null) {
            c1.moveToFirst();
            if (c1.getCount() != 0) {

                for (int i = 0; i < c1.getCount(); i++) {
                    String id = (c1.getString(c1
                            .getColumnIndex("_id")));
                    String item = (c1.getString(c1
                            .getColumnIndex("item")));

                    String mix_amount = (c1.getString(c1
                            .getColumnIndex("mix_amount")));

                    String src_transaction_dt = (c1.getString(c1
                            .getColumnIndex("SRC_TRANSACTION_DT")));
                    historyListDetails.add(id + ":" + item + ":" +  mix_amount + ":"  + src_transaction_dt);
                   /* if(split.equalsIgnoreCase("y"))
                    {*/
                    Movie movie = new Movie(id + "." + item, mix_amount/*"A:10,B:20,C:30"*/, src_transaction_dt);
                    movieList.add(movie);
                    c1.moveToNext();/*}*/
                }
                Movie movie = new Movie("#" + "." + "No More", "0:0,0:0", "N/A");
                movieList.add(movie);
            }
        }
        //Extera setting for handling sliding pannel


        if (mLayout != null) {
            if (mLayout.getAnchorPoint() == 1.0f) {
                mLayout.setAnchorPoint(0.5f);
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                //item.setTitle(R.string.action_anchor_disable);
                //arrow.setBackgroundResource(R.drawable.arrow_dwn);
            } else {
                mLayout.setAnchorPoint(1.0f);
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                //item.setTitle(R.string.action_anchor_enable);
               // arrow.setBackgroundResource(R.drawable.arrow_up);
            }
        }


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager2);
        recyclerView.setAdapter(mAdapter);





    }

    public boolean onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return false;
        }
        return  true;
    }

}
