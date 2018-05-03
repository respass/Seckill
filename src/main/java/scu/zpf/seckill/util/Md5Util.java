package scu.zpf.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {


    public static final String SALT = "1b2u3g";

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String inputPassswordToFormPassword(String input) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + input + SALT.charAt(1) + SALT.charAt(3);
        return md5(str);
    }

    public static String formPasswordToDbPassword(String form, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + form + salt.charAt(1) + salt.charAt(3);
        return md5(str);
    }

    public static String inputPasswordToDbPassword(String input, String salt) {
        return formPasswordToDbPassword(inputPassswordToFormPassword(input) , salt);
    }

    public static void main(String[] args) {
        System.out.println(inputPassswordToFormPassword("123456"));
        System.out.println(inputPasswordToDbPassword("123456", "1b2u3g" ));
    }


}
