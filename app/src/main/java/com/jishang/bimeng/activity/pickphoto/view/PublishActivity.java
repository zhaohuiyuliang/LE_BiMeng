
package com.jishang.bimeng.activity.pickphoto.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.pickphoto.util.CustomConstants;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.utils.CheckNulll;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * "发布动态"UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class PublishActivity extends BaseActivity implements OnClickListener {
    private TextView sendTv;

    private String path = "";

    private EditText mEdt_content;

    private HttpUtils httpUtils;

    private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());

    private ImageView mImagv;

    private RelativeLayout mRl_publish;

    private Gson mGson;

    private Context mContext;

    private static final int PHOTO_CARMERA = 1;

    private static final int PHOTO_PICK = 2;

    private static final int PHOTO_CUT = 3;

    @Override
    public int initResource() {
        return R.layout.act_publish;
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    public void initView() {
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        mImagv = (ImageView)findViewById(R.id.act_public_imgv);
        httpUtils = new HttpUtils(10000);
        mEdt_content = (EditText)findViewById(R.id.act_publish_content);
        mRl_publish = (RelativeLayout)findViewById(R.id.activity_publish_save);
        mGson = new Gson();

    }

    private boolean publish_status = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_publish_save:// 发布按键事件触发
                if (publish_status) {
                    Toast.makeText(application, "发布中，请稍后...", Toast.LENGTH_LONG).show();
                } else {
                    publish_status = true;
                    uploadPublishMsg();
                }
                break;
            case R.id.act_public_imgv:
                new PopupWindows(PublishActivity.this);
                break;
            default:
                break;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 涓婁紶鎴愬姛涔嬪悗鎶婄収鐗囩Щ闄�
     */
    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext) {

            View view = View.inflate(mContext, R.layout.item_popupwindow, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout)view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(450);// 杩欎釜鍦版柟鍘诲鏋滆缃负match_parent浼氶攢姣� 涓嶄簡
            ColorDrawable cd = new ColorDrawable(-0000);
            setBackgroundDrawable(cd);
            setFocusable(true);
            setOutsideTouchable(true);

            setContentView(view);
            showAtLocation(view, Gravity.BOTTOM, 0, 0);
            // update();

            Button bt1 = (Button)view.findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button)view.findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button)view.findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    startPick();
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra("camerasensortype", 2); // 璋冪敤鍓嶇疆鎽勫儚澶�
        openCameraIntent.putExtra("autofocus", true); // 鑷姩瀵圭劍
        openCameraIntent.putExtra("fullScreen", false); // 鍏ㄥ睆
        openCameraIntent.putExtra("showActionIcons", false);
        // 鎸囧畾璋冪敤鐩告満鎷嶇収鍚庣収鐗囩殑瀛樺偍璺緞
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        path = tempFile.getPath();
        startActivityForResult(openCameraIntent, PHOTO_CARMERA);
    }

    // 璋冪敤绯荤粺鐩稿唽
    protected void startPick() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_PICK);
    }

    /**
     * @param uri
     * @param size 璋冪敤绯荤粺瑁佸壀
     */
    private void startPhotoZoom(Uri uri, int size) {
        Log.e("uri", uri + "");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop涓簍rue鏄缃湪寮�鍚殑intent涓缃樉绀虹殑view鍙互瑁佸壀
        intent.putExtra("crop", true);
        // aspectX,aspectY鏄楂樼殑姣斾緥
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY鏄鍓浘鐗囩殑瀹介珮
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 璁剧疆鏄惁杩斿洖鏁版嵁
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }

    // 灏嗚鍓悗鐨勫浘鐗囨樉绀哄湪ImageView涓�
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
            final Bitmap bmp = bundle.getParcelable("data");
            Log.e("---", bundle.getParcelable("data") + "");
            mImagv.setImageBitmap(bmp);

            saveCropPic(bmp);
            Log.i("MainActivity", tempFile.getAbsolutePath());
        }
    }

    // 鎶婅鍓悗鐨勫浘鐗囦繚瀛樺埌sdcard涓�
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CARMERA:
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case PHOTO_PICK:
                if (null != data) {
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                if (null != data) {
                    Log.e("data", data + "");
                    setPicToView(data);
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    public void addListener() {
        mImagv.setOnClickListener(this);
        mRl_publish.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    public void uploadPublishMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "动态发布中...");
        final String URL = UrlUtils.BASEURL + "v1/user_content/release.json";
        // 文字内容
        String content = mEdt_content.getText().toString().trim();
        RequestParams params = new RequestParams();
        if (CheckNulll.check(content)) {
            params.addBodyParameter("content", content);
        }
        if (tempFile.exists()) {
            params.addBodyParameter("thumb_img", tempFile);
        }
        Log.e("======", tempFile + "");
        String token = new SharUtil(PublishActivity.this).getAccess_token();
        params.addHeader("Authorization", "Bearer" + " " + token);
        params.addHeader("User-Agent", "imgfornote");

        httpUtils.send(HttpMethod.POST, URL, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException e, String msg) {
                Toast.makeText(PublishActivity.this, "动态发布失败", Toast.LENGTH_SHORT).show();
                publish_status = false;
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                publish_status = false;
                String result = responseInfo.result;
                try {
                    if (result != null) {
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        DialogUtils.getInstance().cancel();
                        if (entity.getStatus() == 0) {
                            ToastUtil.Toast(getApplicationContext(), "动态发布失败");
                        } else if (entity.getStatus() == 1) {
                            ToastUtil.Toast(getApplicationContext(), "动态发布成功");
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtil.Toast(mContext, "出现异常");
                }
            }
        });

    }

    public void back(View v) {
        finish();
    }

}
