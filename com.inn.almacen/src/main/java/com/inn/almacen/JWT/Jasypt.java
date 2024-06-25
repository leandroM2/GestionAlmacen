package com.inn.almacen.JWT;

import com.inn.almacen.constens.AlmacenConstants;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;

@Service
public class Jasypt{
    private final String key="ASLDSDSLKADASLKDJASLKDJHA";
    private AES256TextEncryptor encrypt;

    public Jasypt(){
        encrypt=new AES256TextEncryptor();
        encrypt.setPassword(key);
    }

    public String encrypting(String password){
        try {
            return encrypt.encrypt(password);
        }catch (Exception e){
            e.printStackTrace();
            return AlmacenConstants.ALGO_SALIO_MAL;
        }
    }

    public String decrypting(Blob password){
        try {
            String pw=convertBlobToString(password);
            return encrypt.decrypt(pw);
        }catch (Exception e){
            e.printStackTrace();
            return AlmacenConstants.ALGO_SALIO_MAL;
        }
    }

    private String convertBlobToString(Blob blob) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = blob.getBinaryStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}