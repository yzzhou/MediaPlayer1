package myapplication.mediaplayertest.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

import myapplication.mediaplayertest.R;
import myapplication.mediaplayertest.service.MusicPlayService;

public class AudioplayerActivity extends AppCompatActivity {
    private ImageView iv_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);
        iv_icon = (ImageView)findViewById(R.id.iv_icon);
        iv_icon.setBackgroundResource(R.drawable.animation_bg);
        AnimationDrawable background = (AnimationDrawable) iv_icon.getBackground();
        background.start();
        Intent intent = new Intent(this, MusicPlayService.class);
        startService(intent);

    }

//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }
//    private  void start(){
//
//    }
    //private void
}
