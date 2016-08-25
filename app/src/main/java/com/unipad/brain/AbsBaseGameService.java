package com.unipad.brain;

import com.unipad.ICoreService;
import com.unipad.IOperateGame;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.common.Constant;
import com.unipad.observer.GlobleObserService;

import java.util.Map;

/**
 * Created by gongkan on 2016/6/22.
 */
public abstract  class AbsBaseGameService extends GlobleObserService implements ICoreService.IGameHand {

    /**进入比赛，签到*/
    public static final int GO_IN_MATCH_SIGN = 1;

    /**进入比赛，试题下载完成*/
    public static final int GO_IN_MATCH_DOWNLOADED = 2;

    /**记忆阶段*/
    public static final int GO_IN_MATCH_START_MEMORY = 3;

    /**记忆结束，还没有开始回忆*/
    public static final int GO_IN_MATCH_END_MEMORY = 4;

    /**已经开始回忆*/
    public static final int GO_IN_MATCH_START_RE_MEMORY = 5;

    /**回忆结束*/
    public static final int GO_IN_MATCH_END_RE_MEMORY = 6;

    protected boolean isInitQuestionAready;

    private IOperateGame operateGame;

    public RuleGame rule;

    /**round是在startMemory中赋值，在这之前初始化的数据*/
    public int round = 1;

    public int allround = 1;

    public boolean isPause;

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

    public int state = GO_IN_MATCH_SIGN;
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
        state = GO_IN_MATCH_SIGN;
        round = 1;
        allround = 1;
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
        isPause = true;
        if (operateGame != null) {
            operateGame.pauseGame();
        }
    }


    @Override
    public void startMemory(int round) {
        this.round = round;
        if (operateGame != null) {
            operateGame.startMemory();
        }
        state = GO_IN_MATCH_START_MEMORY;
    }

    @Override
    public void starRememory() {
        if (operateGame != null) {
            operateGame.startRememory();
        }
        state = GO_IN_MATCH_START_RE_MEMORY;
    }

    @Override
    public void reStartGame() {
        isPause = false;
        if (operateGame != null) {
            operateGame.reStartGame();
        }
    }

    public void setOperateGame(IOperateGame operateGame) {
        this.operateGame = operateGame;
    }

    public void initDataFinished() {
        if (operateGame != null) {
            operateGame.initDataFinished();
        }
        state = GO_IN_MATCH_DOWNLOADED;
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
    public void downloadResource(String questionId){

    }

    public boolean isLastRound(){
        return round >= allround;
    }
    public abstract double getScore();
    public abstract String getAnswerData();
}
