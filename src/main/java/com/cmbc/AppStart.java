package com.cmbc;

import com.cmbc.utils.ImportCVSUtil;

import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class AppStart
{
    public static void main( String[] args ) throws FileNotFoundException {
        ImportCVSUtil.importToMysql();
    }
}
