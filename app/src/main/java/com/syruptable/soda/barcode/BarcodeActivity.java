package com.syruptable.soda.barcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.syruptable.soda.barcode.response.DeleteResponse;
import com.syruptable.soda.barcode.response.InfoResponse;
import com.syruptable.soda.barcode.response.InputResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BarcodeActivity extends AppCompatActivity {

    private int mode;

    private TextView textViewRequest, textViewResponse, textViewBarcode;
    private EditText editText;
    private EditText editTextAppkey;
    private EditText editTextStorekey;
    private EditText editTextStoreNamekey;
    private EditText editTextPrice;
    private EditText editTextProductName;

    private Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        Intent intent = getIntent();
        mode = intent.getIntExtra(CommonCode.EXTRA_MODE, -1);

        init();
    }

    private void init() {
        if (mode < 0) {
            Toast.makeText(this, "잘못된 선택입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        textViewRequest = (TextView) findViewById(R.id.tv_request);
        textViewResponse = (TextView) findViewById(R.id.tv_response);

        if (mode == CommonCode.MODE_INPUT) {

            findViewById(R.id.ll_txid).setVisibility(View.GONE);

            editTextAppkey = (EditText) findViewById(R.id.et_app_key);
            editTextStorekey = (EditText) findViewById(R.id.et_store_key);
            editTextStoreNamekey = (EditText) findViewById(R.id.et_store_name_key);
            editTextPrice = (EditText) findViewById(R.id.et_price);
            editTextProductName = (EditText) findViewById(R.id.et_productName);

            textViewBarcode = (TextView) findViewById(R.id.tv_barcode);
            textViewBarcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //바코드 스캐너 띄우기
                    IntentIntegrator integrator = new IntentIntegrator(BarcodeActivity.this);
                    integrator.setOrientationLocked(false);
                    integrator.initiateScan();
                }
            });

            btnRequest = (Button) findViewById(R.id.bt_request);
            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(editTextAppkey.getText().toString())) {
                        Toast.makeText(BarcodeActivity.this, "App-key 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(editTextStorekey.getText().toString())) {
                        Toast.makeText(BarcodeActivity.this, "Store-Key 입력 해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(editTextStoreNamekey.getText().toString())) {
                        Toast.makeText(BarcodeActivity.this, "StoreName-Key를 입력 해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(editTextPrice.getText().toString())) {
                        Toast.makeText(BarcodeActivity.this, "Price를 입력 해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(editTextProductName.getText().toString())) {
                        Toast.makeText(BarcodeActivity.this, "PriceName 입력 해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(textViewBarcode.getText().toString())) {
                        Toast.makeText(BarcodeActivity.this, "바코드를 스캔 해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    fetchDataInput(textViewBarcode.getText().toString());
                }
            });


        } else {
            findViewById(R.id.ll_barcode).setVisibility(View.GONE);


            editText = (EditText) findViewById(R.id.et_input);
            editText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    switch (i) {
                        case KeyEvent.KEYCODE_ENTER: {

                            switch (mode) {
                                case CommonCode.MODE_INFO:
                                    fetchDataInfo(editText.getText().toString());
                                    break;
                                case CommonCode.MODE_DELETE:
                                    fetchDataDelete(editText.getText().toString());
                                    break;
                            }

                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);    //hide keyboard

                            return true;
                        }
                    }

                    return false;
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "스캔 취소", Toast.LENGTH_LONG).show();

            } else {

                String code = result.getContents();

                switch (mode) {
                    case CommonCode.MODE_INPUT:
                        textViewBarcode.setText(code);
                        break;
                    case CommonCode.MODE_INFO:
                        break;
                    case CommonCode.MODE_DELETE:
                        break;
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void fetchDataInput(String code) {

        String barcode = code;

        String appKey = editTextAppkey.getText().toString();
        String storeKey = editTextStorekey.getText().toString();
        String storeName = editTextStoreNamekey.getText().toString();
        String price = editTextPrice.getText().toString();
        String productName = editTextProductName.getText().toString();

        Disposable disposable = new APIClient(new APIClient.LogListener() {
            @Override
            public void setLog(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewResponse.append(s);
                    }
                });
            }
        }).APIService.input(appKey, barcode, storeKey, storeName, price, productName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> {

                })
                .doAfterTerminate(() -> {

                }).subscribeWith(new DisposableObserver<InputResponse>() {
                    @Override
                    public void onNext(InputResponse inputResponse) {
//                        String result = inputResponse.toString();
//                        textViewResponse.setText(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textViewRequest.setText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void fetchDataInfo(String txid) {

        String txId = txid;

        new APIClient(new APIClient.LogListener() {
            @Override
            public void setLog(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewResponse.append(s);
                    }
                });
            }
        }).APIService.info(txId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> {

                })
                .doAfterTerminate(() -> {

                }).subscribeWith(new DisposableObserver<InfoResponse>() {
            @Override
            public void onNext(InfoResponse infoResponse) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void fetchDataDelete(String txid) {
        String txId = txid;

        new APIClient(new APIClient.LogListener() {
            @Override
            public void setLog(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewResponse.append(s);
                    }
                });
            }
        }).APIService.delete(txId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> {

                })
                .doAfterTerminate(() -> {

                })
                .subscribeWith(new DisposableObserver<DeleteResponse>() {
                    @Override
                    public void onNext(DeleteResponse deleteResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
