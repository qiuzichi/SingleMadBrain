package com.unipad.brain;

import com.unipad.ICoreService;
import com.unipad.IOperateGame;
import com.unipad.observer.GlobleObserService;

/**
 * Created by gongkan on 2016/6/22.
 */
public abstract  class AbsBaseGameService extends GlobleObserService implements ICoreService.IGameHand {

    protected boolean isInitQuestionAready;

    private IOperateGame operateGame;

    public boolean isInitResourseAready() {
        return isInitResourseAready;
    }

    public void setIsInitResourseAready(boolean isInitResourseAready) {
        this.isInitResourseAready = isInitResourseAready;
    }

    public boolean isInitQuestionAready() {
        return isInitQuestionAready;
    }

    public void setIsInitQuestionAready(boolean isInitQuestionAready) {
        this.isInitQuestionAready = isInitQuestionAready;
    }

    protected boolean isInitResourseAready;

    public int mode;
    @Override
    public void parseData(String data) {
        isInitQuestionAready = true;
    }

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean IsALlAready() {
        return isInitQuestionAready && isInitResourseAready;
    }

    @Override
    public void initResourse(String soursePath) {

    }

    @Override
    public void pauseGame() {
        if (operateGame != null) {
            operateGame.pauseGame();
        }
    }

    @Override
    public void startGame() {
        if (operateGame != null) {
            operateGame.startGame();
        }
    }

    @Override
    public void reStartGame() {
        if (operateGame != null) {
            operateGame.reStartGame();
        }
    }

    public void setOperateGame(IOperateGame operateGame) {
        this.operateGame = operateGame;
    }

    @Override
    public void initDataFinished() {
        if (operateGame != null) {
            initDataFinished();
        }
    }
}
