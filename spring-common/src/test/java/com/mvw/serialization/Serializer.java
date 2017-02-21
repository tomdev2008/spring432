package com.mvw.serialization;

/**
 * 序列化测试
 * 
 * @author gaotingping
 *
 * 2016年7月29日 下午6:28:12
 */
public interface Serializer {
	
	public byte[] serialize(Object object);

	public Object deserialize(byte[] bytes);
}
