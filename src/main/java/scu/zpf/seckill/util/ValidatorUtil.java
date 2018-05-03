package scu.zpf.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern PHONE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isPhone(String s) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(s);
        return matcher.matches();
    }
}
