package com.unipad.io.bean;

import com.unipad.io.IPack;
import com.unipad.io.IWrite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by gongkan on 2016/5/31.
 */
public class Response implements IPack {

    private static int HEAD_LENGTH = 0;

    private Map<String,String> datas;
    public static enum PackType {

    }

    private byte[] buffer;
    private boolean isNeedResponse = false;

    private PackType packType;

    public PackType getPackType() {
        return packType;
    }


    @Override
    public void parsePack(byte[] data) {
        buffer = new byte[data.length - HEAD_LENGTH];
        System.arraycopy(buffer, 0, data, HEAD_LENGTH - 1, buffer.length);

        if (isNeedResponse) {

        }

    }

    private void readXMLString(byte[] xmlData) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(xmlData);
        Document doc = builder.parse(inputStream); //
        // 下面开始读取
        Element root = doc.getDocumentElement(); // 获取根元素
        NodeList all = root.getElementsByTagName("TRX");
        datas = new HashMap<String, String>();
        for (int i = 0; i < all.getLength(); i++) {
            Element headAndBody = (Element) all.item(i);
            NodeList part = root.getChildNodes();
            for (int j = 0; j < part.getLength(); j++) {
                Element e = (Element)part.item(j);
                datas.put(e.getTagName(),e.getNodeValue());

            }
        }
        Element head = (Element) all.item(0);



    }

    public boolean hasComplete() {
        return true;
    }

    public int readInt(int index, int length) {
        int temp = 0;
        for (int i = 0; i < length; i++) {
            temp = temp + ((buffer[index + i] & 0xff) << (8 * i));
        }
        return temp;
    }

    public String readString(int index, int length) {
        return new String(buffer, index, length);
    }

    public byte readByte(int index) {
        return buffer[index];
    }
}
