package com.mvw.serialization.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mvw.serialization.Serializer;

//jdk 自带
public class JavaSerializer implements Serializer {
    public byte[] serialize(Object object) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
            objectOut.writeObject(object);
            objectOut.flush();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return byteOut.toByteArray();        
    }
    
    public Object deserialize(byte[] bytes) {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objectIn = new ObjectInputStream(byteIn);
            return objectIn.readObject();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }        
    }
}
