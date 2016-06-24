package com.unipad.common;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.unipad.AppContext;
import com.unipad.IOperateGame;
import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.App;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.absPic.view.AbsFigureFragment;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.brain.home.dao.HomeGameHandService;
import com.unipad.brain.number.NumberRightFragment;
import com.unipad.brain.portraits.view.HeadPortraitFragment;
import com.unipad.brain.virtual.VirtualRightFragment;
import com.unipad.brain.words.view.WordRightFragment;
import com.unipad.common.widget.HIDDialog;
import com.unipad.io.mina.SocketThreadManager;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.LogUtil;
import com.unipad.utils.ToastUtil;

/**
 * Created by Wbj on 2016/4/7.
 */
public class CommonActivity extends BasicActivity implements IDataObserver,IOperateGame{
    private CommonFragment mCommonFragment = new CommonFragment();
    private AbsBaseGameService service;
    public String getMatchId() {
        return matchId;
    }
    private Handler handler;
    private BasicCommonFragment gameFragment;
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    private String matchId;

    private String projectId;

    long startTime;

    private static final int STRAT_GAME = 0;
    private static final int DOWNLOAD_QUESTION = 1;
    private static final int PAUSE_GAME = 2;
    private static final int RESTAT_GAME = 3;
    private static final int FINISH_GAME = 4;
    private static final int INIT_DATA_FINISH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_aty);
        startTime = System.currentTimeMillis();
        matchId = getIntent().getStringExtra("matchId");
        projectId = getIntent().getStringExtra("projectId");
        service = (AbsBaseGameService) AppContext.instance().getGameServiceByProject(projectId);
        service.setOperateGame(this);
        handler  = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                int what = msg.what;
                switch (msg.what) {
                    case STRAT_GAME:
                        HIDDialog.dismissAll();
                        gameFragment.startGame();
                        mCommonFragment.startGame();
                        break;
                    case DOWNLOAD_QUESTION:
                        ToastUtil.createTipDialog(CommonActivity.this, Constant.SHOW_GAME_PAUSE, "下载试题中").show();
                        break;

                    case PAUSE_GAME:
                        ToastUtil.createTipDialog(CommonActivity.this, Constant.SHOW_GAME_PAUSE, "比赛暂停").show();
                        gameFragment.pauseGame();
                        mCommonFragment.pauseGame();
                        break;
                    case RESTAT_GAME:
                        HIDDialog.dismissAll();
                        gameFragment.reStartGame();
                        mCommonFragment.reStartGame();
                        break;
                    case FINISH_GAME:
                        gameFragment.finishGame();
                        mCommonFragment.finishGame();
                        break;
                    case INIT_DATA_FINISH:
                        gameFragment.initDataFinished();
                        mCommonFragment.initDataFinished();
                        HIDDialog.dismissAll();
                        ToastUtil.createTipDialog(CommonActivity.this, Constant.SHOW_GAME_PAUSE, "等待裁判准备开始").show();
                        break;
                }
            }
        };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SocketThreadManager.sharedInstance().signOK(matchId);
                        SocketThreadManager.sharedInstance().setService(service);
                        ((HomeGameHandService) AppContext.instance().getService(Constant.HOME_GAME_HAND_SERVICE)).getRule(matchId);
                    }
                }).start();


    }

    public AbsBaseGameService getService() {
        return service;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime = startTime- System.currentTimeMillis();
        LogUtil.e("-------","time = "+startTime);
    }
    @Override
    public void initData() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.common_lfg_container, mCommonFragment);
        String competeItem = Constant.getProjectName(projectId);
        if (competeItem.equals(getString(R.string.project_2)) || competeItem.equals(getString(R.string.project_3))
                || competeItem.equals(getString(R.string.project_5)) || competeItem.equals(getString(R.string.project_9))) {
            gameFragment = new NumberRightFragment();
        } else if (competeItem.equals(getString(R.string.project_6))) {
            gameFragment = new VirtualRightFragment();
        } else if (competeItem.equals(getString(R.string.project_8))) {
            gameFragment = new WordRightFragment();
        } else if (competeItem.equals(getString(R.string.project_1))) {
            gameFragment = new HeadPortraitFragment();

        } else if (competeItem.equals(getString(R.string.project_4))) {
            gameFragment = new AbsFigureFragment();
        }
        fragmentTransaction.replace(R.id.common_rfg_container, gameFragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SocketThreadManager.sharedInstance().releaseInstance();
    }

    public CommonFragment getCommonFragment() {
        return mCommonFragment;
    }

    @Override
    public void update(int key, Object o) {

    }

    @Override
    public void initDataFinished() {
        SocketThreadManager.sharedInstance().downLoadQuestionOK(matchId,100);
        handler.sendEmptyMessage(INIT_DATA_FINISH);
    }

    @Override
    public void downloadingQuestion() {
        handler.sendEmptyMessage(DOWNLOAD_QUESTION);

    }

    @Override
    public void startGame() {
        handler.sendEmptyMessage(STRAT_GAME);
    }

    @Override
    public void pauseGame() {
        handler.sendEmptyMessage(PAUSE_GAME);

    }

    @Override
    public void reStartGame() {
        handler.sendEmptyMessage(RESTAT_GAME);

    }

    @Override
    public void finishGame() {
        handler.sendEmptyMessage(FINISH_GAME);

    }
}
