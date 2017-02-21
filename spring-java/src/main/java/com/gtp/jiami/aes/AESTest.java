package com.gtp.jiami.aes;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.gtp.jiami.utils.Base64Util;

/**
 * DES/AES :对称算法，私钥加密和解密
 * 缺点:私钥传递的不安全性
 * 
 * @author gaotingping
 *
 * 2016年11月25日 下午3:40:50
 */
public class AESTest {

	public static byte[] encrypt(String content, String pwd) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(256, new SecureRandom(pwd.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] content, String pwd) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(256, new SecureRandom(pwd.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//Invalid AES key length: 8 bytes
	public static void main(String[] args) {
		String content = "123";
		String password = "root@pc201409251700";
		
		// 加密
		System.out.println("明文:" + content);
		byte[] encryptResult = encrypt(content, password);
		String tt4 = Base64Util.encode(encryptResult);
		System.out.println("密文:"+tt4);

		// 解密
		byte[] decryptResult = decrypt(Base64Util.decode(tt4), password);
		System.out.println("解密后：" + new String(decryptResult));
	}
}
