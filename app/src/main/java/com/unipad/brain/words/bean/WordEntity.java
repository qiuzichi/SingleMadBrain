package com.unipad.brain.words.bean;

/**
 * Created by LiuPeng on 2016/4/12.
 */
public class WordEntity {


    /**
     * 序号
     */
    private int number;


    /**
     * 词语
     */
    private String Word;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }


    public WordEntity(int number, String word) {
        this.number = number;
        Word = word;
    }

}
