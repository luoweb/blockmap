package com.blockmap.did.core.web3j.crypto;


public interface HashInterface {
      String hash(String hexInput);
      byte[] hash(byte[] input, int offset, int length);
      byte[] hash(byte[] input);
}
