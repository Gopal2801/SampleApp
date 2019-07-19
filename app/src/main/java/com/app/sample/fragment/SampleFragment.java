package com.app.sample.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.app.sample.R;
import com.app.sample.helper.CommonValues;
import com.app.sample.helper.DBHelper;
import com.app.sample.helper.Helper;
import com.app.sample.model.MaterialItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.UUID;

import static com.app.sample.helper.CommonValues.ALERT_FOR_PRE_MATERIAL;

public class SampleFragment extends Fragment implements CommonValues {

    public static final String TAG = SampleFragment.class.getSimpleName().toString();

    private TextView mAddTXT, mSubmitTXT, mAddJobTXT, mTitleTXT;

    private LinearLayout mParentLAY, mBTNLAY;

    private FragmentActivity mContext;

    private ScrollView mScrollView;

    private EditText mDesEDT;

    private DBHelper mDBHelper;

    private SampleFragmentManager mFragmentManager;

    private String mJobID = "";

    ArrayList<MaterialItem> mItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View aView = inflater.inflate(R.layout.fragment_add_view, container, false);
        classWidgets(aView);
        return aView;
    }

    private void classWidgets(View aView) {
        mContext = getActivity();
        mFragmentManager = new SampleFragmentManager(mContext);
        mAddTXT = (TextView) aView.findViewById(R.id.add_material_TXT);
        mParentLAY = (LinearLayout) aView.findViewById(R.id.dynamic_ll);
        mScrollView = (ScrollView) aView.findViewById(R.id.dynamic_sv);
        mSubmitTXT = (TextView) aView.findViewById(R.id.submit_TXT);
        mAddJobTXT = (TextView) aView.findViewById(R.id.add_job_TXT);
        mTitleTXT = (TextView) aView.findViewById(R.id.title);
        mDesEDT = (EditText) aView.findViewById(R.id.desc_EDT);
        mBTNLAY = (LinearLayout) aView.findViewById(R.id.btn_lay);
        mDBHelper = new DBHelper(mContext);
        mItemList = new ArrayList<>();
        getBundle();
        clickListener();

    }

    private void getMaterialItem() {
        mItemList = mDBHelper.getMaterialItem(mJobID);
        if (mItemList.size() > 0) {
            mAddJobTXT.setTextColor(mContext.getResources().getColor(R.color.text_hint));
            mSubmitTXT.setTextColor(mContext.getResources().getColor(R.color.text_hint));
            mAddTXT.setTextColor(mContext.getResources().getColor(R.color.text_hint));
            mDesEDT.setText(mDBHelper.getDesc(mJobID));
            mDesEDT.setEnabled(false);
            mAddTXT.setEnabled(false);
            mSubmitTXT.setEnabled(false);
            showMaterialItem(mItemList);

        } else {
            mAddJobTXT.setTextColor(mContext.getResources().getColor(R.color.black));
            mAddTXT.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            addMaterial();


        }
        //Log.e("JOBID", "" + mItemList.size());

    }

    private void getBundle() {

        Bundle aBundle = getArguments();

        if (aBundle != null) {

            if (aBundle.containsKey(BUNDLE_KEY)) {
                mJobID = aBundle.getString(BUNDLE_KEY);
                //Log.e("JOBID", mJobID);
                getMaterialItem();

            }
            if (aBundle.containsKey("SHOW")) {
                mTitleTXT.setVisibility(View.VISIBLE);
            } else {
                mTitleTXT.setVisibility(View.GONE);
            }
            if (aBundle.containsKey("ADD")) {
                mAddJobTXT.setEnabled(true);
                mAddJobTXT.setTextColor(mContext.getResources().getColor(R.color.black));

            } else {
                mAddJobTXT.setEnabled(false);

            }

        }
    }

    private void clickListener() {
        mAddTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMaterial();
            }
        });

        mSubmitTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getTextValue(mDesEDT).equals("")) {
                    showAlert(ALERT_FOR_DESCP);

                } else {
                    ValidationForField();

                }
            }
        });
        mSubmitTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getTextValue(mDesEDT).equals("")) {
                    showAlert(ALERT_FOR_DESCP);

                } else {
                    ValidationForField();

                }


            }
        });
        mAddJobTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBHelper.insertTab(Helper.createJOBID(), TAB_NAME, "");
                EventBus.getDefault().post("Added");
            }
        });

    }

    public void addMaterial() {
        LayoutInflater aInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout.LayoutParams aLayoutParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        final View aView = aInflater.inflate(R.layout.inflate_add_item, null);
        TextView aHeaderTXT = (TextView) aView.findViewById(R.id.item_material_TXT);
        TextView aItemEDT = (TextView) aView.findViewById(R.id.item_EDT);
        TextView aQtyEDT = (TextView) aView.findViewById(R.id.qty_EDT);
        TextView aCostEDT = (TextView) aView.findViewById(R.id.cost_EDT);
        TextView aTaxEDT = (TextView) aView.findViewById(R.id.tax_EDT);
        TextView aTotalEDT = (TextView) aView.findViewById(R.id.total_EDT);

        aHeaderTXT.setText(mContext.getString(R.string.lbl_material) + " " + (mParentLAY.getChildCount() + 1));

        // Add the new row before the add field button.
        //  mParentLAY.addView(aView, mParentLAY.getChildCount() - 1);

        mParentLAY.addView(aView, aLayoutParam);

        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

    }


    public void showMaterialItem(ArrayList<MaterialItem> aItemList) {

        for (int k = 0; k < aItemList.size(); k++) {
            MaterialItem aItem = aItemList.get(k);

            LayoutInflater aInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout.LayoutParams aLayoutParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            final View aView = aInflater.inflate(R.layout.inflate_add_item, null);
            TextView aHeaderTXT = (TextView) aView.findViewById(R.id.item_material_TXT);
            TextView aItemEDT = (TextView) aView.findViewById(R.id.item_EDT);
            TextView aQtyEDT = (TextView) aView.findViewById(R.id.qty_EDT);
            TextView aCostEDT = (TextView) aView.findViewById(R.id.cost_EDT);
            TextView aTaxEDT = (TextView) aView.findViewById(R.id.tax_EDT);
            TextView aTotalEDT = (TextView) aView.findViewById(R.id.total_EDT);

            aHeaderTXT.setText(mContext.getString(R.string.lbl_material) + " " + (mParentLAY.getChildCount() + 1));

            aItemEDT.setText(aItem.getItemName());
            aQtyEDT.setText(aItem.getItemQty());
            aCostEDT.setText(aItem.getItemCost());
            aTaxEDT.setText(aItem.getItemTax());
            aTotalEDT.setText(aItem.getItemTotal());

            // Add the new row before the add field button.
            //  mParentLAY.addView(aView, mParentLAY.getChildCount() - 1);
            aItemEDT.setEnabled(false);
            aQtyEDT.setEnabled(false);
            aCostEDT.setEnabled(false);
            aTaxEDT.setEnabled(false);
            aTotalEDT.setEnabled(false);

            mParentLAY.addView(aView, aLayoutParam);

        }


    }

    private void ValidationForField() {

        ArrayList<MaterialItem> aMaterialItem = new ArrayList<>();

        int aChildCount = mParentLAY.getChildCount();

        for (int aChild = 0; aChild < aChildCount; aChild++) {
            View childView = mParentLAY.getChildAt(aChild);
            TextView aHeaderTXT = (TextView) childView.findViewById(R.id.item_material_TXT);
            TextView aItemEDT = (TextView) childView.findViewById(R.id.item_EDT);
            TextView aQtyEDT = (TextView) childView.findViewById(R.id.qty_EDT);
            TextView aCostEDT = (TextView) childView.findViewById(R.id.cost_EDT);
            TextView aTaxEDT = (TextView) childView.findViewById(R.id.tax_EDT);
            TextView aTotalEDT = (TextView) childView.findViewById(R.id.total_EDT);
            if (getTextValue(aItemEDT).equals("")) {
                showAlert(ALERT_FOR_PRE_MATERIAL + getTextValue(aHeaderTXT) + ALERT_FOR_ITEM);
                return;
            } else if (getTextValue(aQtyEDT).equals("")) {
                showAlert(ALERT_FOR_PRE_MATERIAL + getTextValue(aHeaderTXT) + ALERT_FOR_QTY);
                return;
            } else if (getTextValue(aCostEDT).equals("")) {
                showAlert(ALERT_FOR_PRE_MATERIAL + getTextValue(aHeaderTXT) + ALERT_FOR_COST);
                return;
            } else if (getTextValue(aTaxEDT).equals("")) {
                showAlert(ALERT_FOR_PRE_MATERIAL + getTextValue(aHeaderTXT) + ALERT_FOR_TAX);
                return;
            } else if (getTextValue(aTotalEDT).equals("")) {
                showAlert(ALERT_FOR_PRE_MATERIAL + getTextValue(aHeaderTXT) + ALERT_FOR_TOTAL);
                return;
            } else {

                MaterialItem aItem = new MaterialItem();
                aItem.setItemID(Helper.createJOBID());
                aItem.setItemDesc(getTextValue(mDesEDT));
                aItem.setItemName(getTextValue(aItemEDT));
                aItem.setItemQty(getTextValue(aQtyEDT));
                aItem.setItemCost(getTextValue(aCostEDT));
                aItem.setItemTax(getTextValue(aTaxEDT));
                aItem.setItemTotal(getTextValue(aTotalEDT));
                aMaterialItem.add(aItem);

            }
        }

        mDBHelper.insertMaterialITem(aMaterialItem, mJobID);

        mDBHelper.updateDesc(mJobID, getTextValue(mDesEDT));

        mFragmentManager.updateContent(new JobListFragment(), JobListFragment.TAG, null);

    }

    private String getTextValue(View aView) {
        if (aView instanceof EditText) {
            return ((EditText) aView).getText().toString().trim();
        } else if (aView instanceof TextView) {
            return ((TextView) aView).getText().toString().trim();
        }
        return "";

    }

    private void showAlert(String aMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mContext.getResources().getString(R.string.app_name));
        builder.setMessage(aMessage);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //   mManager.popBackStack();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }

}
