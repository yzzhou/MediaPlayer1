package myapplication.mediaplayertest.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapplication.mediaplayertest.R;
import myapplication.mediaplayertest.adapter.RecyclerViewAdpater;
import myapplication.mediaplayertest.domain.NetAudioBean;
import myapplication.mediaplayertest.fragment.BaseFragment;

/**
 * Created by zhouzhou on 2017/6/1.
 */

public class RecyclerViewFragment extends BaseFragment {
    public  String NET_AUDIO_URL ="http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8";
    private static final String TAG = RecyclerViewFragment.class.getSimpleName();
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.tv_nomedia)
    TextView tvNomedia;
    private RecyclerViewAdpater myAdapter;
    private List<NetAudioBean.ListBean> datas;

    @Override
    public View initView() {
        Log.e(TAG, "网络音频UI被初始化了");
        View view = View.inflate(context, R.layout.fragment_recyclerview, null);
        ButterKnife.bind(this, view);

        //设置点击事件
        //设置点击事件
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                NetAudioBean.ListBean listEntity = datas.get(position);
//                if(listEntity !=null ){
//                    //3.传递视频列表
//                    Intent intent = new Intent(mContext,ShowImageAndGifActivity.class);
//                    if(listEntity.getType().equals("gif")){
//                        String url = listEntity.getGif().getImages().get(0);
//                        intent.putExtra("url",url);
//                        mContext.startActivity(intent);
//                    }else if(listEntity.getType().equals("image")){
//                        String url = listEntity.getImage().getBig().get(0);
//                        intent.putExtra("url",url);
//                        mContext.startActivity(intent);
//                    }
//                }
//
//
//            }
//        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "网络视频数据初始化了...");




//        String saveJson = CacheUtils.getString(mContext, Constants.NET_AUDIO_URL);
//        if(!TextUtils.isEmpty(saveJson)){
//            processData(saveJson);
//        }

        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams reques = new RequestParams(NET_AUDIO_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

//                CacheUtils.putString(context, SyncStateContract.Constants.NET_AUDIO_URL,result);
//                LogUtil.e("onSuccess==" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onError==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished==");
            }
        });

    }

    private void processData(String json) {
        NetAudioBean netAudioBean = paraseJons(json);
//        LogUtil.e(netAudioBean.getList().get(0).getText()+"--------------");
        datas = netAudioBean.getList();
        if(datas != null && datas.size() >0){
            //有视频
            tvNomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new RecyclerViewAdpater(context,datas);
            recyclerview.setAdapter(myAdapter);

            //添加布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        }else{
            //没有视频
            tvNomedia.setVisibility(View.VISIBLE);
        }

        progressbar.setVisibility(View.GONE);



    }

    /**
     * json解析数据
     * @param json
     * @return
     */
    private NetAudioBean paraseJons(String json) {
        return new Gson().fromJson(json,NetAudioBean.class);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
