package fingermelody.base;

import android.app.Application;
import android.content.Context;


import fingermelody.util.CrashHandler;

/**
 * Created by FingerMelody on 2018/1/3.
 */

public class BaseApp extends Application {
    private static BaseApp instance;

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initCrash();
        initTool();

    }


    /**
     * 全局捕获异常
     */
    private void initCrash() {
        instance = this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    /**
     * 初始化工具
     */
    private void initTool() {
    }
}
