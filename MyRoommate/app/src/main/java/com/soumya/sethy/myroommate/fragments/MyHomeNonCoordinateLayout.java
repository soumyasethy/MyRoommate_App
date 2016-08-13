package com.soumya.sethy.myroommate.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.adapters.MyFragmentPagerAdapter;
import com.soumya.sethy.myroommate.Pojo.UserBillDetails;
import com.soumya.sethy.myroommate.db.DbHelper;

import java.util.List;
import java.util.Vector;

public class MyHomeNonCoordinateLayout extends Fragment implements OnTabChangeListener,
        OnPageChangeListener {
    public static String Roommate_Name = "";
    public static TabHost tabHost;
    ViewPager viewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    int i = 0;
    View v;
    DbHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tabs_viewpager_layout_coodinate, container, false);

       /* final Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Soumya");

        final ImageView imageView = (ImageView) v.findViewById(R.id.backdrop);
      */  //Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
        db = new DbHelper(getActivity());
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
        this.initializeTabHost(savedInstanceState);

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
        List<Fragment> fragments = new Vector<Fragment>();

       /* fragments.add(new Tab1Fragment());
        fragments.add(new Tab2Fragment());*/
        fragments.add(new Tab3Fragment());
        fragments.add(new Tab4Fragment());

        this.myViewPagerAdapter = new MyFragmentPagerAdapter(
                getChildFragmentManager(), fragments);
        this.viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        this.viewPager.setCurrentItem(0, true);//setting default to Add Expense Tab
        tabHost.setCurrentTab(0);//setting default to Add Expense Tab
        this.viewPager.setOnPageChangeListener(this);

    }

    private void initializeTabHost(Bundle args) {

        tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();

        //for (int i = 1; i <= 3; i++) {

       /* TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("Add Roommate");
        tabSpec.setIndicator("Add Roommate");
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);*/

        /*TabHost.TabSpec tabSpec2;
        tabSpec2 = tabHost.newTabSpec("Add Expenses");
        tabSpec2.setIndicator("Add Expenses");
        tabSpec2.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec2);*/

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
    }

    @Override
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
        tabHost.setCurrentTab(position);
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
}
