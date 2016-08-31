package com.unipad.singlebrain.number.dao;

/**
 * Created by gongkan on 2016/7/4.
 */
public class BinaryService extends NumService{

    /**
     *
     *  @描述：  二进制记分方法
     *  1、完全写满并正确的一行得 30 分。
     *  2、完全写满但有一个错处（或漏空）的一行得 15 分。
     *  3、完全写满但两个错处（或漏空）及以上的一行得 0 分。
     *  4、空白行数不会倒扣分。
     *  5、对于最后一行：如最后的一行没有完成（例：只有写上 20 个 数字），且所有数字皆为正确，其所得分数为该行作答数字的数目。
     *  6、如最后的一行没有完成，但有一个错处（或中间漏空），其 所得分数为该行作答数字的数目的一半（如有小数点，采取四舍五入 法）。
     *  7、如有相同的分数，将在选手已作答而没有得分的行中，以每 个正确作答的数字为 1 分进行计分决定，决定分较高者获胜
     * @return
     */
    @Override
    public double getScore() {
        return getNumberScore(30f,15f,2f,1f,1)[0];
    }
}
