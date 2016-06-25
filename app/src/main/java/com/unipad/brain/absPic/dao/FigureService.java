package com.unipad.brain.absPic.dao;

import android.content.Context;
import android.util.Log;

import com.unipad.ICoreService;
import com.unipad.brain.AbsBaseGameService;
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
public class FigureService extends AbsBaseGameService{

    public ArrayList<Figure> allFigures = new ArrayList<>();
    private String headResourse = "assets://absFigure/";
    private String path = "absFigure";
    private static final int VOLUM = 5;//每行抽象图形的个数


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
            for (int i = 0; i < randomFileNames.size(); i++) {
                allFigures.add(new Figure(headResourse + randomFileNames.get(i), i % 5 +1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public String getAnswerData() {
        return null;
    }
}
