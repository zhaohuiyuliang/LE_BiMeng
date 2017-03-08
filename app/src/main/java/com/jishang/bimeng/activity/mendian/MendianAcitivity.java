package com.jishang.bimeng.activity.mendian;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.RegistActivity3;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.regist.BarEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MendianAcitivity extends BaseActivity implements OnClickListener {
	private GridView mGv;
	private String[] imageUrls; // 图片路径
	private DisplayImageOptions options; // 设置图片显示相关参数
	protected ImageLoader imageLoader;
	private ProgressDialog dialog;

	@Override
	public int initResource() {
		return R.layout.dialog_mendian;
	}

	@Override
	public void initView() {
		mGv = (GridView) findViewById(R.id.activity_regist_gv);
		// imageUrls=Constants.images;

		imageLoader = ImageLoader.getInstance();
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成

	}

	@Override
	public void initData() {
		Setprogrese();
		initPhoto();

	}

	public void initPhoto() {
		new Thread() {
			public void run() {

				String url = UrlUtils.BASEURL+"v1/province/get.json";
				String result = MyHttpRequest.getHttpGet(url, null);
				if (result != null) {
					Message msg = new Message();
					msg.obj = result;
					msg.what = 1;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dialog.cancel();
			String result = (String) msg.obj;
			switch (msg.what) {
			case 1:
				List<BarEntity> entities = ParseUtil.getBanner_photo(result);
				imageUrls = new String[entities.size()];
				for (int i = 0; i < entities.size(); i++) {
					imageUrls[i] = entities.get(i).getW_img();
				}
				mGv.setAdapter(new ItemListAdapter(entities));
				break;

			}
		};
	};

	@Override
	public void addListener() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public class ItemListAdapter extends BaseAdapter {
		List<BarEntity> entities;

		public ItemListAdapter(List<BarEntity> entities) {
			this.entities = entities;
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return imageUrls[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.item_list_regist, null);
				viewHolder = new ViewHolder();
				viewHolder.image = (ImageView) convertView
						.findViewById(R.id.iv_image);
				viewHolder.text = (TextView) convertView
						.findViewById(R.id.tv_introduce);
				viewHolder.bt = (Button) convertView
						.findViewById(R.id.choosedbt);
//				viewHolder.tg = (ToggleButton) findViewById(R.id.toggle_button);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			/**
			 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
			 * DisplayImageOptions配置文件
			 */
			imageLoader.displayImage(imageUrls[position], viewHolder.image,
					options);

			// viewHolder.text.setText("Item " + (position + 1)); //
			// TextView设置文本
			final BarEntity entity = entities.get(position);
			viewHolder.text.setText(entity.getW_name());
			viewHolder.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String wangba=entity.getW_name();
					Intent intent=new Intent(MendianAcitivity.this, RegistActivity3.class);
					intent.putExtra("wangba", wangba);
					Log.e("wangba", wangba);
					setResult(101, intent);
					finish();

				}
			});
			return convertView;
		}

	}

	public class ViewHolder {
		public ImageView image;
		public TextView text;
		public Button bt;
//		public ToggleButton tg;
	}

	public void Setprogrese() {
		dialog = new ProgressDialog(MendianAcitivity.this);
		dialog.setMessage("正在更新数据，请稍候。。。");
		dialog.show();
	}

}
