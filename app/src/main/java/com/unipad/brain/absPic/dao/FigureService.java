package com.unipad.brain.absPic.dao;

import android.content.Context;
import android.util.Log;

import com.unipad.ICoreService;
import com.unipad.brain.App;
import com.unipad.brain.absPic.bean.Figure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by gongkan on 2016/4/15.
 */
public class FigureService implements ICoreService {

    public ArrayList<Figure> allFigures = new ArrayList<>();
    private HashMap<Integer, ArrayList<Figure>> map = new HashMap<Integer, ArrayList<Figure>>();
    private String headResourse = "assets://absFigure/";
    private String path = "absFigure";
    private static final int VOLUM = 5;//每行抽象图形的个数
    public int mode = 0;//0记忆模式，1为答题模式，2对答案模式；

    @Override
    public boolean init() {
        getResourse(App.getContext());
        return true;
    }

    private void getResourse(Context context) {
        try {
            String[] fileNames = context.getAssets().list(path);
            ArrayList<String> randomFileNames = new ArrayList<>(Arrays.asList(fileNames));
            Collections.shuffle(randomFileNames);
            ArrayList<Figure> list = null;
            for (int i = 0; i < randomFileNames.size(); i++) {
                allFigures.add(new Figure(headResourse + randomFileNames.get(i), i % 5 +1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, ArrayList<Figure>> getMap() {
        return map;
    }

    public void shuffle() {
        ArrayList<Figure> temp = new ArrayList<>(allFigures);
        allFigures.clear();
        int j = 4;
        int size = temp.size();
        for (int i = 0; i < size; i++) {
            int min = 0;
            if (j < 0) {
                j = 4;
            }
            if (temp.size() < j) {
                j = temp.size();
            }
            int max = min + j;
            int index = (int) (min+Math.random()*(max-min+1));
            Figure figure = temp.get(index);
            temp.remove(index);
            allFigures.add(figure);
            j--;
        }
    }

    @Override
    public void clear() {

    }
}
