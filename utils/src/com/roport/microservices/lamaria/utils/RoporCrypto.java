package com.roport.microservices.lamaria.utils;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;

public class RoporCrypto implements TextEncryptor {
    KeyGenerator keygenerator;
    SecretKey myDesKey;
    Cipher desCipher;
    final int AES_KEYLENGTH = 128;
    byte[] iv;
    IvParameterSpec parameter;

    public RoporCrypto() throws Exception{
        keygenerator = KeyGenerator.getInstance("AES");
        keygenerator.init(128);
        myDesKey = keygenerator.generateKey();
        iv = new byte[AES_KEYLENGTH / 8];
        desCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        parameter = new IvParameterSpec(iv);
    }

    @Override
    public String encrypt(String string) {
        try{
            // Initialize the cipher for encryption
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey, parameter);

            //sensitive information
            byte[] text = string.getBytes();
            byte[] textEncrypted = desCipher.doFinal(text);

            String result = new BASE64Encoder().encode(textEncrypted);;
            System.out.println("Original: "+string+" and Text Encryted : " + result);

            return result;

        }catch(InvalidKeyException e){
            e.printStackTrace();
        }catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }catch(BadPaddingException e){
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String string) {
        try{

            // Initialize the same cipher for decryption
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey, parameter);

            byte[] decoded = new BASE64Decoder().decodeBuffer(string);
            // Decrypt the text
            byte[] textDecrypted = desCipher.doFinal(decoded);

            String result = new String(textDecrypted);
            System.out.println("Text Decryted : " + result);

            return result;
        }catch(InvalidKeyException e){
            e.printStackTrace();
        }catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }catch(BadPaddingException e){
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
