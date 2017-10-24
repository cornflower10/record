package com.record;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.record.dao.DaoMaster;
import com.record.dao.DaoSession;
import com.record.utils.ForegroundCallbacks;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 灌云县公安局 李秉键 on 2017/8/30.
 */

public class App extends Application {
    private static App INSTANCE;
    private boolean isLogin;
    private String sessionKey;
    private static DaoSession daoSession;
    public static App getInstance() {
        return INSTANCE;
    }
    private ForegroundCallbacks foregroundCallbacks ;

    public ForegroundCallbacks getForegroundCallbacks() {
        return foregroundCallbacks;
    }

    @Override
    public void onCreate() {
        INSTANCE = this;
        super.onCreate();
        foregroundCallbacks = ForegroundCallbacks.init(this);
        initDataBase();
        if(BuildConfig.debug){
            Stetho.initializeWithDefaults(this);
        }

        CrashReport.initCrashReport(getApplicationContext(), "acd7a7de92", BuildConfig.debug);
//        CrashHandler.getInstance().init(this);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }


    private void initDataBase() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "record.db");
        Database db = openHelper.getWritableDb();

        DaoMaster daoMaster  = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
