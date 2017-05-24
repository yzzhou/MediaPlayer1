package myapplication.mediaplayertest.activity;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import myapplication.mediaplayertest.R;

public class AudioplayerActivity extends AppCompatActivity {
    private ImageView iv_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);
        iv_icon = (ImageView)findViewById(R.id.iv_icon);
        //iv_icon.setBackgroundResource(R.drawable.anima);

    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    private  void start(){

    }
    //private void
}
