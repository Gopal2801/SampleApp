package com.app.sample.fragment;

import android.os.Bundle;

import android.util.Log;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.sample.R;


public class SampleFragmentManager {

    private FragmentActivity myContext;

    /**
     * Last fragment tag
     */
    public static String myLastTag = "";

    /**
     * Constructor to Initiate fragment manager
     *
     * @param aContext
     */
    public SampleFragmentManager(FragmentActivity aContext) {
        myContext = aContext;
    }

    /**
     * Update the Current Fragment by passing the below parameters
     *
     * @param aFragment
     * @param tag
     * @param aBundle
     */
    public void updateContent(Fragment aFragment, String tag, Bundle aBundle) {

        Boolean aCheck = false;

        try {

            Log.e("TAG Screen name", tag);
            // Initialise Fragment Manager
            final FragmentManager aFragmentManager = myContext
                    .getSupportFragmentManager();

            // Initialise Fragment Transaction
            final FragmentTransaction aTransaction = aFragmentManager
                    .beginTransaction();

            aFragment.setArguments(aBundle);


            aTransaction.add(R.id.main_container, aFragment, tag);


            // Add the selected fragment

            // add the tag to the backstack
            aTransaction.addToBackStack(tag);

            // Commit the Fragment transaction
            aTransaction.commit();

            myLastTag = tag;

            Log.i("LastTag", myLastTag);

        } catch (Exception aError) {
            aError.printStackTrace();
        }
    }


    public int getBackstackCount() {

        FragmentManager aFragmentManager = myContext
                .getSupportFragmentManager();

        return aFragmentManager.getBackStackEntryCount();
    }


    //Get the Current TAG

    public String getActiveFragmentTAG() {

        if (myContext.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String aCurrentTAG = myContext.getSupportFragmentManager().getBackStackEntryAt(myContext.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();

        return aCurrentTAG;
    }

    public String getActiveFragmentTestTAG() {

        if (myContext.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String aCurrentTAG = myContext.getSupportFragmentManager().getBackStackEntryAt(myContext.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();

        return aCurrentTAG;
    }


    public void removeFragment(int aCount) {

        FragmentManager aFragmentManager = myContext
                .getSupportFragmentManager();

        for (int i = 0; i < aCount; i++) {
            aFragmentManager.popBackStack();

        }
    }

    /**
     * Clear All Fragments
     */
    public void clearAllFragments() {

        try {
            FragmentManager aFragmentManager = myContext
                    .getSupportFragmentManager();

            aFragmentManager.popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public FragmentManager getFragmentManager(FragmentActivity aContext) {


        FragmentManager aFragmentManager = aContext
                .getSupportFragmentManager();

        return aFragmentManager;

    }

    public void backPress() {

        try {

            FragmentManager aFragmentManager = myContext
                    .getSupportFragmentManager();

            if (aFragmentManager.getBackStackEntryCount() > 1) {
                aFragmentManager.popBackStack();
                aFragmentManager.executePendingTransactions();

                Log.d("TAG",
                        "CURRENT FRAGMENT BACK STACK CLASS "
                                + aFragmentManager
                                .getBackStackEntryAt(
                                        aFragmentManager
                                                .getBackStackEntryCount() - 1)
                                .getName());

                Log.e("CURRENTTAGFRAGMENT", getActiveFragmentTAG());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
