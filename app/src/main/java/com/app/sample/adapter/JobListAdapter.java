package com.app.sample.adapter;

import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sample.R;
import com.app.sample.fragment.JobListFragment;
import com.app.sample.fragment.SampleFragment;
import com.app.sample.fragment.SampleFragmentManager;
import com.app.sample.helper.CommonValues;
import com.app.sample.model.JobTab;
import com.app.sample.model.MaterialItem;
import com.google.gson.Gson;

import java.util.ArrayList;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.MyViewHolder> implements CommonValues {
    private FragmentActivity mcontext;
    ArrayList<JobTab> items = new ArrayList<>();
    private SampleFragmentManager mFragmentManager;

    public JobListAdapter(FragmentActivity context, ArrayList<JobTab> items) {

        this.mcontext = context;
        this.items = items;
        mFragmentManager = new SampleFragmentManager(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.inflate_job_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final JobTab model = items.get(position);

        holder.mNameTXT.setText(model.getJobName() + (position + 1));

        holder.mDescTXT.setText(model.getJobDesc());

        holder.mMainLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle aBundle = new Bundle();
                aBundle.putString(BUNDLE_KEY, model.getJobID());
                aBundle.putString("SHOW", "SHOW");
                mFragmentManager.updateContent(new SampleFragment(), SampleFragment.TAG, aBundle);

            }
        });

    }


    public void update(ArrayList<JobTab> aList) {
        this.items = aList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTXT, mDescTXT;

        private LinearLayout mMainLAY;

        private MyViewHolder(View itemView) {
            super(itemView);
            mNameTXT = (TextView) itemView.findViewById(R.id.job_title);
            mDescTXT = (TextView) itemView.findViewById(R.id.job_Desc_TXT);
            mMainLAY = (LinearLayout) itemView.findViewById(R.id.item_LAY);


        }
    }


}
