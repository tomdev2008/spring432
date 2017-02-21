package com.gtp.jiami.utils;

import javax.xml.bind.DatatypeConverter;

/**
 * base64编码
 * 
 * @author gaotingping
 *
 * 2016年11月25日 下午3:37:56
 */
public class Base64Util {

	public static String encode(byte[] b) {

		return DatatypeConverter.printBase64Binary(b);

	}

	public static byte[] decode(String str) {

		return DatatypeConverter.parseBase64Binary(str);

	}
}
