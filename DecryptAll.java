import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class DecryptAll {
    private static final char[] PASSWORD = "t3mc0bAZK@ENcRypT25#Lk".toCharArray();
    private static final byte[] SALT = {
        (byte) 0x3d, (byte) 0x7a, (byte) 0x1c, (byte) 0x5f,
        (byte) 0xe2, (byte) 0x91, (byte) 0xb6, (byte) 0x48,};

    public static String decrypt(String encrypted) throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(Base64.getDecoder().decode(encrypted)));
    }

    public static void main(String[] args) throws Exception {
        String[][] users = {
            {"admin", "GYlSHUv+Meq4u1ebghyyhA=="},
            {"temco_gevindu", "aZm0Gmhcc4ndr0c6nxA4kw=="},
            {"temco_adhikari", "CQdSuUfTyBSEfTTbY07dvg=="},
            {"temco_costa", "coyCghfZlFpRYwCqiQyzqw=="},
            {"temco_prasanna", "sA/P1/woXPoXf4ZO14Pa6A=="},
            {"jiat_kaushalya", "eRn+ZUKoSmBWwahadTGmWA=="}
        };
        
        for (String[] user : users) {
            System.out.println(user[0] + " : " + decrypt(user[1]));
        }
    }
}
