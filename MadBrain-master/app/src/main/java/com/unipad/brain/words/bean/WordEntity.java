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
    private String word;


    private String answer;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        word = word;
    }


    public WordEntity(int number, String word) {
        this.number = number;
        word = word;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
