package com.tendcloud.dynamictddemo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class LoadUtil {

    private DexClassLoader mDexClassLoader;

    private final String mOriginPath = "td_dex.jar";//assets的原始文件
    private final String mOutPath = "/td_dex.jar";//file目录下的文件

    private Class<?> mTgcClass;
    private static LoadUtil mInstance;
    private Method initMethod,onEventMethod;
    private Context mContext;

    public LoadUtil(Context context){
        mContext = context;
    }

    public static LoadUtil getInstance(Context context){
        if (mInstance == null) {
            synchronized (LoadUtil.class) {
                if (mInstance == null) {
                    mInstance = new LoadUtil(context);
                }
            }
        }
        return mInstance;
    }

    @SuppressLint("NewApi") public boolean init() {
        try {
            descryptFile(mContext);
            String destFilePath = mContext.getFilesDir().getAbsolutePath() + mOutPath;
            File opFile = new File(destFilePath);
            Log.e("TD", opFile.getAbsolutePath());
            if (!opFile.exists()) {
                return false;
            }
            //首先获取实例
            mDexClassLoader = new DexClassLoader(opFile.toString()
                    , mContext.getFilesDir().getAbsolutePath()
                    , null
                    , ClassLoader.getSystemClassLoader().getParent());
            //加载其中的类
            mTgcClass = mDexClassLoader.loadClass("com.tendcloud.tenddata.TCAgent");
            //initMethod = mTgcClass.getMethod("init",Context.class);
            Object obj = mTgcClass.newInstance();
            Class[] params = new Class[3];
            params[0] = Context.class;
            params[1] = String.class;
            params[2] = String.class;

            initMethod = mTgcClass.getMethod("init",params);
            initMethod.invoke(obj,mContext,"4735F4BF9074472FB3AB019786056828", "honghualong_test");

        //自定义事件
            Class[] params2 = new Class[2];
            params2[0] = Context.class;
            params2[1] = String.class;

            onEventMethod = mTgcClass.getMethod("onEvent",params2);
            onEventMethod.invoke(obj,mContext,"987654321");




        } catch (Exception e) {
            Log.e("TD", e.toString());
            return false;
        }
        return true;
    }


    private boolean descryptFile(Context context) throws IOException {
        File destFile = new File(context.getFilesDir().getAbsolutePath() + mOutPath);
        if (destFile.exists()) {
            long s = 0;
            FileInputStream fis = null;
            fis = new FileInputStream(destFile);
            s = fis.available();
            if (s > 20) {
                return true;
            }
        }
        InputStream assetsFileInputStream = null;
        try {
            assetsFileInputStream = context.getAssets().open(mOriginPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(destFile);
        int readNum = 0;
        while((readNum = assetsFileInputStream.read()) != -1){
            fos.write(readNum);
        }
        fos.close();
        assetsFileInputStream.close();
        return true;
    }


}
