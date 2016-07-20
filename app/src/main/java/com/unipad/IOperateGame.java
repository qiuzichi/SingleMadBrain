package com.unipad;

import java.util.Map;

/**
 * Created by gongkan on 2016/6/23.
 */
public interface IOperateGame{
    void initDataFinished();
    void downloadingQuestion(Map<String,String> data);
    void startMemory();
    void startRememory();
    void pauseGame();

    void reStartGame();
    void finishGame();
}
