package com.cmbc.utils;

import com.mysql.jdbc.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yangchuanhuan on 2018/10/25.
 */
public class ImportCVSUtil {
    public static void importToMysql(){
        String properties = PropertyUtil.getProperties("application.properties", "application.name");
        if(!StringUtils.isNullOrEmpty(properties)){
            String[] split = properties.split(",");
            for (String application : split) {
                String applicationProperty = String.format("%s.properties", application.trim());
                String tableName=PropertyUtil.getProperties(applicationProperty, "table");
                String csvFile=PropertyUtil.getProperties(applicationProperty, "csv");
                String fieldsStr=PropertyUtil.getProperties(applicationProperty, "fields");
                String delimiter=PropertyUtil.getProperties(applicationProperty, "delimiter");
                String[] fields = fieldsStr.split(",");
                List resultList = readCSV(csvFile, delimiter.trim(), fields);
                DbKit.batchMysql(tableName, fields , resultList);
            }
            
        }
    }
    public static List<Map> readCSV(String csvFileName, String delimiter, String[] fields){
        try {
            URI uri = new URI(ImportCVSUtil.class.getClassLoader().getResource("csv"+File.separator+csvFileName).toString());
            Path path = Paths.get(uri);
            List result = Files.readAllLines(path).stream().map(s ->{
                String[] results = s.split(delimiter, -1);
                Map map = new HashMap();
                for (int i = 0; i < results.length; i++) {
                    map.put(fields[i], results[i]);
                }
                return map;
            })
                    .collect(Collectors.toList());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
