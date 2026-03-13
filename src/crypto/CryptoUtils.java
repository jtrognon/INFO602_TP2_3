package crypto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoUtils {

    private static final String ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGO = "SHA256withRSA";

    private static final Path PUBLIC_KEY_PATH = Paths.get("keys/public.key");
    private static final Path PRIVATE_KEY_PATH = Paths.get("keys/private.key");

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public CryptoUtils() throws Exception {
        init();
    }

    private void init() throws Exception {

        if (!Files.exists(PUBLIC_KEY_PATH) || !Files.exists(PRIVATE_KEY_PATH)) {
            generateAndSaveKeys();
        }

        publicKey = loadPublicKey();
        privateKey = loadPrivateKey();
    }

    private void generateAndSaveKeys() throws Exception {

        Files.createDirectories(PUBLIC_KEY_PATH.getParent());

        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
        generator.initialize(2048);

        KeyPair pair = generator.generateKeyPair();

        Files.write(PUBLIC_KEY_PATH, pair.getPublic().getEncoded());
        Files.write(PRIVATE_KEY_PATH, pair.getPrivate().getEncoded());
    }

    private PublicKey loadPublicKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(PUBLIC_KEY_PATH);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePublic(spec);
    }

    private PrivateKey loadPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(PRIVATE_KEY_PATH);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePrivate(spec);
    }

    public String sign(String message) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGO);
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        byte[] signed = signature.sign();
        return Base64.getEncoder().encodeToString(signed);
    }

    public static boolean verify(String message, String signatureStr, PublicKey key) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGO);
        signature.initVerify(key);
        signature.update(message.getBytes());
        byte[] sigBytes = Base64.getDecoder().decode(signatureStr);
        return signature.verify(sigBytes);
    }

    public static String keyTobase64(Key key) {
        String base64 = Base64.getEncoder()
                .encodeToString(key.getEncoded());
        return base64;
    }

    public static PublicKey base64toPublicKey(String base64key) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return KeyFactory.getInstance(ALGORITHM).generatePublic(spec);
    }

}
