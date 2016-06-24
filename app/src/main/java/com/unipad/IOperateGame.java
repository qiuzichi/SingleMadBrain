package com.unipad;

/**
 * Created by gongkan on 2016/6/23.
 */
public interface IOperateGame {
    void initDataFinished();
    void downloadingQuestion();
    void startGame();

    void pauseGame();

    void reStartGame();
    void finishGame();
}
