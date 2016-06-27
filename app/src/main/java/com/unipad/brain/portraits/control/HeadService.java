package com.unipad.brain.portraits.control;


import android.text.TextUtils;
import android.util.Log;

import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.portraits.bean.Person;
import com.unipad.utils.LogUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Deflater;

/**
 * Created by gongkan on 2016/4/11.
 */
public class HeadService extends AbsBaseGameService{
    /**
     * 0为记忆模式，1为答题回忆模式
     */
    public int mode = 0;

    public ArrayList<Person> data = new ArrayList<>();

    private String headResourse = "assets://portraits/";//file:///android_asset/portraits/
    /**
     * @return
     */
    @Override
    public boolean init() {
        //data = DbUtils.findAllPerson();
       /** List list = DbUtils.findAllPerson();
        if (null != list) {
            data.addAll(list);
        }
        if (data == null || data.size() == 0) {
            DbManager db = x.getDb(AppContext.instance().getDaoConfig());
            try {
                Log.e("", "no data in dataBase");
                Person person1 = new Person(1,"爱德华", "亚当", headResourse + "1.png");
                Person person2 = new Person(2,"莎莉", "丝特", headResourse + "2.png");
                Person person3 = new Person(3,"多米", "尼克", headResourse + "3.png");
                Person person4 = new Person(4,"奥蒂莉亚", "奥特", headResourse + "4.png");
                Person person5 = new Person(5,"弗雷德", "力克", headResourse + "5.png");
                Person person6 = new Person(6,"温妮", "费德", headResourse + "6.png");
                Person person7 = new Person(7,"邓普斯", "盖尔", headResourse + "7.png");
                Person person8 = new Person(8,"丹尼斯", "希腊", headResourse + "8.png");
                Person person9 = new Person(9,"维吉", "尼亚", headResourse + "9.png");
                Person person10 = new Person(10,"埃尔罗伊", "拉丁", headResourse + "10.png");
                Person person11 = new Person(11,"杜", "宇麒", headResourse + "11.png");
                Person person12 = new Person(12,"亚杜", "尼斯", headResourse + "12.png");
                if (data == null) {
                    data = new ArrayList<Person>();
                }
                data.add(person1);
                data.add(person2);
                data.add(person3);
                data.add(person4);
                data.add(person5);
                data.add(person6);
                data.add(person7);
                data.add(person8);
                data.add(person9);
                data.add(person10);
                data.add(person11);
                data.add(person12);
                db.save(data);
            } catch (DbException e) {

            }
        }
        shuffData();
        */
        return true;
    }

    /**
     * @描述 得到人名头像的计分 分数
     * @param persons 选手所填答案的集合与试题集合
     * @param nameScore  名字正确所得分数
     * @param surScore   姓正确所得分数
     * @param  repeatScore 重复出现同样的答案所扣除的分数
     * @return  下标【0】 为总得分 下标【1】 为犯错次数
     */
    public int[] getPortraitsScor(ArrayList<Person> persons,float nameScore,float surScore,float  repeatScore){
        int mistakeNumber = 0; // 犯错次数
        float score = 0; // 总得分
        final int  repeatLength = 2; // 如有答案重复出现超过两次 错一个姓或者名扣分
          //////---- 先计算出总得分
        if(null == persons)
            return new int[]{0,0};
        //选手所答姓的集合
        String[] surs = new String[persons.size()];
        // 选手所答名的集合
        String[] names = new String[persons.size()];
        for(int i = 0; i < persons.size(); i ++ ){
            Person person = persons.get(i);
            // 对比试题与答题的姓
            if(!TextUtils.isEmpty(person.getAnswerFirstName())){
                if(person.getFirstName().equals(person.getAnswerFirstName())){
                    // 累加分
                    score = score + surScore;
                }
                // 添加选手的姓进入数组，去除选手未答的姓
                surs[i] = person.getAnswerFirstName();
            } else {
                // 不计分
            }
            // 对比试题与答题名字
            if(!TextUtils.isEmpty(person.getAnswerLastName())){
                // 累加分
                if(person.getLastName().equals(person.getAnswerLastName())){
                    // 累加分
                    score = score + nameScore;
                }
                // 添加选手的名进入数组，去除选手未答的名
                names[i] = person.getAnswerLastName();
            } else {
                // 不计分
            }
        }
        // 得到姓 扣除分数
        float surDeduct = getRepeatName(surs,repeatLength,repeatScore);
        //得到名 扣除分数
        float nameDeduct = getRepeatName(names,repeatLength,repeatScore);
        Log.e(this.getClass().getSimpleName(),"姓扣" + surDeduct +"\t" + "名扣" + nameDeduct + "\t总得分" + score);
        // 得到最后得分
        BigDecimal bigDecimal =  new BigDecimal(score-(surDeduct + nameDeduct)).setScale(0, BigDecimal.ROUND_HALF_UP);
        //int lastScore = bigDecimal.
        Integer lastScore = bigDecimal.intValue();
        // 得到姓加名的错误次数
        mistakeNumber = getErrorNumber(surs,repeatLength) + getErrorNumber(names,repeatLength);

        return new int[]{lastScore,mistakeNumber};
    }


    /**
     * @描述： 返回姓或名所扣分数
     * @param names 姓或者名的数组集合
     * @param repeatLength 重复出现次数的界限
     * @param repeatScore  重复出现次数超出界限所扣分数
     * @return 返回扣分情况
     */
    private float getRepeatName(String[] names,int repeatLength,float repeatScore){
        int errorNumber = getErrorNumber(names,repeatLength);
        float contScore = errorNumber * repeatScore;
        return contScore;
    }

    /**
     * @描述： 得到错误次数（出现相同姓或者名超过既定界限扣除相应分数）
     * @param names 姓或者名数组
     * @param repeatLength 既定界限
     * @return
     */
    private int getErrorNumber(String[] names,int repeatLength){
        int errorNumber = 0; // 错误次数
        if(null == names)
            return errorNumber;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String str : names) {
            if(TextUtils.isEmpty(str))
                continue;
            Integer num = map.get(str);
            num = null == num ? 1 : num + 1;
            map.put(str, num);
        }
        Set set = map.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()){
            java.util.Map.Entry <String, Integer> entry = (Map.Entry<String, Integer>)it.next();
            //Log.e(this.getClass().getSimpleName(),"姓或者名" + entry.getKey() + " 出现次数 " + entry.getValue());
            //  -- 当一个名字出现超过既定次数，则判定错误一次
            if(entry.getValue() >  repeatLength){
                errorNumber ++;
            }
        }
        return errorNumber;
    }


    public void shuffData() {
        Collections.shuffle(data);
    }


    @Override
    public void clear() {
        data.clear();
        mode = 0;
    }

    @Override
    public void parseData(String data) {
        LogUtil.e("",data);
        super.parseData(data);
        String [] persData = data.split(",");
        for (int i = 0; i <persData.length ; i++) {
            Person person;
            if (this.data.size() < i+1) {
                person = new Person();
                String[] detail = persData[i].split("\\^");
                person.setTag(detail[0]);
                person.setContent(detail[1]);
                person.setFirstName(detail[2]);
                person.setLastName(detail[3]);
                this.data.add(person);
            } else {
                person = this.data.get(i);
                String[] detail = persData[i].split("\\^");
                person.setTag(detail[0]);
                person.setContent(detail[1]);
                person.setFirstName(detail[2]);
                person.setLastName(detail[3]);
            }


        }

        if (IsALlAready()) {
            initDataFinished();
        }
    }

    @Override
    public void initResourse(String soursePath) {
        String dir  = soursePath.substring(0, soursePath.lastIndexOf('.'));
        File fiel = new File(dir);
        if (fiel.exists() && fiel.isDirectory()) {

             String[] fileList = fiel.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    LogUtil.e("","path = "+dir.getAbsolutePath()+",fileName="+filename);
                    return filename.endsWith("jpg") || filename.endsWith("png");
                }
            });

            for (int i = 0; i < fileList.length; i++) {
                Person person;
               if (this.data.size() < i+1) {
                   person = new Person();
                   person.setHeadPortraitPath(dir+File.separator+fileList[i]);
                   this.data.add(person);
               }else {

                   person = data.get(i);
                   person.setHeadPortraitPath(dir+File.separator+fileList[i]);
               }

            }
        }
        setIsInitResourseAready(true);
        if (IsALlAready()) {
            initDataFinished();
        }
    }

    @Override
    public double getScore() {
        return  getPortraitsScor(data,1.0f,1.0f,0.5f)[0];
    }

    @Override
    public String getAnswerData() {
        return data.toString();
    }


}
