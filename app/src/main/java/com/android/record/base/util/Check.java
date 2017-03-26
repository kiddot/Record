package com.android.record.base.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by kiddo on 17-3-26.
 */

public class Check {

    public static boolean isEmpty(CharSequence str) {
        return isNull(str) || str.length() == 0;
    }

    public static boolean isEmpty(Object[] os) {
        return isNull(os) || os.length == 0;
    }

    public static boolean isEmpty(Collection<?> l) {
        return isNull(l) || l.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return isNull(m) || m.isEmpty();
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static CharSequence NullAsNil(CharSequence str){
        if(isEmpty(str)) {
            return "";
        }else {
            return str;
        }
    }

    /**
     * 也可以参考使用 {@link StringUtil#nullStrToEmpty}
     * @param str
     * @return
     */
    public static String NullAsNil(String str){
        if(isEmpty(str)) {
            return "";
        }else {
            return str;
        }
    }

}
