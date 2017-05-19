package myapplication.mediaplayertest.pager;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import myapplication.mediaplayertest.fragment.BaseFragment;

/**
 * Created by zhouzhou on 2017/5/19.
 */

public class LocalVideoPager extends BaseFragment{
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("本地音乐的内容");
    }
}
