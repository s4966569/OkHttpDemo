package com.example.sunpeng.okhttpdemo;

import android.text.TextUtils;
import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by sunpeng on 2016/9/4.
 */
public class SysEncryptUtil {
    private static final String EN_DE_CODE_PWD = "ws3edaw4";
    public static String decryptDES(String deCipMsg, String decryptType) {
        if(!TextUtils.isEmpty(deCipMsg)) {
            String textDeCipher = deCipMsg;   //从服务器返回的加密字符串，需要解密的字符串
            byte[] byteStr = Base64.decode(textDeCipher, Base64.NO_WRAP);//先用Base64解码
            return decrypt(byteStr, EN_DE_CODE_PWD, decryptType);
        }
        return null;
    }

    public static String decrypt(byte[] code, String strKey, String alg) {
        if (!(alg.equalsIgnoreCase("DES") || alg.equalsIgnoreCase("DESede") || alg.equalsIgnoreCase("AES"))) {
            System.out.println("alg type not find: " + alg);
            return null;
        }
        String r = null;
        try {
            Key key = getKey(strKey.getBytes(), alg);
            Cipher c;
            c = Cipher.getInstance(alg);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] clearByte = c.doFinal(code);
            r = new String(clearByte);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            System.out.println("not padding");
            r = null;
        }
        return r;
    }

    public static Key getKey(byte[] arrBTmp, String alg) {
        if (!(alg.equalsIgnoreCase("DES") || alg.equalsIgnoreCase("DESede") || alg.equalsIgnoreCase("AES"))) {
            System.out.println("alg type not find: " + alg);
            return null;
        }
        byte[] arrB;
        if (alg.equalsIgnoreCase("DES")) {
            arrB = new byte[8];
        } else if (alg.equalsIgnoreCase("DESede")) {
            arrB = new byte[24];
        } else {
            arrB = new byte[16];
        }
        int i = 0;
        int j = 0;
        while (i < arrB.length) {
            if (j > arrBTmp.length - 1) {
                j = 0;
            }
            arrB[i] = arrBTmp[j];
            i++;
            j++;
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, alg);
        return key;
    }
}
