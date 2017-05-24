package myapplication.mediaplayertest.pager;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import myapplication.mediaplayertest.R;
import myapplication.mediaplayertest.activity.SystemVideoPlayerActivity;
import myapplication.mediaplayertest.adapter.NetVideoAdapter;
import myapplication.mediaplayertest.domain.MediaItem;
import myapplication.mediaplayertest.domain.MoveInfo;
import myapplication.mediaplayertest.fragment.BaseFragment;

/**
 * Created by zhouzhou on 2017/5/19.
 */

public class NetVideoPager extends BaseFragment {
    public static final String uri = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
    private SharedPreferences sp;
    private TextView tv_nodata;
    private ListView lv;
    private NetVideoAdapter adapter;
    private ArrayList<MediaItem> mediaItems;
    private MaterialRefreshLayout materialRefreshLayout;
    private boolean isLoadMore = false;
    private List<MoveInfo.TrailersBean> datas;

    @Override
    public View initView() {
        sp = context.getSharedPreferences("atguigu",context.MODE_PRIVATE);
        View view = View.inflate(context, R.layout.fragment_net_video_pager, null);
        tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
        lv = (ListView) view.findViewById(R.id.lv);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                isLoadMore = false;
                getDataFromNet();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                isLoadMore = true;
                getMoreData();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MoveInfo.TrailersBean item = adapter.getItem(position);
                Intent intent = new Intent(context, SystemVideoPlayerActivity.class);
                Bundle bunlder = new Bundle();
                bunlder.putSerializable("videolist", mediaItems);
                intent.putExtra("position", position);
                intent.putExtras(bunlder);
                startActivity(intent);

            }
        });


        return view;
    }

    private void getMoreData() {
        final RequestParams request = new RequestParams("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "加载更多xUtils联网成功==" + result);
                processData(result);
                materialRefreshLayout.finishRefreshLoadMore();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "加载更xUtils联网失败==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "NetVideoPager-initData");
        String saveJson = sp.getString(uri,"");
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
            Log.e("TAG","解析缓存的数据=="+saveJson);
        }

        getDataFromNet();
    }

    private void getDataFromNet() {
        final RequestParams request = new RequestParams(uri);
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //SharedPreferences.Editor edit = sp.edit();
                sp.edit().putString(uri,result).commit();

                Log.e("TAG", "xUtils联网成功==" + result);
                processData(result);
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "xUtils联网失败==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void processData(String json) {
        MoveInfo moveInfo = new Gson().fromJson(json, MoveInfo.class);
        if(!isLoadMore){
            datas = moveInfo.getTrailers();
        if (datas != null && datas.size() > 0) {
            mediaItems = new ArrayList<>();

            for (int i = 0; i < datas.size(); i++) {
                MediaItem mediaItem = new MediaItem();
                mediaItem.setName(datas.get(i).getMovieName());
                mediaItem.setData(datas.get(i).getUrl());
                mediaItems.add(mediaItem);

            }
            tv_nodata.setVisibility(View.GONE);
            adapter = new NetVideoAdapter(context, datas);
            lv.setAdapter(adapter);
        } else {
            tv_nodata.setVisibility(View.VISIBLE);
        }
        }else{
            List<MoveInfo.TrailersBean> trailers = moveInfo.getTrailers();
            for (int i = 0; i < trailers.size(); i++) {
                MediaItem mediaItem = new MediaItem();
                mediaItem.setName(trailers.get(i).getMovieName());
                mediaItem.setData(trailers.get(i).getUrl());
                mediaItems.add(mediaItem);

            }

            datas.addAll(trailers);
            adapter.notifyDataSetChanged();
        }
        }
    }






