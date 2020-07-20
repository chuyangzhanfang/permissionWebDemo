package com.jiantou.demo.validator;

import com.jiantou.demo.exception.RRException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 *
 * @author jialin
 */
public abstract class Assert {
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
