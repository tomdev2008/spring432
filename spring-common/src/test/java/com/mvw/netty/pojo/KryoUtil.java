package com.mvw.netty.pojo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

//kryo
public class KryoUtil{
    
	public static byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Output output = new Output(stream);
            Kryo kryo=new Kryo();
           //kryo.writeObject(output, object);
            kryo.writeClassAndObject(output, object);
            output.close();
            return stream.toByteArray();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
    
	//速度更快，体积更小，但是需要知道class
    public static Object deserialize(byte[] bytes) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            Input input = new Input(stream);
            Kryo kryo=new Kryo();
            //Object result = kryo.readObject(input, BeanDTO.class);
            Object result = kryo.readClassAndObject(input);
            input.close();
            return result;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
}
