package co.com.juan.invbill.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Felipe
 */
public class Encrypt {

    private static final Logger log = LoggerFactory.getLogger(Encrypt.class);
    private static final String PASS_PHRASE = "We are the world";
    private Cipher cipher;
    private Cipher decipher;

    public Encrypt() {
        SecretKey key;
        byte[] salt = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3,
                (byte) 0x03};
        int iterationCount = 19;

        KeySpec keySpec = new PBEKeySpec(PASS_PHRASE.toCharArray(), salt, iterationCount);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        try {
            key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            cipher = Cipher.getInstance(key.getAlgorithm());
            decipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            decipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (InvalidKeySpecException e) {
            log.error("init encrypt failed. Invalid key specifications", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("init encrypt failed. A cryptographic algorithm is requested but is not available in the environment", e);
        } catch (NoSuchPaddingException e) {
            log.error("init encrypt failed. A padding mechanism is requested but is not available in the environment", e);
        } catch (InvalidKeyException e) {
            log.error("init encrypt failed. Invalid Keys", e);
        } catch (InvalidAlgorithmParameterException e) {
            log.error("init encrypt failed. Invalid or inappropriate algorithm parameters", e);
        }
    }

    public String encrypt(String str) {
        byte[] utf8;
        byte[] enc;
        try {
            utf8 = str.getBytes(StandardCharsets.UTF_8);
            enc = cipher.doFinal(utf8);
            return Base64.getEncoder().encodeToString(enc);
        } catch (IllegalBlockSizeException e) {
            log.error("init encrypt failed. The length of data provided to a block cipher is incorrect", e);
        } catch (BadPaddingException e) {
            log.error("init encrypt failed. A padding mechanism is expected for the input data but the data is not padded properly", e);
        }

        return null;
    }

    public String decrypt(String str) {
        byte[] dec;
        byte[] utf8;
        try {
            dec = Base64.getDecoder().decode(str);
            utf8 = decipher.doFinal(dec);
            return new String(utf8, StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException e) {
            log.error("init encrypt failed. The length of data provided to a block cipher is incorrect", e);
        } catch (BadPaddingException e) {
            log.error("init encrypt failed. A padding mechanism is expected for the input data but the data is not padded properly", e);
        }

        return null;
    }

}
