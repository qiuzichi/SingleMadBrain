package com.unipad;

import android.app.Activity;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.baidu.location.BDLocation;
import com.unipad.singlebrain.absPic.dao.FigureService;
import com.unipad.singlebrain.home.dao.HomeGameHandService;
import com.unipad.singlebrain.home.dao.NewsService;
import com.unipad.singlebrain.location.dao.LocationService;
import com.unipad.singlebrain.longPoker.dao.LongPokerService;
import com.unipad.singlebrain.number.dao.BinaryService;
import com.unipad.singlebrain.number.dao.ListenToWriteNumService;
import com.unipad.singlebrain.number.dao.LongNumService;
import com.unipad.singlebrain.number.dao.QuickRandomNumService;
import com.unipad.singlebrain.personal.dao.PersonCenterService;
import com.unipad.singlebrain.portraits.control.HeadService;
import com.unipad.singlebrain.quickPoker.dao.QuickCardService;
import com.unipad.singlebrain.virtual.dao.VirtualTimeService;
import com.unipad.singlebrain.words.dao.WordsService;
import com.unipad.common.Constant;
import com.unipad.observer.GlobleHandle;

import org.xutils.DbManager;
import org.xutils.image.ImageOptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 整个应用的全局上下文
 * <p/>
 * Created by gongkan on 2016/3/11
 *
 */
public class AppContext {

    /**
     * The logging tag used by this class with com.huawei.hid.util.log.HLog.
     */
    protected static final String TAG = "AppContext";

    private static volatile AppContext instance = null;

    public UserDetailEntity loginUser;
    /**
     * 所有service的集合
     */

    public HashMap<String, ICoreService> serviceList = new HashMap<String, ICoreService>();


    public GlobleHandle globleHandle;

    /**
     * 当前升级包的版本
     */
    public String updateVersion = "";


    /**
     * 是否该类已经实例化完成
     */
    public static boolean isInstanceed = false;

    /**
     * 屏的像素宽和高
     */

    public int[] disPlay = {1280, 800};

    /**
     * 用户对当前地址信息
     * 请查找相关API http://developer.baidu.com/map/loc_refer/com/baidu/location/BDLocation.html
     * 使用时应当判断对象是否为空
     */
    public BDLocation location;

    private DbManager.DaoConfig daoConfig;

    public ImageOptions getImageOptions() {
        //ImageOptions ImageOptions = new ImageOptions.Builder().set
        return null;
    }

    ;

    private AppContext() {
    }

    public boolean isDebug = true;

    private void init() {
        globleHandle = new GlobleHandle();
        daoConfig = new DbManager.DaoConfig()
                .setDbName("unistrong_db")//创建数据库的名称
                .setDbVersion(0)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);http://localhost:8091/crazybrain-monitor-mng/auth/login_admin?r=/system/index
                        // ...
                    }
                });//数据库更新操作

    }

    /**
     * 获取AppContext的单例
     *
     * @return AppContext的实例
     */
    public static AppContext instance() {
        // 如果为空或是没有实例化完成，则等待
        if (instance == null || !isInstanceed) {
            synchronized (AppContext.class) {
                if (instance == null) {
                    instance = new AppContext();
                    instance.init();
                    isInstanceed = true;
                }
            }
        }

        return instance;
    }

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    public ICoreService getService(String key) {
        ICoreService service = null;
        if (!TextUtils.isEmpty(key)) {
            service = serviceList.get(key);
            if (service != null) {

            } else if (key.equals(Constant.HEADSERVICE)) {
                service = new HeadService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.ABS_FIGURE)) {
                service = new FigureService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.WORDS_SERVICE)) {
                service = new WordsService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.BINARYNUMSERVICE)) {
                service = new BinaryService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.PERSONCENTER)) {
                service = new PersonCenterService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.NEWS_SERVICE)) {
                service = new NewsService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.HOME_GAME_HAND_SERVICE)) {
                service = new HomeGameHandService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.LOCATION_SERVICE)) {
                service = new LocationService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.LONG_SERVICE)) {
                service = new LongNumService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.VIRTUAL_TIME_SERVICE)) {
                service = new VirtualTimeService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.QUICK_POKER_SERVICE)) {
                service = new QuickCardService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.LONG_POKER_SERVICE)) {
                service = new LongPokerService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.QUICK_RANDOM_NUM_SERVICE)) {
                service = new QuickRandomNumService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.LISTENTOWRITE_NUM_SERVICE)) {
                service = new ListenToWriteNumService();
                service.init();
                serviceList.put(key, service);
            }
        }
        return service;
    }

    public ICoreService getGameServiceByProject(String projectId) {
        String key = null;
        if (Constant.GAME_PORTRAITS.endsWith(projectId)) {
            key = Constant.HEADSERVICE;
        } else if (Constant.GAME_BINARY_NUM.endsWith(projectId)) {
            key = Constant.BINARYNUMSERVICE;
        } else if (Constant.GAME_LONG_NUM.endsWith(projectId)) {
            key = Constant.LONG_SERVICE;
        } else if (Constant.GAME_ABS_PICTURE.endsWith(projectId)) {
            key = Constant.ABS_FIGURE;
        } else if (Constant.GAME_RANDOM_NUM.endsWith(projectId)) {
            key = Constant.QUICK_RANDOM_NUM_SERVICE;
        } else if (Constant.GAME_VIRTUAL_DATE.endsWith(projectId)) {
            key = Constant.VIRTUAL_TIME_SERVICE;
        } else if (Constant.GAME_LONG_POCKER.endsWith(projectId)) {
            key = Constant.LONG_POKER_SERVICE;
        } else if (Constant.GAME_RANDOM_WORDS.endsWith(projectId)) {
            key = Constant.WORDS_SERVICE;
        } else if (Constant.GAME_LISTON_AND_MEMORY_WORDS.endsWith(projectId)) {
            key = Constant.LISTENTOWRITE_NUM_SERVICE;
        } else if (Constant.GAME_QUICKIY_POCKER.endsWith(projectId)) {
            key = Constant.QUICK_POKER_SERVICE;
        }

        return getService(key);
    }

    /**
     * 初始化屏幕的像素参数
     *
     * @param activity
     */
    public void initDisplay(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        disPlay[0] = metric.widthPixels; // 屏幕宽度（像素）
        disPlay[1] = metric.heightPixels; // 屏幕高度（像素）
    }

    public boolean initServiceState() {


        return true;
    }

    public void clearService(String key) {
        if (!TextUtils.isEmpty(key)) {
            ICoreService service = serviceList.get(key);
            if (service != null) {
                service.clear();
                serviceList.remove(key);
            }
        }
    }

    public void clearService(ICoreService service) {
        Iterator<Map.Entry<String, ICoreService>> it = serviceList.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, ICoreService> entry=it.next();
            String key=entry.getKey();
            ICoreService  iCoreService = entry.getValue();
            if (service == iCoreService){
                service.clear();
                serviceList.remove(key);
                break;
            }
        }
    }

    /**
     * 销毁该全局处理器
     */
    public void destory() {
        if (null != instance) {
            Set keys = serviceList.keySet();
            if (keys != null) {
                Iterator iterator = keys.iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    ICoreService service = serviceList.get(key);
                    service.clear();
                }
            }
            instance = null;
        }
        isInstanceed = false;
    }
}
