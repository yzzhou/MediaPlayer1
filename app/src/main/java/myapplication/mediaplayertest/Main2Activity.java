package myapplication.mediaplayertest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import myapplication.mediaplayertest.fragment.BaseFragment;
import myapplication.mediaplayertest.pager.LocalAudioPager;
import myapplication.mediaplayertest.pager.LocalVideoPager;
import myapplication.mediaplayertest.pager.NetAudioPager;
import myapplication.mediaplayertest.pager.NetVideoPager;

public class Main2Activity extends AppCompatActivity {
    private RadioGroup rg_main;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private Fragment tempFragment;
    private SensorManager sensorManager;
    private JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        isGrantExternalRW(this);
        initFragment();

        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_main.check(R.id.rb_local_video);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideoPager());
        fragments.add(new LocalAudioPager());
        fragments.add(new NetAudioPager());
        fragments.add(new NetVideoPager());
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_local_video:
                    position = 0;
                    break;
                case R.id.rb_local_audio:
                    position = 1;
                    break;
                case R.id.rb_net_audio:
                    position = 2;

                    break;
                case R.id.rb_net_video:
                    position = 3;

                    break;
            }
            BaseFragment cueeentFragment = fragments.get(position);
            addFragment(cueeentFragment);
        }
    }
        private void addFragment(Fragment cueeentFragment) {
            if(tempFragment != cueeentFragment){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(!cueeentFragment.isAdded()){
                    if(tempFragment != null){
                        ft.hide(tempFragment);
                    }
                    ft.add(R.id.fl_content,cueeentFragment);
                }else{

                    if(tempFragment != null){
                        ft.hide(tempFragment);
                    }
                    ft.show(cueeentFragment);
                }
                ft.commit();
                tempFragment = cueeentFragment;
            }

    }
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }
    private boolean isExit = false;
    //private Handler handler = new Handler();
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            if(position !=0){
                rg_main.check(R.id.rb_local_video);
                return  true;
            }else if(!isExit){
                Toast.makeText(Main2Activity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExit=true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false ;
                    }
                },2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

}
