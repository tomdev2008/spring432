package com.mvw.serialization.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.mvw.serialization.Serializer;

//kryo
public class KryoSerializer implements Serializer {
    
	Kryo kryo;
    
    public KryoSerializer(Kryo kryo) {
       this.kryo=kryo;
    }
    
    public byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Output output = new Output(stream);
            this.kryo.writeClassAndObject(output, object);
            output.close();
            return stream.toByteArray();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public Object deserialize(byte[] bytes) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            Input input = new Input(stream);
            Object result = this.kryo.readClassAndObject(input);//与write对应
            input.close();
            return result;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
