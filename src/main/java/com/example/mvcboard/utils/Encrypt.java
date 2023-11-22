/**
 * 게시글 작성 시 입력한 비밀번호를
 * salt 방식으로 암호화한다.
 * */

package com.example.mvcboard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {

    public static String getEncrypt(String pwd) {

        String result = "";
        String salt = "pepper";

        try {
            //1. SHA256 알고리즘 객체 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //2. 비밀번호와 salt 합친 문자열에 SHA 256 적용
            System.out.println("비밀번호 + salt 적용 전 : " + pwd+salt);
            md.update((pwd+salt).getBytes());
            byte[] pwdsalt = md.digest();

            //3. byte To String (10진수의 문자열로 변경)
            StringBuffer sb = new StringBuffer();
            for (byte b : pwdsalt) {
                sb.append(String.format("%02x", b));
            }

            result=sb.toString();
            System.out.println("비밀번호 + salt 적용 후 : " + result);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }  // getEncrypt()

}  // class
