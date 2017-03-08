
package com.jishang.bimeng.activity.wode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.fragment.FragmentActivityHome;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.custom.view.PopupWindows;
import com.jishang.bimeng.ui.custom.view.PopupWindows.IModelPopupWindows;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "个人设置"UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class GerenSzActivity extends BaseActivity implements OnClickListener, IModelPopupWindows {
    private TextView mTv_nicheng, mTv_qianming, mTv_qianming_real, mTv_sex, mTv_nicheng_real,
            mTv_save, mTv_wangba;

    private RelativeLayout mRl_qianming, mRl_nicheng, mRl_exist, mRl_sex,
            activity_grsz_rl_touxiang;

    private ImageView mImg_head;

    private SharUtil mshSharUtil;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private Context mContext;

    private List<BasicNameValuePair> params;

    private String token;

    private Gson mGson;

    /**
     * 拍照按钮点击事件返回后的标示
     */
    private static final int PHOTO_CARMERA = 1;

    private static final int PHOTO_PICK = 2;

    /**
     * 照片修建UI返回的标示
     */
    private static final int PHOTO_CUT = 3;

    @Override
    public int initResource() {
        return R.layout.activity_grsz;
    }

    @Override
    public void initView() {
        mContext = this;
        uiHandler = new GerenSzHandler(this);
        mshSharUtil = new SharUtil(GerenSzActivity.this);
        token = mshSharUtil.getAccess_token();
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        imageLoader_head = ImageLoader.getInstance();
        mRl_qianming = (RelativeLayout)findViewById(R.id.activity_grsz_rl_qianming);
        mRl_nicheng = (RelativeLayout)findViewById(R.id.activity_grsz_rl_nicheng);
        mTv_nicheng = (TextView)findViewById(R.id.activity_grsz_tv_nicheng);
        mTv_qianming = (TextView)findViewById(R.id.activity_grsz_tv_qianming);
        mImg_head = (ImageView)findViewById(R.id.activity_grsz_headimg);
        mTv_qianming_real = (TextView)findViewById(R.id.activity_grsz_tv_qianming_real);
        mTv_nicheng_real = (TextView)findViewById(R.id.activity_grsz_tv_nicheng_real);
        mRl_exist = (RelativeLayout)findViewById(R.id.activity_grsz_rl_extist);
        mTv_wangba = (TextView)findViewById(R.id.activity_grsz_tv_wangba);
        mTv_sex = (TextView)findViewById(R.id.activity_grsz_tv_sex);
        activity_grsz_rl_touxiang = (RelativeLayout)findViewById(R.id.activity_grsz_rl_touxiang);
        mGson = new Gson();

        init();
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);

    }

    public void init() {
        String headimg = mshSharUtil.getHead_img();
        String userName = mshSharUtil.getUserName();
        String qianming = mshSharUtil.getDescribetion_info();
        int sex = mshSharUtil.getSex();
        imageLoader_head.displayImage(headimg, mImg_head, options_head);
        mTv_qianming_real.setText(qianming);
        mTv_nicheng_real.setText(userName);
        mTv_wangba.setText(mshSharUtil.getBusiness());
        if (sex == 0) {
            mTv_sex.setText("女");
        } else if (sex == 1) {
            mTv_sex.setText("男");
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mRl_nicheng.setOnClickListener(this);
        mRl_qianming.setOnClickListener(this);
        mRl_exist.setOnClickListener(this);
        activity_grsz_rl_touxiang.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.activity_grsz_rl_qianming:// 修改签名
                intent = new Intent(GerenSzActivity.this, XiugaiqmActivity.class);
                startActivityForResult(intent, 101);

                break;
            case R.id.activity_grsz_rl_nicheng:// 修改昵称
                intent = new Intent(GerenSzActivity.this, XiugaincActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.activity_grsz_rl_extist: {// 退出程序
                Setdialog();
            }
                break;
            case R.id.activity_grsz_rl_touxiang:// 修改头像
                PopupWindows popupWindows = new PopupWindows(GerenSzActivity.this);
                popupWindows.setPHOTO_CARMERA(PHOTO_CARMERA);
                popupWindows.setPHOTO_PICK(PHOTO_PICK);
                popupWindows.setModel(this);
                break;
            default:
                break;

        }

    }

    public void Setdialog() {
        new AlertDialog.Builder(mContext).setTitle("警告").setMessage("确定退出？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setNull();
                        Exist();

                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void Exist() {
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = "http://apicms.gbimoo.com/v1/user/logout.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    TYEntity entity = mGson.fromJson(result, TYEntity.class);
                    if (entity.getStatus() == 0) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = entity.getErrors();
                        uiHandler.sendMessage(msg);
                    } else if (entity.getStatus() == 1) {
                        uiHandler.sendEmptyMessage(1);
                    }
                }

            };
        }.start();
    }

    class GerenSzHandler extends UIHandler {
        public GerenSzHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 1: {
                    setResult(FINISH_EXIT);
                    finish();
                }
                    break;
                default:
                    break;

            }
        };
    };

    public static final int FINISH_EXIT = 102;

    /**
     * 杀掉主页面
     */
    public void kill() {
        if (FragmentActivityHome.instance != null) {
            FragmentActivityHome.instance.finish();
        }
        finish();
    }

    public void setNull() {
        SharUtil mSharUtil = new SharUtil(mContext);
        mSharUtil.setUserName(null);
        mSharUtil.setUserPhone(null);
        mSharUtil.setUserPwd(null);
        mSharUtil.setAccess_token(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (resultCode) {
            case 200:
                String content = (String)intent.getSerializableExtra("content");
                if (content != null) {
                    mTv_nicheng_real.setText(content);
                }

                break;
            case 201:
                String content1 = (String)intent.getSerializableExtra("content");
                if (content1 != null) {
                    mTv_qianming_real.setText(content1);
                }
                break;
            default:
                break;
        }
        switch (requestCode) {

            case PHOTO_CARMERA:// 拍照UI返回
                // 返回的Intent为null
                startPhotoZoom(Uri.fromFile(file), 300);// 跳转到修剪照片UI
                break;
            case PHOTO_PICK:// 从相册获取图片UI返回
                if (null != intent) {
                    startPhotoZoom(intent.getData(), 300);
                }
                break;
            case PHOTO_CUT:// 修剪照片UI返回
                if (null != intent) {
                    setPicToView(intent);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * @param uri
     * @param size 调用系统裁剪
     */
    private void startPhotoZoom(Uri uri, int size) {
        Log.e("uri", uri + "");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }

    /**
     * 将裁剪后的图片显示在ImageView上
     * 
     * @param intent
     */
    private void setPicToView(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            final Bitmap bmp = bundle.getParcelable("data");
            Log.e("---", bundle.getParcelable("data") + "");
            mImg_head.setImageBitmap(bmp);
            saveHeadBmpToSD(bmp);
            if (file != null) {
                application.getController().doUploadPersonHeadToServer(uiHandler, file.getPath());
            }
        }
    }

    /**
     * 把剪裁后的图片保存到SD
     * 
     * @param bmp
     */
    private void saveHeadBmpToSD(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(file);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void back(View v) {
        finish();
    }

    File file;

    @Override
    public void setUri(File file) {
        this.file = file;
    }

}
