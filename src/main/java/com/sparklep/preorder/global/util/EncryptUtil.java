package com.sparklep.preorder.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@Component
public class EncryptUtil {
    private final TextEncryptor textEncryptor;

    public EncryptUtil(@Value("${encrypt.secretKey}") String secret) {
        this.textEncryptor = Encryptors.text(secret, KeyGenerators.string().generateKey());
    }

    public String encrypt(String plainText) {
        return textEncryptor.encrypt(plainText);
    }

    public String decrypt(String encryptedText) {
        return textEncryptor.decrypt(encryptedText);
    }
}