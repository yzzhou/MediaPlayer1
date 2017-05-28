package myapplication.mediaplayertest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;

import java.util.List;

import myapplication.mediaplayertest.R;
import myapplication.mediaplayertest.domain.SearchBean;
import myapplication.mediaplayertest.utils.Utils;

/**
 * Created by zhouzhou on 2017/5/27.
 */

public class SearchAdapter extends BaseAdapter {
    private final Context context;
    List<SearchBean.ItemsBean> datas;
    private Utils utils;
    private ImageOptions imageOptions;

    public SearchAdapter(Context context, List<SearchBean.ItemsBean> datas) {
        this.context = context;
        this.datas = datas;
        utils = new Utils();
        imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.drawable.video_default)
                .setLoadingDrawableId(R.drawable.video_default)
                .build();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public SearchBean.ItemsBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_net_video,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        SearchBean.ItemsBean trailersBean = datas.get(position);

        viewHolder.tv_name.setText(trailersBean.getItemTitle());
        viewHolder.tv_size.setText(trailersBean.getDatecheck());
        viewHolder.tv_duration.setText(trailersBean.getKeywords());
        //使用xUtils3请求网络图片
        // x.image().bind(viewHolder.iv_icon, trailersBean.getCoverImg(),imageOptions);
        Picasso.with(context)
                .load(trailersBean.getItemImage().getImgUrl1())
                //设置正在加载显示的图片
                .placeholder(R.drawable.video_default)
                //加载出错显示的图片
                .error(R.drawable.video_default)
                .into(viewHolder.iv_icon);


        return convertView;
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_duration;
        TextView tv_size;
    }
}
