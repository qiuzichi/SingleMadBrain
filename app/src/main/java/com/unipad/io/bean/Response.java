package com.unipad.io.bean;

import com.unipad.io.IWrite;

/**
 * Created by gongkan on 2016/5/31.
 */
public class Response implements IWrite{
    @Override
    public boolean write(byte[] data) {
        return false;
    }

    @Override
    public boolean write(byte[] data, int length) {
        return false;
    }
}
