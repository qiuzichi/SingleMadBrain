package com.unipad.io;

/**
 * Created by gongkan on 2016/5/31.
 */
public abstract class AbsBaseDataHandler implements IOpen,IDataHandler{
    private IDataHandler mIDataHandler;
    public void setHandler(IDataHandler handler) {
        this.mIDataHandler = handler;
    }

    public void processPack(IPack pack, IWrite writer) {
        if (null != mIDataHandler) {
            mIDataHandler.processPack(pack,writer);
        }
    }
}
