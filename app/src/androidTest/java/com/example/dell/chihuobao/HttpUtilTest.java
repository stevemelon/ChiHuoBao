package com.example.dell.chihuobao;

import android.test.InstrumentationTestCase;

import com.example.dell.chihuobao.util.HttpUtil;

/**
 * Created by fanghao on 2016/3/4.
 */
public class HttpUtilTest extends InstrumentationTestCase {
    public void test() throws Exception {
        String s=HttpUtil.getURLResponse("http://10.6.12.82:8080/chb/user/login.do?username=xia&password=dsf");

        //assertEquals(s,);

    }
}
