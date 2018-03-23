package com.ropor.microservice.configuration.crypto;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;

@Component
@Primary
public class RoporCrypto implements TextEncryptor {
    KeyGenerator keygenerator;
    SecretKey myDesKey;
    Cipher desCipher;
    final int AES_KEYLENGTH = 128;
    byte[] iv;
    SecureRandom prng;

    public RoporCrypto() throws Exception{
        keygenerator = KeyGenerator.getInstance("AES");
        keygenerator.init(128);
        myDesKey = keygenerator.generateKey();
        iv = new byte[AES_KEYLENGTH / 8];
        prng = new SecureRandom();
        prng.nextBytes(iv);
        desCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    @Override
    public String encrypt(String string) {
        try{
            // Initialize the cipher for encryption
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey, new IvParameterSpec(iv));

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
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey,new IvParameterSpec(iv));

            // Decrypt the text
            byte[] textDecrypted = desCipher.doFinal(new BASE64Decoder().decodeBuffer(string));

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
