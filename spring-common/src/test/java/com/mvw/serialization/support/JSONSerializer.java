package com.mvw.serialization.support;

import com.alibaba.fastjson.JSON;
import com.mvw.serialization.Serializer;
import com.mvw.serialization.test.TestBean;

//fast json
public class JSONSerializer implements Serializer {
    
    public byte[] serialize(Object object) {
        try {
           return JSON.toJSONString(object).getBytes();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public Object deserialize(byte[] bytes) {
        try {
           return JSON.parseObject(bytes, TestBean.class);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
