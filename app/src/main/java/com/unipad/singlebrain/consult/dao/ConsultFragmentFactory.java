package com.unipad.singlebrain.consult.dao;

import android.text.TextUtils;

import com.unipad.singlebrain.consult.entity.ConsultTab;
import com.unipad.singlebrain.home.MainBasicFragment;

/**
 * Created by 63 on 2016/6/20.
 */
public class ConsultFragmentFactory {

    private static ConsultFragmentFactory instance;

    public static ConsultFragmentFactory getInstance(){
        if(instance == null){
            instance = new ConsultFragmentFactory();
        }
        return instance;
    }

    public MainBasicFragment getFragment(ConsultTab consultTab){
        String name = null;
        switch (consultTab){
            case INTRODUCATION:
                name = "com.unipad.singlebrain.consult.view.IntroductionFragment";
                break;
            case OCCASIONS:
                name = "com.unipad.singlebrain.consult.view.OccasionsFragment";
                break;
            case HOTSPOT:
                name = "com.unipad.singlebrain.consult.view.HotspotFragment";
                break;
//            case SUBSCRIBE:
//                name = "com.unipad.brain.consult.view.SubscribeFragment";
//                break;
        }

        return getFragmentByClassName(name);
    }

    private MainBasicFragment getFragmentByClassName(String className){
        if(TextUtils.isEmpty(className))return null;
        try {
            Class fragmentClass = Class.forName(className);
           return (MainBasicFragment)fragmentClass.newInstance();
        }catch (Exception e){

        }
        return null;
    }
}
