package com.soumya.sethy.myroommate.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TabHost.TabContentFactory;

import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.soumya.sethy.myroommate.Activity.MainActivityApplicationLeftDraw;
import com.soumya.sethy.myroommate.Pojo.UserBillDetails;
import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.adapters.MyFragmentPagerAdapter;
import com.soumya.sethy.myroommate.db.DbHelper;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.List;
import java.util.Vector;

import it.sephiroth.android.library.tooltip.Tooltip;

public class MyHome extends Fragment /*implements OnTabChangeListener,*/
        implements OnPageChangeListener {
    public static String Roommate_Name = "";
    // public static TabHost tabHost;
    ViewPager viewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    int i = 0;
    View v;
    DbHelper db;
    BarChart mBarChart;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tabs_viewpager_layout, container, false);
        db = new DbHelper(getActivity());
        final Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(
                        ((MainActivityApplicationLeftDraw) getActivity()).openDrawerRunnable(),
                        200);


            }
        });

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Expense");

        //final ImageView imageView = (ImageView) v.findViewById(R.id.backdrop);
        mBarChart = (BarChart) v.findViewById(R.id.barchart);
        updatedBarChartData();
        //Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);

        Cursor c = db.getAllUser();


        if (c != null) {
            if (c.moveToFirst()) {
                Roommate_Name = "";
                do {
                    String Name_temp = (c.getString(c.getColumnIndex("name")));
                    Roommate_Name = Name_temp + "," + Roommate_Name;
                } while (c.moveToNext());
            }
        }

        // We put TabHostView Pager here:
        /*
         * part1*****************************************************************
		 * ********  DONE! !!!!! :))
		 */

        i++;

        // init tabhost
        // this.initializeTabHost(savedInstanceState);


        // init ViewPager
        this.initializeViewPager();



		/*
         * part1*****************************************************************
		 * ********
		 */
        return v;
    }

    // fake content for tabhost
    class FakeContent implements TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    private void initializeViewPager() {

        /*Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseListFragment(), "Category 2");
        adapter.addFragment(new CheeseListFragment(), "Category 3");*/

        List<Fragment> fragments = new Vector<Fragment>();

       /* fragments.add(new Tab1Fragment());
        fragments.add(new Tab2Fragment());*/
        fragments.add(new Tab3Fragment());
        fragments.add(new Tab4Fragment());

        this.myViewPagerAdapter = new MyFragmentPagerAdapter(
                getChildFragmentManager(), fragments);
        this.viewPager = (ViewPager) v.findViewById(R.id.tab_viewpager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        //this.viewPager.setPageTransformer(true, new RotateUpTransformer());
        this.viewPager.setCurrentItem(0, true);//setting default to Add Expense Tab
        //tabHost.setCurrentTab(0);//setting default to Add Expense Tab
        this.viewPager.setOnPageChangeListener(this);
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Detailed List"));
        tabLayout.addTab(tabLayout.newTab().setText("Simple Division"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
     /*      tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

            }
        });*/

        //Setting tabs from adpater
        // tabLayout.setTabsFromPagerAdapter(this.myViewPagerAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        this.viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                //replace this line to scroll up or down
                //mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                viewPager.setCurrentItem(1, true);
            }
        }, 2000L);
    }

   /* private void initializeTabHost(Bundle args) {

        tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();

        //for (int i = 1; i <= 3; i++) {

       *//* TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("Add Roommate");
        tabSpec.setIndicator("Add Roommate");
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);*//*

        *//*TabHost.TabSpec tabSpec2;
        tabSpec2 = tabHost.newTabSpec("Add Expenses");
        tabSpec2.setIndicator("Add Expenses");
        tabSpec2.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec2);*//*

        TabHost.TabSpec tabSpec3;
        tabSpec3 = tabHost.newTabSpec("Show Expenses");
        tabSpec3.setIndicator("Show Expenses");
        tabSpec3.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec3);

        TabHost.TabSpec tabSpec4;
        tabSpec4 = tabHost.newTabSpec("Simple Division");
        tabSpec4.setIndicator("Simple Division");
        tabSpec4.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec4);
        //}
        tabHost.setOnTabChangedListener(this);
    }*/

    /* @Override
     public void onTabChanged(String tabId) {
         int pos = tabHost.getCurrentTab();
         this.viewPager.setCurrentItem(pos);

         HorizontalScrollView hScrollView = (HorizontalScrollView) v
                 .findViewById(R.id.hScrollView);
         View tabView = tabHost.getCurrentTabView();
         int scrollPos = tabView.getLeft()
                 - (hScrollView.getWidth() - tabView.getWidth()) / 2;
         hScrollView.smoothScrollTo(scrollPos, 0);

     }
 */
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
       /* if (position >= 4) {
            position = 1;
            viewPager.setCurrentItem(position, true);
        }*/
        tabLayout.setScrollPosition(position, 0, true);
        if (position == 0) {
            Cursor c = db.getAllUser();


            if (c != null) {
                if (c.moveToFirst()) {
                    Roommate_Name = "";
                    do {
                        String Name_temp = (c.getString(c.getColumnIndex("name")));
                        Roommate_Name = Name_temp + "," + Roommate_Name;
                    } while (c.moveToNext());
                }
            }//mView.dismiss();

        }
        if (position == 1) {
            Tab3Fragment.adapter.clear();
            UserBillDetails newUser = new UserBillDetails("NAME", "AMOUNT");
            Tab3Fragment.adapter.add(newUser);

            Cursor c1 = null;
            try {
                c1 = db.getExpense();
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
                        Tab3Fragment.adapter.add(newUser);

                        c1.moveToNext();
                    }
                }
            }

            // adapter.notifyDataSetChanged();
            Tab3Fragment.lv.setAdapter(Tab3Fragment.adapter);
        }


    }

    public void updatedBarChartData() {

        Cursor c1 = null;
        try {
            c1 = db.getExpense();
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


                    Name = Float.parseFloat(Amount) >= 0 ? "+" + Name :"-" + Name;
                    mBarChart.addBar(new BarModel(Name, Math.abs(Float.parseFloat(Amount)), Color.parseColor(Tab3Fragment.randomColor())));

                    c1.moveToNext();
                }
            }
        }


        mBarChart.startAnimation();
        Tooltip.make(getActivity(),
                new Tooltip.Builder(101)
                        .anchor(mBarChart, Tooltip.Gravity.BOTTOM)
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
        ).show();


    }

}
