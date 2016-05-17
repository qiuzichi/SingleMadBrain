package com.unipad.brain.portraits.control;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.ICoreService;
import com.unipad.brain.portraits.bean.Person;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gongkan on 2016/4/11.
 */
public class HeadService implements ICoreService {
    /**
     * 0为记忆模式，1为答题回忆模式
     */
    public int mode = 0;

    public ArrayList<Person> data;

    private String headResourse = "assets://portraits/";//file:///android_asset/portraits/

    /**
     * @return
     */
    @Override
    public boolean init() {
        //data = DbUtils.findAllPerson();
        data = (ArrayList)DbUtils.findAllPerson();
        if (data == null || data.size() == 0) {
            DbManager db = x.getDb(AppContext.instance().getDaoConfig());
            try {
                Log.e("", "no data in dataBase");
                Person person1 = new Person("爱德华", "亚当", headResourse + "1.png");
                Person person2 = new Person("莎莉", "丝特", headResourse + "2.png");
                Person person3 = new Person("多米", "尼克", headResourse + "3.png");
                Person person4 = new Person("奥蒂莉亚", "奥特", headResourse + "4.png");
                Person person5 = new Person("弗雷德", "力克", headResourse + "5.png");
                if (data == null) {
                    data = new ArrayList<Person>();
                }
                data.add(person1);
                data.add(person2);
                data.add(person3);
                data.add(person4);
                data.add(person5);
                db.saveOrUpdate(person1);
                db.saveOrUpdate(person2);
                db.saveOrUpdate(person3);
                db.saveOrUpdate(person4);
                db.saveOrUpdate(person5);
            } catch (DbException e) {

            }
            //DbUtils.addPerson(new Person("莎莉", "丝特", headResourse + "2.png"));
            //DbUtils.addPerson(new Person("多米", "尼克", headResourse + "3.png"));
            //DbUtils.addPerson(new Person("奥蒂莉亚", "奥特", headResourse + "4.png"));
            //DbUtils.addPerson(new Person("弗雷德", "力克", headResourse + "5.png"));
            shuffData();
        }
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
}
