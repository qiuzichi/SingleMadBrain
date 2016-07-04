package com.unipad.brain;

import com.unipad.ICoreService;
import com.unipad.IOperateGame;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.observer.GlobleObserService;

import java.util.Map;

/**
 * Created by gongkan on 2016/6/22.
 */
public abstract  class AbsBaseGameService extends GlobleObserService implements ICoreService.IGameHand {

    protected boolean isInitQuestionAready;

    private IOperateGame operateGame;

    public RuleGame rule;

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
        rule = null;
        mode = 0;
        isInitResourseAready = false;
        isInitQuestionAready = false;
        operateGame = null;
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
            operateGame.initDataFinished();
        }
    }
    public void finishGame(){
        if (operateGame != null) {
            operateGame.finishGame();
        }
    }
    public void downloadingQuestion(Map<String,String> data){
        if (operateGame != null) {
            operateGame.downloadingQuestion(data);
        }
    }

    public abstract double getScore();
    public abstract String getAnswerData();
}
