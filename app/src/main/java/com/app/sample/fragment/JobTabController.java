package com.app.sample.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.sample.R;
import com.app.sample.activity.MainActivity;
import com.app.sample.adapter.JobListAdapter;
import com.app.sample.helper.CommonValues;
import com.app.sample.helper.DBHelper;
import com.app.sample.model.JobTab;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class JobTabController extends Fragment implements CommonValues {

    public static final String TAG = JobTabController.class.getSimpleName().toString();

    private FragmentActivity mContext;

    private DBHelper mDBHelper;

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private ViewPagerAdapter mAdapter;

    private int mTabSize = 0;

    private ArrayList<JobTab> mJobTabList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View aView = inflater.inflate(R.layout.fragment_tab_controller, container, false);
        classWidgets(aView);
        return aView;
    }

    private void classWidgets(View aView) {
        mContext = getActivity();
        mJobTabList = new ArrayList<>();
        mDBHelper = new DBHelper(mContext);
        mViewPager = (ViewPager) aView.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout) aView.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mJobTabList = mDBHelper.getJobTabController();
        mTabSize = mJobTabList.size();
        if (mTabSize > 0) {
            for (int a = 0; a < mTabSize; a++) {
                JobTab aTab = mJobTabList.get(a);
                mAdapter.addFragment(new SampleFragment(), TAB_NAME + (a + 1), aTab.getJobID());
            }
        }
        viewPager.setAdapter(mAdapter);
      /*  ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new SampleFragment(), "ONE");
        adapter.addFragment(new SampleFragment(), "TWO");
        adapter.addFragment(new SampleFragment(), "THREE");
        viewPager.setAdapter(adapter);*/
    }

    public void setTab() {

        mTabSize = mDBHelper.getJobTabController().size();
        mJobTabList = mDBHelper.getJobTabController();

        if (mTabSize > 0) {

            JobTab aTab = mJobTabList.get(mTabSize - 1);

            mAdapter.addFragment(new SampleFragment(), "Job " + (mTabSize), aTab.getJobID());

        }
        mViewPager.setAdapter(mAdapter);

    }

    public void getTagValue() {
        int aTab = mTabLayout.getSelectedTabPosition();
        Log.e("VALUE", mTabLayout.getTabAt(aTab).getTag().toString());

    }

    public
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, String aID) {
            Bundle aBundle = new Bundle();
            aBundle.putString(BUNDLE_KEY, aID);
            aBundle.putString("ADD", "ENABLE");
            fragment.setArguments(aBundle);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String aEvent) {
        if (aEvent.equals("Added")) {
            setTab();
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mTabSize);
                        }
                    }, 100);
        }


    }


}