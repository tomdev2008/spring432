package com.gtp.jiami.rsa;

import java.util.Map;

public class RSATest {
	static String publicKey;
	static String privateKey;

	static {
		try {
			Map<String, Object> keyMap = RSAUtils.genKeyPair();
			publicKey = RSAUtils.getPublicKey(keyMap);
			privateKey = RSAUtils.getPrivateKey(keyMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		test();
		testSign();
	}

	//加密:公钥加密——私钥解密
	static void test() throws Exception {
		
		String source = "测试文字，简单点不可以吗?";
		
		byte[] data = source.getBytes();
		byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
		byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
	
		String target = new String(decodedData);
		System.out.println(target);
	}

	//签名:私钥加密——公钥解密
	static void testSign() throws Exception {
		
		String source = "这是一行测试RSA数字签名的无意义文字";

		byte[] data = source.getBytes();
		byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);

		String sign = RSAUtils.sign(encodedData, privateKey);
		
		boolean status = RSAUtils.verify(encodedData, publicKey, sign);
		System.err.println("验证结果:" + status);
	}
}
