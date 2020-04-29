package com.blockmap.did.core.web3j.crypto;

/**
 * Cipher com.blockmap.did.core.exception wrapper.
 */
public class CipherException extends Exception {

    public CipherException(String message) {
        super(message);
    }

    public CipherException(Throwable cause) {
        super(cause);
    }

    public CipherException(String message, Throwable cause) {
        super(message, cause);
    }
}
