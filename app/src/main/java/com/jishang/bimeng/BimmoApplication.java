
package com.jishang.bimeng;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.util.Log;

import com.easemob.chat.EMChat;
import com.igexin.sdk.PushManager;
import com.jishang.bimeng.activity.pickphoto.util.CustomConstants;
import com.jishang.bimeng.net.Controller;
import com.jishang.bimeng.ui.UIHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class BimmoApplication extends Application implements Thread.UncaughtExceptionHandler {
    private static BimmoApplication application;

    private Controller controller;

    public static BimmoApplication getApplication() {
        return application;
    }

    private UIHandler uiHandler;

    public void setUIHandler(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    /**
     * 由哪个模块儿进入到支付的,如："商城"、"充值"
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        EMChat.getInstance().init(this);// 环信聊天初始化
        initImageLoader(getApplicationContext());// imageloader初始化
        removeTempFromPref();// 相册初始化
        PushManager.getInstance().initialize(this.getApplicationContext());

    }

    public static void initImageLoader(Context context) {
        // 缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的URI名称用MD5
                                                                        // 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You
                                                                               // can
                                                                               // pass
                                                                               // your
                                                                               // own
                                                                               // memory
                                                                               // cache
                                                                               // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 由原先的discCache -> diskCache
                .diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
                                                                                        // (5
                                                                                        // s),
                                                                                        // readTimeout
                                                                                        // (30
                                                                                        // s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        // 全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }

    @Override
    public void onLowMemory() {
        Log.d(loggerName, "Recieved OnLow Memory ");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        Log.d(loggerName, "Calling ondestroy on control application");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.d("PolyError", "", ex);
        Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public String getInstallPath() {
        return application.getApplicationContext().getFilesDir().getAbsolutePath();
    }

    private static final String loggerName = "BimmoApplication";

}
