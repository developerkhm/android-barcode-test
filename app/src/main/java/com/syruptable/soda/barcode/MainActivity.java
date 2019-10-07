package com.syruptable.soda.barcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        findViewById(R.id.bt_input).setOnClickListener(this);
        findViewById(R.id.bt_info).setOnClickListener(this);
        findViewById(R.id.bt_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_input:
                mode = CommonCode.MODE_INPUT;
                break;
            case R.id.bt_info:
                mode = CommonCode.MODE_INFO;
                break;
            case R.id.bt_delete:
                mode = CommonCode.MODE_DELETE;
                break;
        }

        Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
        intent.putExtra(CommonCode.EXTRA_MODE, mode);
        startActivity(intent);
    }


    private void finishApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            ActivityCompat.finishAffinity(this);
        }
        System.exit(0);
    }
}
