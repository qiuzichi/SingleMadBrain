package com.unipad.brain.number.bean;

import java.util.List;

import android.util.SparseArray;

/**
 * 二进制数字项目实体
 */
public class BinaryNumberEntity {
    /**
     * 文字0
     */
    public static String TEXT_ZERO = "0".trim();
    /**
     * 文字1
     */
    public static String TEXT_ONE = "1".trim();
    /**
     * 发消息开启线程准备加载界面
     */
    public static final int MSG_OPEN_THREAD = 105;
    /**
     * 发消息更新界面
     */
    public static final int MSG_REFRESH_UI = 106;
    /**
     * 行数
     */
    public static int lines = 25;
    /**
     * 列数
     */
    public static int rows = 30;
    /**
     * 以行号为key值保存题目的每一行数字的出现顺序
     */
    public static SparseArray<List<String>> lineNumbers = new SparseArray<>();
    /**
     * 评分：完全写满并正确的一行得30分
     */
    public static final int LINE_FULL_SCORE = 30;
    /**
     * 评分：完全写满但有一个错处（或漏空）的一行得15分
     */
    public static final int LINE_ONE_ERROR_SCORE = 15;
}
