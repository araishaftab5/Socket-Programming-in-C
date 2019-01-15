import java.io.*;
import java.util.*;
import java.net.*;
import java.security.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
 import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Client{


 private static Socket socket;
 private static DataInputStream in = null;
 private static DataOutputStream out = null;
 final static String secretKey = "ssshhhhhhhhhhh!!!!";
 private static String publick= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2LN5XQz0+QzDghyPzunBIVp2cr3P45MuknCPS5PLRrYZAhxsm2yjvWc/HUo7d5E4McsTNK80fmSc28JnjXtw2t7M5TgaX+6T/BjHxx4wHjF8ZN4O4Ajkx1NwPhhyZ3X2jdZAted2a0IlK2nW/pg+JndN4Hw76jVoIO6N//MK3TwIDAQAB";
 private static String privatek = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMALLhlSWIFBYMibLJ/HCvckLGJrvWOOH23LjLvUfZbVzmMXgXoH2IocQEpQ18hAbV6/jE5+DAZdyiTL5X4Pbdx9XtpZ5iMOhHcSmgbtYV/mdWRZOkFnfO+7menlICF4T6nCkzmD6xkcnN+UERLNTDU6weffndaAXKYYSgme2aIhAgMBAAECgYEAu2JtUcHaoAx17mgTC7hAx9NhB4vPGTQVEKH7qU72WFY48tF9wd+j92cHiDwHwNq0nS3ULsao3xFm666UOAPAEiBqWHGPyUYieUBQ4roZNVwI5l/ByOn9rhohY3VEbFTrjk46Wb97KbNVAy3BlVAXKtETvaS7+Yp1vcUFLdV9uu0CQQD3X7CekFX8EvtMnEmxU44CBZUrDPkluxdhuZ9ijqRrs5u340OEqMw7gChZNW672Ip2xYZpMQy9raibSWsR70cvAkEAxr2Op9257bKn6jBH583x9kAew37zgfCir/k1lw/PiUPosRIM3V4FxT4DZd3VnVBQa/mJFN2d0HlOCC2JgJlXrwJAGt4Lws0OabhuDt4Squ3WSKXuk6RvEwgE/Bo7E2tGtGPnj8thM/FZbUT4HOhOxJw6PbgqZxZqudc4rtUEigghBQJAOeiR4KfivTgJZVucGBcSIcadMCmVmo8bWFstGk8pEb4P10iDJx12YPqo0s3IqIX2aP/UyZepnE08R3W9UxNbUQJBAOkischKSsHehILYBd5bNSFnRRUWKscZrs09N9NcQ6/Jfhn9pXyYCXIJ8K4eiEWLZCWFL1nquf7WhNYf64DVamQ=";
   String line = "";
   static String receiverKey = "ssshhhhhhhhhhh!!!!";

 public static void main(String []args) throws NoSuchAlgorithmException,UnknownHostException, IOException  {
   Scanner sc = new Scanner(System.in); 
    System.out.println("hii i am client");

         socket = new Socket("127.0.0.1",3000);
         in = new DataInputStream(socket.getInputStream());
         out = new DataOutputStream(socket.getOutputStream());


          try {
            String encryptedString = Base64.getEncoder().encodeToString(RSAUtil.encrypt(secretKey, publick));
            System.out.println("encrypted secret key : "+encryptedString);
            out.writeUTF(encryptedString);
            System.out.println("sent key to server!!");
            //String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            //System.out.println(decryptedString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        



    Thread sendMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() {
            System.out.println("enter your name begin by $ to continue \n type 'disconnect' to disconnect messenger\n @ fllowed by user to connected to messenger\n % to see all online user and \nlogout to logout from chat app"); 
                while (true) { 
                    // read the message to deliver. 
                    System.out.println("enter message : ");
                    String msg = sc.nextLine(); 
                    
                    try { 
                        // write on the output stream 
                        out.writeUTF(AES.encrypt(msg,secretKey)); 
                    } catch (IOException e) { 
                            
                        e.printStackTrace(); 
                    } 
                } 
            } 


        }); 
          
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = in.readUTF();
                        String aesdec = AES.decrypt(msg,receiverKey);
                        if(aesdec.equals("secret")){
                           // System.out.println("some one message to you enter @yes to allow otherwise @no");
                            //int str = sc.nextInt();
                            //System.out.println("done");
                            //if(str == 1){
                                //System.out.println("done again"); 
                            //out.writeUTF("@1");
                            
                            try{ String encryptedString = in.readUTF();
            System.out.println("received secret key to server!! "+encryptedString);
            String decryptedString = RSAUtil.decrypt(encryptedString, privatek);
            receiverKey = decryptedString;
            System.out.println(decryptedString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
                        System.out.println("####################################");
                           System.out.println("received string: "+msg);
                          System.out.println("decrypted string: "+aesdec);
                          System.out.println("####################################");
                    } catch (IOException e) {
                         
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
  
        sendMessage.start(); 
        readMessage.start(); 
   
 }
}

class AES {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}

class RSAUtil {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2LN5XQz0+QzDghyPzunBIVp2cr3P45MuknCPS5PLRrYZAhxsm2yjvWc/HUo7d5E4McsTNK80fmSc28JnjXtw2t7M5TgaX+6T/BjHxx4wHjF8ZN4O4Ajkx1NwPhhyZ3X2jdZAted2a0IlK2nW/pg+JndN4Hw76jVoIO6N//MK3TwIDAQAB";
    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMALLhlSWIFBYMibLJ/HCvckLGJrvWOOH23LjLvUfZbVzmMXgXoH2IocQEpQ18hAbV6/jE5+DAZdyiTL5X4Pbdx9XtpZ5iMOhHcSmgbtYV/mdWRZOkFnfO+7menlICF4T6nCkzmD6xkcnN+UERLNTDU6weffndaAXKYYSgme2aIhAgMBAAECgYEAu2JtUcHaoAx17mgTC7hAx9NhB4vPGTQVEKH7qU72WFY48tF9wd+j92cHiDwHwNq0nS3ULsao3xFm666UOAPAEiBqWHGPyUYieUBQ4roZNVwI5l/ByOn9rhohY3VEbFTrjk46Wb97KbNVAy3BlVAXKtETvaS7+Yp1vcUFLdV9uu0CQQD3X7CekFX8EvtMnEmxU44CBZUrDPkluxdhuZ9ijqRrs5u340OEqMw7gChZNW672Ip2xYZpMQy9raibSWsR70cvAkEAxr2Op9257bKn6jBH583x9kAew37zgfCir/k1lw/PiUPosRIM3V4FxT4DZd3VnVBQa/mJFN2d0HlOCC2JgJlXrwJAGt4Lws0OabhuDt4Squ3WSKXuk6RvEwgE/Bo7E2tGtGPnj8thM/FZbUT4HOhOxJw6PbgqZxZqudc4rtUEigghBQJAOeiR4KfivTgJZVucGBcSIcadMCmVmo8bWFstGk8pEb4P10iDJx12YPqo0s3IqIX2aP/UyZepnE08R3W9UxNbUQJBAOkischKSsHehILYBd5bNSFnRRUWKscZrs09N9NcQ6/Jfhn9pXyYCXIJ8K4eiEWLZCWFL1nquf7WhNYf64DVamQ=";
    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    // public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
    //     try {
    //         String encryptedString = Base64.getEncoder().encodeToString(encrypt("Dhiraj is the author", publicKey));
    //         System.out.println(encryptedString);
    //         String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
    //         System.out.println(decryptedString);
    //     } catch (NoSuchAlgorithmException e) {
    //         System.err.println(e.getMessage());
    //     }

    // }
}