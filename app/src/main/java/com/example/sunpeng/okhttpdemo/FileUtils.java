package com.example.sunpeng.okhttpdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by sunpeng on 2016/10/21.
 */

public class FileUtils {

    public static boolean writeStringDataToFile(String path, String data){
        boolean bSuccess = false;
        File file  =new File(path);
        OutputStream os = null;
        BufferedWriter bw = null;
        try {
            file.createNewFile();
            os= new FileOutputStream(file);
            bw= new BufferedWriter(new OutputStreamWriter(os));
            bw.write(data);
            bw.flush();
            bSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
            bSuccess = false;
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(bw!=null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                bSuccess=false;
            }

        }
        return bSuccess;
    }
    public static <T> T getStringDataFromFile(String filePath, Class<T> clazz){
        if(TextUtils.isEmpty(filePath))
            return null;
        T t;
        StringBuilder sb = new StringBuilder();
        InputStream is=null;
        BufferedReader bf=null;
        try{
            String s;
            File file = new File(filePath);
            if(!file.exists())
                return null;
            is = new FileInputStream(file);
            bf = new BufferedReader(new InputStreamReader(is));
            while ((s=bf.readLine())!=null){
                sb.append(s);
            }
        }catch (IOException e){
            return null;
        }finally {
            try {
                if(is!=null)
                    is.close();
                if(bf!=null)
                    bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        t = JSON.parseObject(sb.toString(),clazz);
        return  t;
    }

    public static void parseProvinceDataXML(Context mContext) {
        try {
            AssetManager asset = mContext.getAssets();
            InputStream input = asset.open("province_data.xml");

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
