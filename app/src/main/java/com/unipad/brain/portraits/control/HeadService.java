package com.unipad.brain.portraits.control;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.ICoreService;
import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.portraits.bean.Person;
import com.unipad.utils.LogUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private INotifyInitData dataInitNotify;
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

        if (IsALlAready() && dataInitNotify != null) {
            dataInitNotify.initDataFinished();
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
                   person.setHeadPortraitPath(fileList[i]);
                   this.data.add(person);
               }else {

                   person = data.get(i);
                   person.setHeadPortraitPath(dir+File.separator+fileList[i]);
               }

            }
        }
        setIsInitResourseAready(true);
        if (IsALlAready() && dataInitNotify != null) {
            dataInitNotify.initDataFinished();
        }
    }

    public void setDataInitNotify(INotifyInitData dataInitNotify) {
        this.dataInitNotify = dataInitNotify;
    }

    public interface INotifyInitData{
        void initDataFinished();
    }
}
