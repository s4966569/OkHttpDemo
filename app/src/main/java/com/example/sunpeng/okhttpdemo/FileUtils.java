package com.example.sunpeng.okhttpdemo;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by sunpeng on 2016/10/21.
 */

public class FileUtils {

    public static <T> T getStringFromFile(String filePath, Class<T> clazz) {
        if (TextUtils.isEmpty(filePath))
            throw new IllegalArgumentException("File path is null or empty");
        File file = new File(filePath);
        return getStringFromFile(file, clazz);
    }

    public static <T> T getStringFromFile(File file, Class<T> clazz) {
        if (file == null || !file.exists())
            throw new IllegalArgumentException("file is null or doesn't exists");
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return getStringFromInputStream(inputStream, clazz);
    }

    public static <T> T getStringFromInputStream(InputStream inputStream, Class<T> clazz) {
        if (inputStream == null)
            return null;
        T t;
        StringBuilder sb = new StringBuilder();
        BufferedReader bf;
        String s;
        bf = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((s = bf.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (bf != null)
                    bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        t = JSON.parseObject(sb.toString(), clazz);
        return t;
    }

    public static InputStream getInputStreamFromAsset(String filaName) {
        InputStream inputStream = null;
        try {
            AssetManager asset = MyApplication.getApplication().getAssets();
            inputStream = asset.open("province_data.xml");

        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
        return inputStream;
    }

    public static boolean writeStringToFile(String path, String data) {
        boolean bSuccess = false;
        File file = new File(path);
        OutputStream os = null;
        BufferedWriter bw = null;
        try {
            file.createNewFile();
            os = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(data);
            bw.flush();
            bSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
            bSuccess = false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                bSuccess = false;
            }

        }
        return bSuccess;
    }

}
