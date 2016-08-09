package com.unipad.common;

import com.unipad.ICoreService;
import com.unipad.brain.BasicActivity;

/**
 * Created by gongkan on 2016/8/4.
 */
public abstract class AbsMatchActivity extends BasicActivity{

    public abstract ICoreService getService();

    public abstract String getProjectId();
}
