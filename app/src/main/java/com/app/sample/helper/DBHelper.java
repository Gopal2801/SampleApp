package com.app.sample.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.sample.model.JobTab;
import com.app.sample.model.MaterialItem;

import java.util.ArrayList;

public class DBHelper implements CommonValues {

    private String TAG = DBHelper.class.getSimpleName();

    private DBHelper.DataBaseHelper myDBHelper;

    private SQLiteDatabase myDataBase;

    private Context myContext;

    private int DATABASE_VERSION = 1;


    public class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

            } catch (SQLException e) {
                e.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public DBHelper(Context context) {
        myContext = context;
        myDBHelper = new DataBaseHelper(context);
        myDataBase = myDBHelper.getReadableDatabase();
        open();
    }


    private void open() {

        try {
            if (myDataBase == null) {

                myDataBase = myDBHelper.getWritableDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void close() {
        try {
            Log.d(TAG, "mySQLiteDatabase Closed");
            // ---Closing the database---
            myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTab(String aJobID, String aJobName, String aJobDescp) {
        TAG = "insertTab";
        //   String aID = "\'" + aJobID + "\'";
        SQLiteDatabase db = null;
        try {

            db = myDBHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(JOB_ID, aJobID);

            values.put(JOB_NAME, aJobName);

            values.put(JOB_DESC, aJobDescp);

            db.insert(TABLE_JOB, null, values);


        } catch (Exception e) {
            Log.e(TAG, ", Exception Occurs " + e);
        } finally {
            try {
                db.close();
            } catch (Exception e) {
                Log.e(TAG, ", Exception Occurs " + e);
            }
        }
    }

    public ArrayList<JobTab> getJobTab() {

        ArrayList<JobTab> aJobList = new ArrayList<>();

        try {


            String aQuery = "SELECT * FROM " + TABLE_JOB;

            Log.e("query", aQuery);

            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            Cursor aCursor = db.rawQuery(aQuery, null);

            aCursor.moveToFirst();

            if (aCursor.getCount() > 0) {

                if (aCursor.moveToFirst()) {

                    do {

                        JobTab aItem = new JobTab();


                        aItem.setJobID(aCursor.getString(aCursor
                                .getColumnIndex(JOB_ID)));

                        aItem.setJobName(aCursor.getString(aCursor
                                .getColumnIndex(JOB_NAME)));
                        aItem.setJobDesc(aCursor.getString(aCursor
                                .getColumnIndex(JOB_DESC)));

                        if (getCount(aCursor.getString(aCursor
                                .getColumnIndex(JOB_ID))) == 0) {

                        } else {
                            aJobList.add(aItem);

                        }

                    } while (aCursor.moveToNext());
                } else {
                    Log.d(TAG, " no MaterialItem ");
                }

                if (aCursor != null) {
                    aCursor.close();
                }
                try {
                    if (db != null) {
                        db.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, ", Exception Occurs " + e);
                }
            }
        } catch (Exception e) {


            Log.e(TAG, ", Exception Occurs " + e);
        }
        return aJobList;
    }

    public ArrayList<JobTab> getJobTabController() {

        ArrayList<JobTab> aJobList = new ArrayList<>();

        try {


            String aQuery = "SELECT * FROM " + TABLE_JOB;

            Log.e("query", aQuery);

            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            Cursor aCursor = db.rawQuery(aQuery, null);

            aCursor.moveToFirst();

            if (aCursor.getCount() > 0) {

                if (aCursor.moveToFirst()) {

                    do {

                        JobTab aItem = new JobTab();


                        aItem.setJobID(aCursor.getString(aCursor
                                .getColumnIndex(JOB_ID)));

                        aItem.setJobName(aCursor.getString(aCursor
                                .getColumnIndex(JOB_NAME)));
                        aItem.setJobDesc(aCursor.getString(aCursor
                                .getColumnIndex(JOB_DESC)));

                        aJobList.add(aItem);


                    } while (aCursor.moveToNext());
                } else {
                    Log.d(TAG, " no MaterialItem ");
                }

                if (aCursor != null) {
                    aCursor.close();
                }
                try {
                    if (db != null) {
                        db.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, ", Exception Occurs " + e);
                }
            }
        } catch (Exception e) {


            Log.e(TAG, ", Exception Occurs " + e);
        }
        return aJobList;
    }

    public void insertMaterialITem(ArrayList<MaterialItem> aMaterial, String aJobID) {
        TAG = "MaterialItem";
        SQLiteDatabase db = null;
        try {

            db = myDBHelper.getWritableDatabase();

            for (int i = 0; i < aMaterial.size(); i++) {

                MaterialItem aItem = aMaterial.get(i);

                ContentValues values = new ContentValues();

                db = myDBHelper.getWritableDatabase();

                values.put(MATERIAL_NAME, aItem.getItemName());

                values.put(MATERIAL_QTY, aItem.getItemQty());

                values.put(MATERIAL_COST, aItem.getItemCost());

                values.put(MATERIAL_TAX, aItem.getItemTax());

                values.put(MATERIAL_TOTAL, aItem.getItemTotal());

                values.put(JOB_ID, aJobID);

                values.put(MATERIAL_ID, aItem.getItemID());

                db.insert(TABLE_MATERIAL_ITEM, null, values);

            }


        } catch (Exception e) {
            Log.e(TAG, ", Exception Occurs " + e);
        } finally {
            try {
                db.close();
            } catch (Exception e) {
                Log.e(TAG, ", Exception Occurs " + e);
            }
        }
    }


    public ArrayList<MaterialItem> getMaterialItem(String aJobID) {

        ArrayList<MaterialItem> aItemList = new ArrayList<>();

        try {


            String aQuery = "SELECT * FROM " + TABLE_MATERIAL_ITEM + " WHERE " + JOB_ID + "=" + "\'" + aJobID + "\'";

            Log.e("query", aQuery);

            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            Cursor aCursor = db.rawQuery(aQuery, null);

            aCursor.moveToFirst();

            if (aCursor.getCount() > 0) {

                if (aCursor.moveToFirst()) {

                    do {

                        MaterialItem aItem = new MaterialItem();

                        aItem.setItemName(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_NAME)));
                        aItem.setItemQty(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_QTY)));
                        aItem.setItemCost(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_COST)));
                        aItem.setItemTax(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_TAX)));
                        aItem.setItemTotal(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_TOTAL)));
                        aItemList.add(aItem);

                    } while (aCursor.moveToNext());
                } else {
                    Log.d(TAG, " no MaterialItem ");
                }

                if (aCursor != null) {
                    aCursor.close();
                }
                try {
                    if (db != null) {
                        db.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, ", Exception Occurs " + e);
                }
            }
        } catch (Exception e) {


            Log.e(TAG, ", Exception Occurs " + e);
        }
        return aItemList;
    }

    public ArrayList<MaterialItem> getMaterialItem() {

        ArrayList<MaterialItem> aItemList = new ArrayList<>();

        try {


            String aQuery = "SELECT * FROM " + TABLE_MATERIAL_ITEM;

            Log.e("query", aQuery);

            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            Cursor aCursor = db.rawQuery(aQuery, null);

            aCursor.moveToFirst();

            if (aCursor.getCount() > 0) {

                if (aCursor.moveToFirst()) {

                    do {

                        MaterialItem aItem = new MaterialItem();

                        aItem.setItemName(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_NAME)));
                        aItem.setItemQty(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_QTY)));
                        aItem.setItemCost(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_COST)));
                        aItem.setItemTax(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_TAX)));
                        aItem.setItemTotal(aCursor.getString(aCursor
                                .getColumnIndex(MATERIAL_TOTAL)));
                        aItemList.add(aItem);

                    } while (aCursor.moveToNext());
                } else {
                    Log.d(TAG, " no MaterialItem ");
                }

                if (aCursor != null) {
                    aCursor.close();
                }
                try {
                    if (db != null) {
                        db.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, ", Exception Occurs " + e);
                }
            }
        } catch (Exception e) {


            Log.e(TAG, ", Exception Occurs " + e);
        }
        return aItemList;
    }


    public int getCount(String aJobID) {
        String aQuery = "SELECT * FROM " + TABLE_MATERIAL_ITEM + " WHERE " + JOB_ID + "=" + "\'" + aJobID + "\'";
        Cursor cursor = myDataBase.rawQuery(aQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void updateDesc(String aID, String aDesc) {

        try {
            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            String aQueryUpdate = "UPDATE Job SET JobDescription = " + "\'" + aDesc + "\'"
                    + " WHERE JobID = " + "\'" + aID + "\'";

            db.execSQL(aQueryUpdate);

            try {
                db.close();
            } catch (Exception e) {
                Log.e(TAG, ", Exception Occurs " + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDesc(String aJobID) {

        TAG = "getDesc";

        String aDescp = "";

        try {

            String aQuery = "Select JobDescription from Job where JobID=" + "\'" + aJobID + "\'";

            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            Cursor aCursor = db.rawQuery(aQuery, null);

            if (aCursor.getCount() > 0) {

                if (aCursor.moveToFirst()) {

                    do {

                        aDescp = aCursor.getString(aCursor
                                .getColumnIndex("JobDescription"));


                    } while (aCursor.moveToNext());
                }
            } else {
            }

            if (aCursor != null) {
                aCursor.close();
            }
            try {
                if (db != null) {
                    db.close();

                }
            } catch (Exception e) {
            }
        } catch (Exception e) {


        }

        return aDescp;
    }
}