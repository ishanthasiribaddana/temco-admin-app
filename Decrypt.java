import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Decrypt {
    private static final char[] PASSWORD = "t3mc0bAZK@ENcRypT25#Lk".toCharArray();
    private static final byte[] SALT = {
        (byte) 0x3d, (byte) 0x7a, (byte) 0x1c, (byte) 0x5f,
        (byte) 0xe2, (byte) 0x91, (byte) 0xb6, (byte) 0x48,};

    public static void main(String[] args) throws Exception {
        String encrypted = "GYlSHUv+Meq4u1ebghyyhA==";
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        String decrypted = new String(pbeCipher.doFinal(Base64.getDecoder().decode(encrypted)));
        System.out.println("Decrypted: " + decrypted);
    }
}
