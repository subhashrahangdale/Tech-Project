package com.springboottask.SpringSecurityTechProject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {
    public static String encryptedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(password.getBytes());
        byte[] bytes = m.digest();
        StringBuilder stringBuffer = new StringBuilder();
        for (byte b : bytes) {
            stringBuffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static String decryptedPassword(String password) {
        return null;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println(PasswordUtils.encryptedPassword("shubh"));
    }
}
