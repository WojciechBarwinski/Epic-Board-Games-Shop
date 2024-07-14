package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.xml.bind.DatatypeConverter;

@Service
public class KeyPairGeneratorService {

    public KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public void savePublicKey(PublicKey publicKey, String fileName) throws IOException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        String publicKeyPEM = "-----BEGIN PUBLIC KEY-----\n" +
                DatatypeConverter.printBase64Binary(x509EncodedKeySpec.getEncoded()) +
                "\n-----END PUBLIC KEY-----";
        Files.write(Paths.get(fileName), publicKeyPEM.getBytes());
    }

    public void savePrivateKey(PrivateKey privateKey, String fileName) throws IOException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\n" +
                DatatypeConverter.printBase64Binary(pkcs8EncodedKeySpec.getEncoded()) +
                "\n-----END PRIVATE KEY-----";
        Files.write(Paths.get(fileName), privateKeyPEM.getBytes());
    }

    public void generateAndSaveKeys() throws NoSuchAlgorithmException, IOException {
        KeyPair keyPair = generateRSAKeyPair();
        savePublicKey(keyPair.getPublic(), "src/main/resources/keys/public.pem");
        savePrivateKey(keyPair.getPrivate(), "src/main/resources/keys/private.pem");
    }
}
