package com.blockmap.did.core.web3j.crypto;

public interface SignInterface {
    Sign.SignatureData signMessage(byte[] message, ECKeyPair keyPair);
}
