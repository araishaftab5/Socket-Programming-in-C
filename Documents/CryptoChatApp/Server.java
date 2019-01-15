import java.io.*;
import java.net.*;
import java.util.*;
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

public class Server{


   static String publick = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDACy4ZUliBQWDImyyfxwr3JCxia71jjh9ty4y71H2W1c5jF4F6B9iKHEBKUNfIQG1ev4xOfgwGXcoky+V+D23cfV7aWeYjDoR3EpoG7WFf5nVkWTpBZ3zvu5np5SAheE+pwpM5g+sZHJzflBESzUw1OsHn353WgFymGEoJntmiIQIDAQAB";
   static String privatek = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALYs3ldDPT5DMOCHI/O6cEhWnZyvc/jky6ScI9Lk8tGthkCHGybbKO9Zz8dSjt3kTgxyxM0rzR+ZJzbwmeNe3Da3szlOBpf7pP8GMfHHjAeMXxk3g7gCOTHU3A+GHJndfaN1kC153ZrQiUradb+mD4md03gfDvqNWgg7o3/8wrdPAgMBAAECgYAn+a6MfX+54uqdymnUOMwcw4zwbfvH2QTqySx/Qaga5LX6AOuvWhYgd5fFiFM7U7bgump/DyaC0YuuXZuXLiCqLkdL2WGgOOGe7lbGGZiclG7ezP6zmXVIsBQmfyLKPHHoXMb0kgmVubN21ipJxoFn/6AKrKSntj4CsLJ00SdooQJBAOEkdisd7vmrZvUWYO7yeXusH8M6wa5A37EkQzYbeHSMwPRKPQhG3NzaX0a+F+lDLFT6jGeGDHPH6M7fghsADb8CQQDPJNMcHjszUqg2mjbIbHGC3lD8RxmtsMHWBLB3W6AiCTRqCXHAkf5H05TNbUhYHHF9CjmV1Mrxt/y8Q3CL+NpxAkEAjrRRwlqi0tX12HtqzYobvmwHZfOkOBMetmVJxM38t8NPuogjz087RzGTGn/7H/tX6jU1MZkh43Fe+2FoUjzieQJBAJy+ObziJ1vbpQvEP8YDQRWNEm0DhCvJP7Op0rpOq+p1WqMOSHQ2RNqnAMc9dWuvAjVi4lY9MeAcn/L5eRWBlhECQDSy8clnRYOLIUAUP/nIyLTlvAY39V/ePu5rR5HddO1qWFQ/9XJwXzOvxlS/16KKdiBy/iaafNTRcW7asbntnbo=";
   static int i =0 ;
   static Vector<ClientHandler> ar = new Vector<>(); 
   public static void main(String []argv) throws IOException,NoSuchAlgorithmException {
   
   ServerSocket server = new ServerSocket(3000);
   System.out.println("server started , waiting for client");


 while(true){
   Socket socket = null;
   DataInputStream in = null;
   DataOutputStream out = null;
   try{
   socket = server.accept();
   System.out.println("connected to client:"+socket);
   in = new DataInputStream(socket.getInputStream());
   out = new DataOutputStream(socket.getOutputStream());
   System.out.println("assign new thread to client");
   ClientHandler cl = new ClientHandler(socket,in,out,privatek);
   Thread t = new Thread(cl);
   ar.add(cl);
   t.start();
   i++;
   
   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
   System.out.println("online users -->");
   
   for (ClientHandler mc : Server.ar)  
            {  
                    System.out.println("user : "+ mc.name );
                    
            }
   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
   }catch(Exception e){
   	socket.close();
   	in.close();
   	out.close();
   	e.printStackTrace();
   }
}
}
}


class ClientHandler implements Runnable{

   DataOutputStream dos = null;
    Socket s = null;
    String receiverKey = "ssshhhhhhhhhhh!!!!";
     String secretKey = "ssshhhhhhhhhhh!!!!";
   DataInputStream dis = null;
   String name = "anonymous" ,privateKey;
   boolean isLoggedin ;
   public ClientHandler(Socket s,DataInputStream dis,DataOutputStream dos,String pk){
   	this.dos = dos;
   	this.dis = dis;
   	this.s = s;
   	this.privateKey = pk;
   	//this.name = name;
   	this.isLoggedin = true;
    }

   public void run(){

   String line = "",recpt = "";
  
   ClientHandler rec=null;
    try {
            //String encryptedString = Base64.getEncoder().encodeToString(encrypt(secretKey, publick));
            //System.out.println("encrypted secret key : "+encryptedString);
            //out.writeUTF(encryptedString);
    	    String encryptedString = dis.readUTF();
            System.out.println("received secret key to server!! "+encryptedString);
            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            secretKey = decryptedString;
            System.out.println(decryptedString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
  
   while(true){

   	try{
      line = dis.readUTF();
      if(line.equals("exit")){
      	this.isLoggedin=false; 
      	break;
      }
     String aesdec = AES.decrypt(line , secretKey);
      System.out.println("####################################");
      System.out.println("received string :"+line);
      System.out.println("decrypted string: "+ aesdec);
     System.out.println("####################################");
      //line = "server responding back";
      //dos.writeUTF(AES.encrypt(line,secretKey));
      
      if(aesdec.equals("disconnect")){
      	this.dos.writeUTF(AES.encrypt("disconnected  to user "+recpt,receiverKey));
      	rec = null;
      }
      else if(aesdec.charAt(0)=='$'){
      	String msg ="your profile name updated";
      	for (ClientHandler mc : Server.ar)  
                { 
                 
                    if(mc.name.equals(aesdec.substring(1))&&mc.isLoggedin==true){
                      msg = "name already exist else";
                      break;   
                    } 
                    
             }
        if(!msg.equals("name already exist else"))
      	this.name = aesdec.substring(1);
      	this.dos.writeUTF(AES.encrypt(msg,receiverKey));
      }
      else if (aesdec.charAt(0)=='@'){
      	if(!(aesdec.equals("@1")||aesdec.equals("@0"))){
        recpt = aesdec.substring(1);
       for (ClientHandler mc : Server.ar)  
                { 
                 
                    if(mc.name.equals(recpt)&&mc.isLoggedin==true){
                         rec = mc;
                    } 
                    
             }
            }
              if(rec != null){
              	receiverKey = rec.secretKey;
                         //rec.dos.writeUTF(AES.encrypt(aesdec,secretKey));
              	if(!(aesdec.equals("@1")||aesdec.equals("@0")))
              	rec.dos.writeUTF(AES.encrypt("secret",secretKey));
              	//System.out.println("good nyc");
              		System.out.println("good nyc again");
                          try {
             String encryptedString = Base64.getEncoder().encodeToString(RSAUtil.encrypt(secretKey, Server.publick));
             System.out.println("encrypted secret key : "+encryptedString);
             rec.dos.writeUTF(encryptedString);
             System.out.println("sent key to server!!");
            //String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            //System.out.println(decryptedString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        this.dos.writeUTF(AES.encrypt("connected to user "+recpt,receiverKey));
          }
              else
                   this.dos.writeUTF(AES.encrypt("cannot connect to user "+recpt,receiverKey));
            
      }
      else if(aesdec.equals("%")){

         ClientHandler clr = null;
         String s ="\nonline users --> \n";
         for (ClientHandler mc : Server.ar)  
            {     if((mc.name).equals(this.name))
                    clr = mc;
            	    s+= "user : "+mc.name+"\n";
            	    
                    //System.out.println("user : "+ mc.name );
                    
            }
            clr.dos.writeUTF(AES.encrypt(s,receiverKey)); 
      }
      else if (aesdec.contains("#")){
      StringTokenizer st = new StringTokenizer(aesdec, "#"); 
      String MsgToSend = st.nextToken(); 
      String recipient = st.nextToken();
              for (ClientHandler mc : Server.ar)  
                { 
                    if (mc.name.equals(recipient) && mc.isLoggedin==true)  
                    { 
                        mc.dos.writeUTF(AES.encrypt(this.name+" : "+MsgToSend,secretKey));
                       
                    } 
                    
                }


             }else{
                         

                         if(aesdec.equals("logout")){
      	try
        { 
            // closing resources 
            this.s.close();
            if(rec != null){
            rec.dos.close();
            rec.dis.close();
           }
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            //e.printStackTrace(); 
        } 

      }
                         
                         else if(rec != null){
                         rec.dos.writeUTF(AES.encrypt(this.name+" : "+aesdec,secretKey));
                         //this.dos.writeUTF(AES.encrypt("connecte",secretKey));
                        }
                        else
                        this.dos.writeUTF(AES.encrypt("disconnected to  user "+recpt,receiverKey));
             }
         

   	}catch(IOException i){
   		System.out.println(i);
   	}
   }

   try
        { 
            // closing resources 
            this.s.close();
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
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

private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDACy4ZUliBQWDImyyfxwr3JCxia71jjh9ty4y71H2W1c5jF4F6B9iKHEBKUNfIQG1ev4xOfgwGXcoky+V+D23cfV7aWeYjDoR3EpoG7WFf5nVkWTpBZ3zvu5np5SAheE+pwpM5g+sZHJzflBESzUw1OsHn353WgFymGEoJntmiIQIDAQAB";
private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALYs3ldDPT5DMOCHI/O6cEhWnZyvc/jky6ScI9Lk8tGthkCHGybbKO9Zz8dSjt3kTgxyxM0rzR+ZJzbwmeNe3Da3szlOBpf7pP8GMfHHjAeMXxk3g7gCOTHU3A+GHJndfaN1kC153ZrQiUradb+mD4md03gfDvqNWgg7o3/8wrdPAgMBAAECgYAn+a6MfX+54uqdymnUOMwcw4zwbfvH2QTqySx/Qaga5LX6AOuvWhYgd5fFiFM7U7bgump/DyaC0YuuXZuXLiCqLkdL2WGgOOGe7lbGGZiclG7ezP6zmXVIsBQmfyLKPHHoXMb0kgmVubN21ipJxoFn/6AKrKSntj4CsLJ00SdooQJBAOEkdisd7vmrZvUWYO7yeXusH8M6wa5A37EkQzYbeHSMwPRKPQhG3NzaX0a+F+lDLFT6jGeGDHPH6M7fghsADb8CQQDPJNMcHjszUqg2mjbIbHGC3lD8RxmtsMHWBLB3W6AiCTRqCXHAkf5H05TNbUhYHHF9CjmV1Mrxt/y8Q3CL+NpxAkEAjrRRwlqi0tX12HtqzYobvmwHZfOkOBMetmVJxM38t8NPuogjz087RzGTGn/7H/tX6jU1MZkh43Fe+2FoUjzieQJBAJy+ObziJ1vbpQvEP8YDQRWNEm0DhCvJP7Op0rpOq+p1WqMOSHQ2RNqnAMc9dWuvAjVi4lY9MeAcn/L5eRWBlhECQDSy8clnRYOLIUAUP/nIyLTlvAY39V/ePu5rR5HddO1qWFQ/9XJwXzOvxlS/16KKdiBy/iaafNTRcW7asbntnbo=";
   
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
