package myapplication.mediaplayertest.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import io.vov.vitamio.MediaPlayer;
import myapplication.mediaplayertest.IMusicPlayService;
import myapplication.mediaplayertest.domain.MediaItem;

/**
 * Created by zhouzhou on 2017/5/24.
 */

public class MusicPlayService extends Service {

    private  IMusicPlayService.Stub stub = new IMusicPlayService.Stub() {
        MusicPlayService service = MusicPlayService.this;
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void openAudio(int position) throws RemoteException {
            service.openAudio(position);
        }

        @Override
        public void start() throws RemoteException {
            service.start();
        }

        @Override
        public void pause() throws RemoteException {
            service.pause();
        }

        @Override
        public String getAetistName() throws RemoteException {
            return service.getAetistName();
        }

        @Override
        public String getAudioPath() throws RemoteException {
            return service.getAudioPath();
        }

        @Override
        public int getDuration() throws RemoteException {
            return service.getDuration();
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            service.seekTo(position);
        }

        @Override
        public void next() throws RemoteException {
            service.next();
        }

        @Override
        public void pre() throws RemoteException {
            service.pre();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return mediaPlayer.isPlaying();
        }

    };

    private void setPlaymode(int playmode) {

    }

    private ArrayList<MediaItem> mediaItems;
   private  int position;
    private MediaPlayer mediaPlayer;
    public static final String OPEN_COMPLETE = "com.atguigu.mobileplayer.OPEN_COMPLETE";
    private NotificationManager nm;
    static final int REPEAT_NORMAL = 1;
    public static final int REPEAT_SINGLE = 2;
    public static final int REPEAT_ALL = 3;
    private int playmode = REPEAT_NORMAL;
    private boolean isCompletion = false;
    private SharedPreferences sp;
    private MediaItem mediaItem;


    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("atguigu",MODE_PRIVATE);
        playmode = sp.getInt("playmode",getPlaymode());
        getData();
    }

    private int getPlaymode() {
        return 0;
    }

    private void getData() {
        new Thread() {
            public void run() {
                mediaItems = new ArrayList<MediaItem>();
                ContentResolver resolver = getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.ARTIST
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {

                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        if (duration > 10 * 1000) {
                            mediaItems.add(new MediaItem(name, duration, size, data, artist));
                        }
                    }
                        cursor.close();
                    }
                }
            }.start();

    }

    @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return stub;
        }
    private void openAudio(int position){
        this.position = position;
        if(mediaItems !=null&& mediaItems.size()>0){
            if(position<mediaItems.size()){
                 mediaItem = mediaItems.get(position);
                if(mediaPlayer !=null){
                    mediaPlayer.reset();
                    mediaPlayer =null;
                    try {
                        mediaPlayer = new MediaPlayer(this);

                        mediaPlayer.setDataSource(mediaItem.getData());
                        mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
                        mediaPlayer.setOnErrorListener(new MyOnErrorListener());
                        mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());
                        mediaPlayer.prepareAsync();

                        if(playmode== MusicPlayService.REPEAT_SINGLE){
                            isCompletion = false;
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
                       Toast.makeText(MusicPlayService.this, "音频还没有加载完成", Toast.LENGTH_SHORT).show();
                   }
    }
    private void start(){
        mediaPlayer.start();
    }
    private void pause(){
        mediaPlayer.pause();
    }
    private  String getAetistName(){
        return "";

    }
    private String getAudioPath(){
        return "";
    }
    private int getDuration(){
        return 0;
    }
    private void seekTo(int position){

    }
    private void next(){

    }
    private void pre(){

    }

    private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
                start();
        }
    }

    private class MyOnErrorListener implements MediaPlayer.OnErrorListener {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            next();
            return true;
        }
    }

    private class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            next();

        }
    }
}
