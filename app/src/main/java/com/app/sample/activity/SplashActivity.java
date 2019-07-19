package com.app.sample.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.sample.R;
import com.app.sample.helper.CommonValues;
import com.app.sample.helper.DBHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashActivity extends AppCompatActivity implements CommonValues {

    private String mDBFileName;

    private DBInsert mDBLoadTask;

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        createDB();
    }

    private void createDB() {

        mDBFileName = this.getDatabasePath(DATABASE_NAME).toString();
        if (!isDBExist(mDBFileName)) {
            mDBLoadTask = new DBInsert();
            mDBLoadTask.execute();

        } else {
            mDBHelper = new DBHelper(SplashActivity.this);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    GotoMainActivity();
                }
            }, 3000);

        }
    }

    private boolean isDBExist(String aDBFileName) {

        boolean aStatus = false;

        SQLiteDatabase aCheck = null;
        try {

            File file = new File(aDBFileName);
            if (file.exists() && !file.isDirectory()) {
                aCheck = SQLiteDatabase.openDatabase(aDBFileName, null,
                        SQLiteDatabase.OPEN_READONLY
                                | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

                aStatus = (aCheck != null) ? true : false;

                if (aStatus)
                    aCheck.close();
            } else {
                aStatus = false;
            }

        } catch (SQLiteException e) {
            e.printStackTrace();

        }
        return aStatus;
    }

    public void GotoMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class DBInsert extends AsyncTask<Void, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Long doInBackground(Void... params) {

            constructNewFileFromResources(mDBFileName);
            return null;
        }

        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

            Log.d("SPLASH", "Completed" + result);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    GotoMainActivity();
                }
            }, 3000);

        }

    }


    public void constructNewFileFromResources(String DBFile) {

        try {
            String packageName = getApplicationContext().getPackageName();

            String appDatabaseDirectory = String.format(
                    "/data/data/%s/databases", packageName);
            (new File(appDatabaseDirectory)).mkdir();
            OutputStream dos = new FileOutputStream(appDatabaseDirectory + "/"
                    + DATABASE_NAME);

            InputStream dis = getResources().openRawResource(
                    DB_RAW_RESOURCES_ID);
            byte[] buffer = new byte[1028];
            while ((dis.read(buffer)) > 0) {
                dos.write(buffer);
            }
            dos.flush();
            dos.close();
            dis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}