package com.assignment4.security;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESCrypt 
{
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    
    public static String encrypt(String value) 
    {
    	String encryptedValue64 = "";
       	try{
	        Key key = generateKey();
	        Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("ISO-8859-1"));
	        encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return encryptedValue64;
               
    }
    
    public static String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"ISO-8859-1");
        return decryptedValue;
                
    }
    
    private static Key generateKey() throws Exception 
    {
        Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(),AESCrypt.ALGORITHM);
        return key;
    }
}