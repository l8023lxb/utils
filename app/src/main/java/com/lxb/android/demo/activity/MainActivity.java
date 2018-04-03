package com.lxb.android.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.lxb.android.demo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mLvContent;
    ArrayList<String> mActivitys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLvContent = findViewById(R.id.main_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("所有程序入口");
        setSupportActionBar(toolbar);
        initData();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    PackageManager pm = getPackageManager();
                    PackageInfo info = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
                    if (info != null && info.activities != null && info.activities.length > 0) {
                        for (int i = 0; i < info.activities.length; i++) {
                            if (info.activities[i] != null
                                    && !TextUtils.isEmpty(info.activities[i].name)
                                    //去掉自己
                                    && !getComponentName().getClassName().equals(info.activities[i].name)) {
                                mActivitys.add(info.activities[i].name);
                            }
                        }
                        if (mActivitys != null && mActivitys.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextAdapter adapter = new TextAdapter(MainActivity.this);
                                    mLvContent.setAdapter(adapter);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class TextAdapter extends BaseAdapter {

        Context mContext;

        public TextAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mActivitys.size();
        }

        @Override
        public Object getItem(int position) {
            return mActivitys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                Button text = new Button(mContext);
                text.setAllCaps(false);
                text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                text.setTextColor(Color.parseColor("#ffff00ff"));
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClassName(getPackageName(), v.getTag().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                convertView = text;
            }
            ((Button) convertView).setText(mActivitys.get(position));
            convertView.setTag(mActivitys.get(position));
            return convertView;
        }
    }

}
