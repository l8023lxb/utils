package com.lxb.android.demo.activity.testpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lxb.android.demo.R;
import com.lxb.android.smartutils.SystemProperties;

public class SystemPropertiesTestActivity extends AppCompatActivity {

    EditText mEtProperName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_properties_test);
        mEtProperName = (EditText) findViewById(R.id.proper_et);
    }

    public void getProper(View view) {
        Toast.makeText(this,
                SystemProperties.get(mEtProperName.getText().toString(), "没取到，我是默认值!"),
                Toast.LENGTH_SHORT).show();

    }

}
