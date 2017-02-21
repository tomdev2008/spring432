package org.spring.dubbo.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;

/**
 * 扩展dubbo序列化
 * 
 * @author gaotingping
 *
 * 2016年11月9日 上午10:54:04
 */
public class KryoSerialization implements Serialization{

	@Override
	public byte getContentTypeId() {
		return 8;
	}

	@Override
	public String getContentType() {
		return "x-application/kryo";
	}

	@Override
	public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
		return new KryoObjectOutput(output);
	}

	@Override
	public ObjectInput deserialize(URL url, InputStream input) throws IOException {
		return new KryoObjectInput(input);
	}
}
