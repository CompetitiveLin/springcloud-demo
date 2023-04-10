package com.example.common.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {
    /**
     * 获取堆栈信息
     * @param throwable /
     * @return 堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        try(PrintWriter printWriter = new PrintWriter(stringWriter)){
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        }
    }
}