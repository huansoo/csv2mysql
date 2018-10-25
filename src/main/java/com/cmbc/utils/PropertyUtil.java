package com.cmbc.utils;

import java.io.IOException;
import java.util.PropertyResourceBundle;

/**
 * Created by yangchuanhuan on 2018/10/25.
 */
public class PropertyUtil {
    public static String getProperties(String fileName, String key){
        try {
            PropertyResourceBundle prb = new PropertyResourceBundle(PropertyUtil.class.getClassLoader().getResourceAsStream(fileName));
            return prb.getString(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getMysqlProperties(String key){
        return getProperties("jdbc.properties", key);
    }
}
