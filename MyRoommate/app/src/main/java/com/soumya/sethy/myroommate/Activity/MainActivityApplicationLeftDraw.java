package com.soumya.sethy.myroommate.Activity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;
import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.fragments.MyAbout;
import com.soumya.sethy.myroommate.fragments.MyHome;
import com.soumya.sethy.myroommate.fragments.MySettings;
import com.soumya.sethy.myroommate.fragments.Tab1Fragment;
import com.soumya.sethy.myroommate.fragments.Tab2Fragment;
import com.soumya.sethy.myroommate.fragments.Tab3Fragment;
import com.soumya.sethy.myroommate.models.NavItem;
import com.soumya.sethy.myroommate.sync.sync;

import java.util.ArrayList;
import java.util.List;

public class MainActivityApplicationLeftDraw extends ActionBarActivity {

    //DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;

    List<NavItem> listNavItems;
    List<Fragment> listFragments;

    ActionBarDrawerToggle actionBarDrawerToggle;
    LeftDrawerLayout mLeftDrawerLayout;
    FragmentManager fm;
    MyMenuFragment mMenuFragment;
    FlowingView mFlowingView;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_application_leftdrawerlayout);
        sync();
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        fm = getSupportFragmentManager();
        mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);

        //Adding Fragment to Main Content Page
        listFragments = new ArrayList<Fragment>();
        listFragments.add(new MyHome());
        listFragments.add(new MySettings());
        listFragments.add(new MyAbout());
        listFragments.add(new Tab2Fragment());
        listFragments.add(new Tab1Fragment());

        // load first fragment as default:
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack("Tab3Fragment")
                .replace(R.id.main_content, listFragments.get(0)).commit();


    }

    public Runnable openDrawerRunnable() {
        return new Runnable() {

            @Override
            public void run() {
                mLeftDrawerLayout.toggle();

            }
        };
    }

    public Runnable openExpensesRunnable() {
        return new Runnable() {

            @Override
            public void run() {
                /*AddExpensesMenuFragment AddExpenses;
				AddExpenses = (AddExpensesMenuFragment) fm.findFragmentById(R.id.id_container_exp);

				if (AddExpenses == null) {
					fm.beginTransaction().add(R.id.id_container_menu, AddExpenses = new AddExpensesMenuFragment()).commit();
				}
				mLeftDrawerLayout.setFluidView(mFlowingView);
				mLeftDrawerLayout.setMenuFragment(mMenuFragment);
				mLeftDrawerLayout.toggle();
			*/    //mLeftDrawerLayout.openDrawer();
                //mLeftDrawerLayout.openDrawer(Gravity.RIGHT);
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("Tab2Fragment")
                        .replace(R.id.main_content, listFragments.get(3)).commit();
            }
        };
    }

    @Override
    public void onBackPressed() {
        int i = getSupportFragmentManager().getBackStackEntryCount();
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        } else if (i > 1) {
            fragmentManager.popBackStack();
        } else {
           /* Tab3Fragment fragment = (Tab3Fragment) getSupportFragmentManager().findFragmentByTag("Tab3Fragment");

            boolean chk =fragment.onBackPressed();
            if (!chk)
            {  return;}
            else {*/
                super.onBackPressed();
                System.exit(0);
            /*}*/
        }
    }

    private void sync() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            new sync(getBaseContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            new sync(getBaseContext()).execute();
    }


    public Runnable BackToHome() {
        return new Runnable() {

            @Override
            public void run() {
                onBackPressed();

            }
        };
    }

    public Runnable openAddRoommateRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("Tab1Fragment")
                        .replace(R.id.main_content, listFragments.get(4)).commit();
            }
        };
    }

}
