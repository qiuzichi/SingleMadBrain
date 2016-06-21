package com.unipad.io.bean;

import android.util.Log;

import com.unipad.io.IPack;
import com.unipad.io.IWrite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
public class Response  implements IPack{

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


    public void parsePack(String data) {
        try {
            readXMLString(data);
        } catch (Exception e) {

        }
    }

    private void readXMLString(String xmlData) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes("GBK"));
        Document doc = builder.parse(inputStream); //
        // 下面开始读取
        Element root = doc.getDocumentElement();
        NodeList head = root.getChildNodes();
        Log.e("","length:"+head.getLength());
        datas = new HashMap<String, String>();
        for (int i = 0; i < head.getLength(); i++) {
            Node headAndBody = (Node) head.item(i);
            NodeList part = headAndBody.getChildNodes();
            for (int j = 0; j < part.getLength(); j++) {
                Node e = (Node)part.item(j);
                datas.put(e.getNodeName(),e.getTextContent());

            }
        }



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

    public Map<String, String> getDatas() {
        return datas;
    }
}
