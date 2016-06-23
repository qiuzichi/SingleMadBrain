package com.unipad.brain.number.bean;

import android.util.SparseArray;

import java.util.List;

/**
 * 二进制数字项目实体
 */
public class RandomNumberEntity {
    /**
     * 行数
     */
    public static int lines = 25;
    /**
     * 列数
     */
    public static int rows = 40;
    /**
     * 数字总数
     */
    public static int totalNumbers = lines * rows;
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
