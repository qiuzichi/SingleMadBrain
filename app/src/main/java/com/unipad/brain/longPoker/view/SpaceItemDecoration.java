package com.unipad.brain.longPoker.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gongkan on 2016/7/14.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space1;
    private int space2;

    public SpaceItemDecoration(int space1,int space2) {
        this.space1 = space1;
        this.space2 = space2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view)%53 == 0 || parent.getChildPosition(view)%53 ==1) {
            outRect.left = space1;
        }else{
            outRect.left = space2;
        }
    }
}
