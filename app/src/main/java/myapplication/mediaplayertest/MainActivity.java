package myapplication.mediaplayertest;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.PixelCopy;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private PixelCopy rxPermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(
                         Manifest.permission.INTERNET,
                         Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.WAKE_LOCK,
                         Manifest.permission.ACCESS_NETWORK_STATE,
                         Manifest.permission.ACCESS_WIFI_STATE,
                         Manifest.permission.READ_PHONE_STATE

                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if(aBoolean){
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startMain2Activity();
                                }


                            },2000);
                        }else{
                            finish();
                        }
                    }
                });


    }
    private void startMain2Activity() {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startMain2Activity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
