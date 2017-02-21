package org.spring.dubbo.serialize;

import java.io.IOException;
import java.io.OutputStream;

import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class KryoObjectOutput implements ObjectOutput{

	private final Output out;
	
	private final Kryo kryo;
	
	public KryoObjectOutput(OutputStream output) {
		out=new Output(output);
		kryo=new Kryo();
	}

	@Override
	public void writeBool(boolean v) throws IOException {
		out.writeBoolean(v);
	}

	@Override
	public void writeByte(byte v) throws IOException {
		out.writeByte(v);
	}

	@Override
	public void writeShort(short v) throws IOException {
		out.writeShort(v);
	}

	@Override
	public void writeInt(int v) throws IOException {
		out.writeInt(v);
	}

	@Override
	public void writeLong(long v) throws IOException {
		out.writeLong(v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		out.writeFloat(v);
	}

	@Override
	public void writeDouble(double v) throws IOException {
		out.writeDouble(v);
	}

	@Override
	public void writeUTF(String v) throws IOException {
		out.writeString(v);
	}

	@Override
	public void writeBytes(byte[] v) throws IOException {
		out.writeBytes(v);
	}

	@Override
	public void writeBytes(byte[] v, int off, int len) throws IOException {
		out.writeBytes(v,off,len);
	}

	@Override
	public void flushBuffer() throws IOException {
		out.flush();
	}

	@Override
	public void writeObject(Object obj) throws IOException {
		kryo.writeClassAndObject(out, obj);
	}

}
