package com.unipad.brain.words.dao;

import com.unipad.ICoreService;
import com.unipad.brain.words.bean.WordEntity;

import java.util.ArrayList;

/**
 * Created by gongkan on 2016/5/30.
 */
public class WordsService implements ICoreService {
    public static int VOLUM = 5;//每行词语的个数
    public ArrayList<WordEntity> words = new ArrayList<>();
    private String [] wordArr = new String[]{"地毯","公里","唇","双生儿","狗窝","剃刀","橙","允许","香水","发刷","河马","战舰","跑步者","坚果","游艇","风格","省略","意思","小猫","羽毛",
         "报纸","知道","鲍鱼","套头毛衣","恐龙","伞","梯子","退休","石英","衣领","项链","吸收","车库","誓约","格子饼","拉链","头痛","虹膜","失业","雨雪",
         "斑马","手表","飞机","教练","文具","坐浴盆","工作","羊毛","组织","录像","苹果","雨","须","婴儿","骑师","鼓槌","鼓","编辑","资格","行政人员",
         "报纸","知道","鲍鱼","套头毛衣","恐龙","伞","梯子","退休","石英","衣领","项链","吸收","车库","誓约","格子饼","拉链","头痛","虹膜","失业","雨雪",
         "开始","维他命","股东","面包店","大自然","股东","梯子","退休","石英","衣领","项链","吸收","车库","誓约","格子饼","拉链","头痛","虹膜","失业","雨雪",};
    @Override
    public boolean init() {
        for (int i = 1;i<wordArr.length;i++) {
            words.add(new WordEntity(20*(i%5)+(i/5)+1,wordArr[i]));
        }
        return true;
    }

    @Override
    public void clear() {

    }
}
