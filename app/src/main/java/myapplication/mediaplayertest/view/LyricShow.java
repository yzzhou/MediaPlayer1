package myapplication.mediaplayertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;

import myapplication.mediaplayertest.domain.Lyric;
import myapplication.mediaplayertest.utils.DensityUtil;

/**
 * Created by zhouzhou on 2017/5/26.
 */

public class LyricShow extends TextView {
    private final Context conetext;
    private ArrayList<Lyric> lyrics;
    private Paint paintGreen;
    private Paint paintWhite;
    private int index = 0;
    private  int width;
    private  int height;
    private   float  textHeight =20;
    private float currentPosition;
    private long timePoint;
    private long sleepTime;



    public LyricShow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.conetext = context;
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void initView() {

        textHeight = DensityUtil.dip2px(conetext,20);
        paintGreen = new Paint();
        paintGreen.setColor(Color.GREEN);
        paintGreen.setAntiAlias(true);
        paintGreen.setTextSize(16);
        paintGreen.setTextSize(DensityUtil.dip2px(conetext,16));
        paintGreen.setTextAlign(Paint.Align.CENTER);

        paintWhite = new Paint();
        paintWhite.setColor(Color.WHITE);
        paintWhite.setAntiAlias(true);
        paintWhite.setTextSize(16);
        paintWhite.setTextSize(DensityUtil.dip2px(conetext,16));
        paintWhite.setTextAlign(Paint.Align.CENTER);


//        lyrics = new ArrayList<>();
//        Lyric lyric = new Lyric();
//        for(int i = 0;i<1000;i++){
//            lyric.setContent("aaaaaaaa__" +i);
//            lyric.setSleepTime(2000);
//            lyric.setTimePoint(2000*i);
//            lyrics.add(lyric);
//
//           lyric = new Lyric();
//        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lyrics != null && lyrics.size() > 0) {
            String currentContent = lyrics.get(index).getContent();
            canvas.drawText(currentContent,width/2,height/2, paintGreen);
            float tempY = height/2;
            for(int i = index-1;i>=0;i--){
                String preContent = lyrics.get(i).getContent();

                tempY = tempY-textHeight;
                if(tempY<0){
                    break;
                }
                canvas.drawText(preContent,width/2,tempY,paintWhite);
            }
            tempY = height/2;
            for(int i = index+1;i<lyrics.size();i++){
                String nextContent = lyrics.get(i).getContent();

                tempY = tempY+textHeight;
                if(tempY>height){
                    break;
                }
                canvas.drawText(nextContent,width/2,tempY,paintWhite);
            }

        }else{
            canvas.drawText("没有找到歌词...",width/2,height/2, paintGreen);
        }

    }


    public void setNextLyricShow(int currentPosition) {
            this.currentPosition = currentPosition;
        if(lyrics ==null ||lyrics.size() ==0)
            return;
        for(int i =1;i<lyrics.size();i++){
            if(currentPosition<lyrics.get(i).getTimePoint()){
                int tempIndex = i-1;
                if(currentPosition>=lyrics.get(tempIndex).getTimePoint()){
                    index = tempIndex;
                    timePoint = lyrics.get(index).getTimePoint();
                    sleepTime = lyrics.get(index).getSleepTime();
                }
            }else{
                index = i;
            }

        }
        invalidate();
    }

    public void setLyrics(ArrayList<Lyric> lyrics) {
        this.lyrics = lyrics;
    }
}
