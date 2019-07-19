package com.app.sample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.sample.R;
import com.app.sample.fragment.JobListFragment;
import com.app.sample.fragment.JobTabController;
import com.app.sample.fragment.SampleFragment;
import com.app.sample.fragment.SampleFragmentManager;
import com.app.sample.helper.DBHelper;
import com.app.sample.helper.Helper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SampleFragmentManager mFragmentManager;
    private DBHelper mDBHelper;
    private static long myBack_Pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mFragmentManager = new SampleFragmentManager(MainActivity.this);
        mDBHelper = new DBHelper(MainActivity.this);
        loadDefaultFragment();

    }

    private void loadDefaultFragment() {

        if (mDBHelper.getJobTab().size() == 0)
            mDBHelper.insertTab(Helper.createJOBID(), "Job", "");

        // mFragmentManager.updateContent(new JobTabController(), JobTabController.TAG, null);
        mFragmentManager.clearAllFragments();
        mFragmentManager.updateContent(new JobListFragment(), JobListFragment.TAG, null);

    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackstackCount() > 1) {
            mFragmentManager.backPress();

        } else {
            if (myBack_Pressed + 2000 > System.currentTimeMillis()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);

            } else {
                Toast.makeText(getBaseContext(),
                        "Press once again to exit!", Toast.LENGTH_SHORT)
                        .show();
            }

            myBack_Pressed = System.currentTimeMillis();
        }

    }

}
