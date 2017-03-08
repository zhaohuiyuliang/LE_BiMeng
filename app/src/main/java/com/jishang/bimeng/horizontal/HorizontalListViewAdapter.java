package com.jishang.bimeng.horizontal;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.PhotoEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class HorizontalListViewAdapter extends BaseAdapter {
	private List<PhotoEntity> entities;
	protected ImageLoader imageLoader_head;
	private DisplayImageOptions options_head; // 设置图片显示相关参数

	public HorizontalListViewAdapter(Context context, List<PhotoEntity> entities) {

		options_head = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成
		imageLoader_head = ImageLoader.getInstance();
		mInflater = LayoutInflater.from(context);
		this.entities = entities;
		Log.e("entities--", entities.toString());
	}

	@Override
	public int getCount() {
		if (entities.size() == 0) {
			return 0;
		}
		return entities.size();
	}

	private LayoutInflater mInflater;

	@Override
	public Object getItem(int position) {
		return position;
	}

	private static class ViewHolder {
		private ImageView im;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.horizontallistview_item,
					null);
			vh.im = (ImageView) convertView.findViewById(R.id.iv_pic);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
			imageLoader_head.displayImage(entities.get(position).getGm_img(), vh.im, options_head);
             
		}
		return convertView;
	}
}