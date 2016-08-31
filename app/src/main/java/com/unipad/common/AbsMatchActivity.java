package com.unipad.common;

import com.unipad.singlebrain.AbsBaseGameService;
import com.unipad.singlebrain.BasicActivity;

/**
 * Created by gongkan on 2016/8/4.
 */
public abstract class AbsMatchActivity extends BasicActivity{

    public abstract AbsBaseGameService getService();

    public abstract String getProjectId();
}
