import java.io.*;
import java.util.*;
import java.security.Provider.Service;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import de.flexiprovider.core.FlexiCoreProvider;


public class FileEncrypt{


public static void main(String []argv) throws Exception {

   
   Security.addProvider(new FlexiCoreProvider());
  

  Cipher cipher = Cipher.getInstance("AES128_CBC", "FlexiCore");
  KeyGenerator keyGen = KeyGenerator.getInstance("AES", "FlexiCore");
  SecretKey secKey = keyGen.generateKey();



cipher.init(Cipher.ENCRYPT_MODE, secKey);
 System.out.println("enter name of file to encrypt ");
 Scanner sc =new Scanner(System.in);
 String cleartextFile = sc.nextLine();
 String ciphertextFile = "encryptedFile.txt";
 FileInputStream fis = new FileInputStream(cleartextFile);
 FileOutputStream fos = new FileOutputStream(ciphertextFile);
 CipherOutputStream cos = new CipherOutputStream(fos, cipher);
 
 byte[] block = new byte[8];
 int i;
 while ((i = fis.read(block)) != -1) {
 cos.write(block, 0, i);
 }
 cos.close();




String cleartextAgainFile = "decryptedFile.txt";
 
fis = new FileInputStream(ciphertextFile);
 CipherInputStream cis = new CipherInputStream(fis, cipher);
 fos = new FileOutputStream(cleartextAgainFile);

while ((i = cis.read(block)) != -1) {
 fos.write(block, 0, i);
 }
 fos.close(); 
 
}



} 

