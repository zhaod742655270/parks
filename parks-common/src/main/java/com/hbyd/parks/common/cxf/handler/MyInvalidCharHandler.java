package com.hbyd.parks.common.cxf.handler;

import com.ctc.wstx.api.InvalidCharHandler;

import java.io.IOException;

/**
 * WebService 输出字符串时，将 XML 中的无效字符转换为指定字符
 */
public class MyInvalidCharHandler implements InvalidCharHandler {

    final char mReplacementChar;

    public MyInvalidCharHandler(String mReplacementChar) {
        this.mReplacementChar = mReplacementChar.charAt(0);
    }

    @Override
    public char convertInvalidChar(int invalidChar) throws IOException {
        return mReplacementChar;
    }
}
