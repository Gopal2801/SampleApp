package com.app.sample.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sample.R;
import com.app.sample.adapter.JobListAdapter;
import com.app.sample.helper.CommonValues;
import com.app.sample.helper.DBHelper;
import com.app.sample.model.JobTab;
import com.app.sample.model.MaterialItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.UUID;

public class JobListFragment extends Fragment implements CommonValues {

    public static final String TAG = JobListFragment.class.getSimpleName().toString();

    private FragmentActivity mContext;

    private RecyclerView mJobRC;

    private DBHelper mDBHelper;

    private JobListAdapter mAdapter = null;

    private FloatingActionButton mAddBTN;

    private SampleFragmentManager mFragmentManager;

    private TextView mNoRecordTXT;


    private ArrayList<JobTab> mJobList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View aView = inflater.inflate(R.layout.fragment_job_list, container, false);
        classWidgets(aView);
        return aView;
    }

    private void classWidgets(View aView) {
        mContext = getActivity();
        mFragmentManager = new SampleFragmentManager(mContext);
        mJobRC = (RecyclerView) aView.findViewById(R.id.job_RC);
        mAddBTN = (FloatingActionButton) aView.findViewById(R.id.add_job_BTN);
        mNoRecordTXT = (TextView) aView.findViewById(R.id.no_record_TXT);

        mJobList = new ArrayList<>();
        mDBHelper = new DBHelper(mContext);
        clickListener();
        toLoadValue();
    }

    private void clickListener() {
        mAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentManager.updateContent(new JobTabController(), JobTabController.TAG, null);

            }
        });
    }

    private void toLoadValue() {

        mJobList = mDBHelper.getJobTab();
        if (mJobList.size() > 0) {
            mNoRecordTXT.setVisibility(View.GONE);
            mAdapter = new JobListAdapter(mContext, mJobList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            mJobRC.setHasFixedSize(true);
            mJobRC.setLayoutManager(mLayoutManager);
            mJobRC.setAdapter(mAdapter);

        } else {
            mJobRC.setVisibility(View.GONE);
            mNoRecordTXT.setVisibility(View.VISIBLE);
        }

    }


}
