package com.unipad.brain.portraits.control;

import android.content.Context;
import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.portraits.bean.Person;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by gongkan on 2016/4/12.
 */
public class DbUtils {


    public Person findPerson(String firstName,String lastName ){

        DbManager db = x.getDb(AppContext.instance().getDaoConfig());
        try {
            Person p = db.selector(Person.class)
                    .where("firstName", "=", firstName)
                    .and("lastName", "=", lastName)
                    .findFirst();
            /*long count = db.selector(Person.class) //查总数
                    .where("name", "LIKE", "w%")
                    .and("age", ">", 32)
                    .count();  */
            /*List<Person> testList = db.selector(Person.class) //between
                    .where("id", "between", new String[]{"1", "5"})
                    .findAll();*/
            return p;

        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void addPerson(Person person){

        DbManager db = x.getDb(AppContext.instance().getDaoConfig());
        try {//new Object[]{name,age}
            db.save(person);

        } catch (DbException e) {
            Log.e("","save error");
            e.printStackTrace();
        }


    }

    public  void deletePerson(){



    }

    public  static List<Person> findAllPerson(){
        DbManager db = x.getDb(AppContext.instance().getDaoConfig());
        try {
            List<Person> data = db.selector(Person.class).findAll();

            return data;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;

    }
}
