package com.fei.zzqghelper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private TextView main_jingdu_tv;
    private ImageView main_luopan_iv;
    private EditText main_dushu_et;
    private Button main_dushu_bt;
    private LocationManager lManager;
    private Button main_location_bt;
    private RadioGroup main_choiceGroup_rg;
    private String Model = LocationManager.NETWORK_PROVIDER;

    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限

    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        initLocation();

    }

    private void initView(){
        main_jingdu_tv = findViewById(R.id.main_jingdu_tv);
        main_location_bt = findViewById(R.id.main_location_bt);
        main_choiceGroup_rg = findViewById(R.id.main_choiceGroup_rg);
        main_luopan_iv = findViewById(R.id.main_luopan_iv);
        main_dushu_et = findViewById(R.id.main_dushu_et);
        main_dushu_bt = findViewById(R.id.main_dushu_bt);
    }

    private void initListener(){
        main_location_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocation();
            }
        });
        main_choiceGroup_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_Net_rb :
                        Model = LocationManager.NETWORK_PROVIDER;
                        break;
                    case R.id.main_gps_rb :
                        Model = LocationManager.GPS_PROVIDER;
                        break;
                }
            }
        });
        main_dushu_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = main_dushu_et.getText().toString();
                if(null != num && !num.equals("")) {
                    int numi = Integer.parseInt(num);
                    main_luopan_iv.setRotation(numi);
                }else{
                    Toast.makeText(MainActivity.this,"请输入要设置的度数",Toast.LENGTH_SHORT).show();
                }
            }
        });
        main_dushu_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = main_dushu_et.getText().toString();
                if(null != num && !num.equals("")) {
                    int numi = Integer.parseInt(num);
                    if (numi > 360) {
                        main_dushu_et.setText("360");
                    }
                    if (numi < 0) {
                        main_dushu_et.setText("0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initLocation() {
        if(null == lManager) {
            //获取LocationManager
            lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        lManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {// 没有权限，申请权限。
                    ActivityCompat.requestPermissions(this, LOCATIONGPS, BAIDU_READ_PHONE_STATE);
                    return;
                }
            }
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }

        /**
         * 参1:选择定位的方式
         * 参2:定位的间隔时间
         * 参3:当位置改变多少时进行重新定位
         * 参4:位置的回调监听
         */
        lManager.requestLocationUpdates(Model, 1, 0, new LocationListener() { //当位置改变的时候调用
            @Override
            public void onLocationChanged(Location location) {
                //经度
                double longitude = location.getLongitude();
                //纬度
                double latitude = location.getLatitude();
                main_jingdu_tv.setText("经度:==>" + longitude + " \n 纬度==>" + latitude);
            } //当GPS状态发生改变的时候调用

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status) {
                    case LocationProvider.AVAILABLE:
                        Toast.makeText(MainActivity.this, "当前GPS为可用状态!", Toast.LENGTH_SHORT).show();
                        break;
                    case LocationProvider.OUT_OF_SERVICE:
                        Toast.makeText(MainActivity.this, "当前GPS不在服务内", Toast.LENGTH_SHORT).show();
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Toast.makeText(MainActivity.this, "当前GPS为暂停服务状态", Toast.LENGTH_SHORT).show();
                        break;
                }
            } //GPS开启的时候调用

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(MainActivity.this, "GPS开启了", Toast.LENGTH_SHORT).show();
            }

            //GPS关闭的时候调用
            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this, "GPS关闭了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) { super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) { // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE: //如果用户取消，permissions可能为null.
//                if (grantResults[0] == PERMISSION_GRANTED && grantResults.length > 0) { //有权限
//                    // 获取到权限，作相应处理
//                    getLocation();
//                } else {
                    initLocation();
//                }
                break;
            default:
                break;
        }
    }

}
