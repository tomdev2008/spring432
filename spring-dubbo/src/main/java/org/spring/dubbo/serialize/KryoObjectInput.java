package org.spring.dubbo.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;

public class KryoObjectInput implements ObjectInput {

	private final Input in;

	private final Kryo kryo;

	public KryoObjectInput(InputStream input) {
		in = new Input(input);
		kryo = new Kryo();
	}

	@Override
	public boolean readBool() throws IOException {
		return in.readBoolean();
	}

	@Override
	public byte readByte() throws IOException {
		return in.readByte();
	}

	@Override
	public short readShort() throws IOException {
		return in.readShort();
	}

	@Override
	public int readInt() throws IOException {
		return in.readInt();
	}

	@Override
	public long readLong() throws IOException {
		return in.readLong();
	}

	@Override
	public float readFloat() throws IOException {
		return in.readFloat();
	}

	@Override
	public double readDouble() throws IOException {
		return in.readDouble();
	}

	@Override
	public String readUTF() throws IOException {
		return in.readString();
	}

	@Override
	public byte[] readBytes() throws IOException {
		try {
			int len = in.readInt();
			if (len < 0) {
				return null;
			} else if (len == 0) {
				return new byte[] {};
			} else {
				return in.readBytes(len);
			}
		} catch (KryoException e) {
			throw new IOException(e);
		}
	}

	public Object readObject() throws IOException, ClassNotFoundException {
		try {
			return kryo.readClassAndObject(in);
		} catch (KryoException e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T readObject(Class<T> clazz) throws IOException, ClassNotFoundException {
		return (T) readObject();
	}

	public <T> T readObject(Class<T> clazz, Type type) throws IOException, ClassNotFoundException {
		return (T) readObject(clazz);
	}
}
