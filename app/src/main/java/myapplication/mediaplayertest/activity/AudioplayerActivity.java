package myapplication.mediaplayertest.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

import myapplication.mediaplayertest.IMusicPlayService;
import myapplication.mediaplayertest.R;
import myapplication.mediaplayertest.domain.Lyric;
import myapplication.mediaplayertest.domain.MediaItem;
import myapplication.mediaplayertest.service.MusicPlayService;
import myapplication.mediaplayertest.utils.LyricUitls;
import myapplication.mediaplayertest.utils.Utils;
import myapplication.mediaplayertest.view.BaseVisualizerView;
import myapplication.mediaplayertest.view.LyricShow;

public class AudioplayerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_icon;
    private RelativeLayout rlTop;
    private ImageView ivIcon;
    private TextView tvArtist;
    private TextView tvAudioname;
    private LinearLayout llBottom;
    private TextView tvTime;
    private SeekBar seekbarAudio;
    private Button btnPlaymode;
    private Button btnPre;
    private Button btnStartPause;
    private Button btnNext;
    private Button btnLyric;
    private int position;
    private IMusicPlayService service;
    private MyReceiver receiver;
    private Utils utils;
    private   final  static int PROGRESS=0;
    private   final  static int SHOW_LYRIC=1;
    private boolean notification;
    private LyricShow lyricShow;
    private Visualizer mVisualizer;
    private BaseVisualizerView visualizerview;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_LYRIC:
                    try {
                        int currentPosition = service.getCurrentPosition();
                        lyricShow.setNextLyricShow(currentPosition);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    removeMessages(SHOW_LYRIC);
                    sendEmptyMessage(SHOW_LYRIC);

                    break;
                case PROGRESS:
                    try {
                        int currentPosition = service.getCurrentPosition();
                        seekbarAudio.setProgress(currentPosition);

                        //设置更新时间
                        tvTime.setText(utils.stringForTime(currentPosition) + "/" + utils.stringForTime(service.getDuration()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }


                    //每秒中更新一次
                    removeMessages(PROGRESS);
                    sendEmptyMessageDelayed(PROGRESS, 1000);

                    break;

            }
        }
    };
    private ServiceConnection conon = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
                service=IMusicPlayService.Stub.asInterface(iBinder);
            if(service !=null){
                try {
                    if(notification){
                        setViewData(null);
                    }else{
                        service.openAudio(position);
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }


    };

    private void findViews() {
        setContentView(R.layout.activity_audioplayer);
        ivIcon = (ImageView)findViewById(R.id.iv_icon);
        ivIcon.setBackgroundResource(R.drawable.animation_bg);
        AnimationDrawable background = (AnimationDrawable) ivIcon.getBackground();
        background.start();
        rlTop = (RelativeLayout)findViewById( R.id.rl_top );
        iv_icon = (ImageView)findViewById( R.id.iv_icon );
        tvArtist = (TextView)findViewById( R.id.tv_artist );
        tvAudioname = (TextView)findViewById( R.id.tv_audioname );
        llBottom = (LinearLayout)findViewById( R.id.ll_bottom );
        tvTime = (TextView)findViewById( R.id.tv_time );
        seekbarAudio = (SeekBar)findViewById( R.id.seekbar_audio );
        btnPlaymode = (Button)findViewById( R.id.btn_playmode );
        btnPre = (Button)findViewById( R.id.btn_pre );
        btnStartPause = (Button)findViewById( R.id.btn_start_pause );
        btnNext = (Button)findViewById( R.id.btn_next );
        lyricShow = (LyricShow)findViewById(R.id.lyricShow);
        btnLyric = (Button)findViewById( R.id.btn_lyric );
        visualizerview = (BaseVisualizerView)findViewById(R.id.visualizerview);

        btnPlaymode.setOnClickListener( this );
        btnPre.setOnClickListener( this );
        btnStartPause.setOnClickListener( this );
        btnNext.setOnClickListener( this );
        btnLyric.setOnClickListener( this );

        seekbarAudio.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
    }
    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                try {
                    service.seekTo(progress);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-05-25 11:02:39 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnPlaymode ) {
            // Handle clicks for btnPlaymode
            setPlayMode();
        } else if ( v == btnPre ) {
            // Handle clicks for btnPre
            try {
                service.pre();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if ( v == btnStartPause ) {
            // Handle clicks for btnStartPause

                try {
                    if(service.isPlaying()) {
                        service.pause();
                        btnStartPause.setBackgroundResource(R.drawable.btn_audio_start_selector);
                    }else{
                        service.start();
                        btnStartPause.setBackgroundResource(R.drawable.btn_audio_pause_selector);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


        } else if ( v == btnNext ) {
            // Handle clicks for btnNext
            try {
                service.next();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if ( v == btnLyric ) {
            // Handle clicks for btnLyric
        }
    }

    private void setPlayMode() {
        try {
            int playmode = service.getPlaymode();
            if (playmode == MusicPlayService.REPEAT_NORMAL) {
                playmode = MusicPlayService.REPEAT_SINGLE;
            } else if (playmode == MusicPlayService.REPEAT_SINGLE) {
                playmode = MusicPlayService.REPEAT_ALL;
            } else if (playmode == MusicPlayService.REPEAT_ALL) {
                playmode = MusicPlayService.REPEAT_NORMAL;
            }
            service.setPlaymode(playmode);
            setButtonImage();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private void setButtonImage() {
        try {

            int playmode = service.getPlaymode();
            if (playmode == MusicPlayService.REPEAT_NORMAL) {
                btnPlaymode.setBackgroundResource(R.drawable.btn_playmode_normal_selector);
            } else if (playmode == MusicPlayService.REPEAT_SINGLE) {
                btnPlaymode.setBackgroundResource(R.drawable.btn_playmode_single_selector);
            } else if (playmode == MusicPlayService.REPEAT_ALL) {
                btnPlaymode.setBackgroundResource(R.drawable.btn_playmode_all_selector);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        findViews();
        getData();
        startAndBindService();

    }

    private void initData() {

        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicPlayService.OPEN_COMPLETE);
        registerReceiver(receiver, intentFilter);
        utils = new Utils();
        EventBus.getDefault().register(this);

    }







    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setViewData(null);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setViewData(MediaItem mediaItem) {
        try {

            tvArtist.setText(service.getArtistName());
            tvAudioname.setText(service.getAudioName());
            setButtonImage();
            int duration = service.getDuration();
            seekbarAudio.setMax(duration);
            String audioPath = service.getAudioPath();

            String lyricPath = audioPath.substring(0,audioPath.lastIndexOf("."));
            File file = new File(lyricPath+".lrc");
            if(!file.exists()){
                file = new File(lyricPath+".txt");
            }
            LyricUitls lyricsUtils = new LyricUitls();
            lyricsUtils.readFile(file);

            ArrayList<Lyric> lyrics = lyricsUtils.getLyrics();
            lyricShow.setLyrics(lyrics);
            if(lyricsUtils.isLyric()){
                handler.sendEmptyMessage(SHOW_LYRIC);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(PROGRESS);
        //handler.sendEmptyMessage(SHOW_LYRIC);
        setupVisualizerFxAndUi();
    }

    private void setupVisualizerFxAndUi() {
        int audioSessionid = 0;
                try {
                        audioSessionid = service.getAudioSessionId();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                System.out.println("audioSessionid==" + audioSessionid);
                mVisualizer = new Visualizer(audioSessionid);
                // 参数内必须是2的位数
                        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
                // 设置允许波形表示，并且捕获它
                        visualizerview.setVisualizer(mVisualizer);
                mVisualizer.setEnabled(true);
    }


    //    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }
//    private  void start(){
//
//    }
    //private void

    private void getData() {
        notification = getIntent().getBooleanExtra("notification",false);
        if(!notification){
            position =getIntent().getIntExtra("position",0);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()){
            mVisualizer.release();
        }
    }

    @Override
    protected void onDestroy() {

        if(conon !=null){
            unbindService(conon);
            conon =null;
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        EventBus.getDefault().unregister(this);
        if(handler !=null){
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
    private void startAndBindService() {
        Intent intent = new Intent(this,MusicPlayService.class);
        bindService(intent,conon, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

}
