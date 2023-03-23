package GUI;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Encryptor
{
    //Note Encryption part
    //https://howtodoinjava.com/java/java-security/java-aes-encryption-example/

    //Image Encryption
    //https://www.baeldung.com/java-cipher-input-output-stream
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static Cipher cipher;


    public static void setKey(final String password)
    {
        MessageDigest sha = null;
        try
        {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //create secretKey for note
            key = password.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");


        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String encryptNote(final String strToEncrypt, final String password)
    {
        try
        {
            if (strToEncrypt.equals(""))
            {
                return strToEncrypt;
            }else
            {
                setKey(password);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.getEncoder()
                        .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            }
        } catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decryptNote(final String strToDecrypt, final String password)
    {
        try
        {
            setKey(password);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }


    static byte[] encryptByteArr(byte[] imageByteArr, String password)
    {
        Cipher cipher = null;
        setKey(password);
        try
        {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Encrypt the data
            return cipher.doFinal(imageByteArr);

        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e)
        {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e)
        {
            throw new RuntimeException(e);
        } catch (BadPaddingException e)
        {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e)
        {
            throw new RuntimeException(e);
        }

    }

    static byte[] decryptByteArr(byte[] encryptedByteArr, String password)
    {
        setKey(password);
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encryptedByteArr);

        } catch (IllegalBlockSizeException e)
        {
            throw new RuntimeException(e);
        } catch (BadPaddingException e)
        {
            ConsultTablePanel.wrongPassVisible();
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e)
        {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e)
        {
            System.out.println("Wrong Key!!");
            throw new RuntimeException(e);
        }
    }


}
