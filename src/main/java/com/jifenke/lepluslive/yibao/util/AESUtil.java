package com.jifenke.lepluslive.yibao.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zhangwen on 2017/7/13.
 */
public class AESUtil {

  private static final String AES = "AES";
  private static final String UTF8 = "UTF-8";

  /**
   * AES加密
   */
  public static String encrypt(String content, String pKey) {
    try {
      byte[] encodeFormat = null;
      try {
        //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
        encodeFormat = Hex.decodeHex(pKey.toCharArray());
      } catch (DecoderException e) {
        e.printStackTrace();
      }
      SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
      // Cipher对象实际完成加密操作
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      // 加密内容进行编码
      byte[] byteContent = content.getBytes(UTF8);
      // 用密匙初始化Cipher对象
      cipher.init(Cipher.ENCRYPT_MODE, key);
      // 正式执行加密操作
      return cipher.doFinal(byteContent).toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * AES解密
   */
  public static String decrypt(String contents, String password) throws DecoderException {
    try {
      //密文使用Hex解码
      byte[] content = Hex.decodeHex(contents.toCharArray());
      //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
      byte[] encodeFormat = Hex.decodeHex(password.toCharArray());
      SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
      // Cipher对象实际完成加密操作
      Cipher cipher = Cipher.getInstance(AES);
      // 用密匙初始化Cipher对象
      cipher.init(Cipher.DECRYPT_MODE, key);
      // 正式执行解密操作
      return cipher.doFinal(content).toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
