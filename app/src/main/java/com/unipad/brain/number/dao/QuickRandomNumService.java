package com.unipad.brain.number.dao;

/**
 * Created by gongkan on 2016/7/18.
 */
public class QuickRandomNumService extends  NumService {
    /**
     * 1、完全写满并正确的一行得 40 分。
     * 2、完全写满但有一个错处（或漏空）的一行得 20 分。
     * 3、完全写满但超过两个及以上错处（或漏空）的一行得 0 分。
     * 4、空白行数不会扣分。
     * 5、对于最后一行：如最后的一行没有完成（例：只有写上 29 个 数字），且所有数字皆为正确，其所得分数为该行作答数字的数目（即 29 分于该例）。 如最后的一行没有完成，但有一个错处（或中间漏空），其所得 分数为该行作答数字的数目的一半。 如有小数点则四舍五入。例：如作答了 29 个数字但有一错处， 分数将除 2，即 29/2 = 14.5，分数调高至 15 分。 如最后一行有二个及以上的错处（或中间漏空），则将以 0 分计。 该项目成绩如有相同的最高得分，则取另外一轮得分较高的一 位。如另外一轮的得分皆为相等，裁判将参考每位选手的最佳那轮的
     * 额外行数（即作答了但得 0 分的行数）。每个正确作答的数字将获 1 分决定分，决定分较高者胜。
     * @return
     */
    @Override
    public double getScore() {
        return super.getNumberScore(40f,20f,2f,1f,1)[0];
    }

}
