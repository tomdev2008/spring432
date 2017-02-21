package com.mvw.serialization.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.mvw.serialization.Serializer;

//hessian 
public class HessianSerializer implements Serializer {
  
	public byte[] serialize(Object obj){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Hessian2Output ho = new Hessian2Output(os);
		try {
			ho.writeObject(obj);
			ho.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os.toByteArray();
	}

	public Object deserialize(byte[] by){
		ByteArrayInputStream is = new ByteArrayInputStream(by);
		Hessian2Input hi = new Hessian2Input(is);
		try {
			return hi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
