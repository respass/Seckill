package scu.zpf.seckill.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

    static public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[]  cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
