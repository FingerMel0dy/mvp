package fingermelody.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import fingermelody.base.BaseApp;

/**
 * Created by FingerMelody on 2018/1/3.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    // 程序的Context对象
    private Application application;
    private static CrashHandler instance;
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context context) {
        application = (Application) context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 核心方法，当程序crash 会回调此方法， Throwable中存放这错误日志
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        mDefaultHandler.uncaughtException(thread, throwable);
        throwable.printStackTrace();
        //  将程序杀掉
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
