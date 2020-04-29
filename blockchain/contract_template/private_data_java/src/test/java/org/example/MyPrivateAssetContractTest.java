/*
 * SPDX-License-Identifier: Apache License 2.0
 */

package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public final class MyPrivateAssetContractTest {

    Context ctx;
    ChaincodeStub stub;
    MyPrivateAssetContract contract;

    String collection = "CollectionOne";

    @BeforeEach
    void beforeEach() {
        ctx = mock(Context.class);
        stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        contract = new MyPrivateAssetContract();

        byte[] someAsset = ("{\"privateValue\":\"125\"}").getBytes(StandardCharsets.UTF_8);
        when(stub.getPrivateData(collection, "001")).thenReturn(someAsset);
        when(stub.getPrivateDataHash(collection, "001")).thenReturn(("someAsset").getBytes(StandardCharsets.UTF_8));
    }

    @Nested
    class AssetExists {
        @Test
        public void noProperAsset() {
            when(stub.getPrivateData(collection, "002")).thenReturn(("").getBytes(StandardCharsets.UTF_8));
            boolean result = contract.myPrivateAssetExists(ctx, "002");
            assertFalse(result);
        }

        @Test
        public void assetExists() {
            boolean result = contract.myPrivateAssetExists(ctx, "001");
            assertTrue(result);
        }

        @Test
        public void noKey() {
            boolean result = contract.myPrivateAssetExists(ctx,"10002");
            assertFalse(result);
        }
    }

    @Nested
    class AssetCreates {

        @Test
        public void newPrivateAssetCreate() throws UnsupportedEncodingException {
            Map<String, byte[]> transientMap = new HashMap<>();

            transientMap.put("privateValue", "150".getBytes(StandardCharsets.UTF_8));

            when(stub.getTransient()).thenReturn(transientMap);
            contract.createMyPrivateAsset(ctx, "002");

            verify(stub).putPrivateData(collection, "002",
                    ("{\"privateValue\":\"150\"}").getBytes(StandardCharsets.UTF_8));
        }

        @Test
        public void alreadyExists() throws UnsupportedEncodingException {
            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createMyPrivateAsset(ctx, "001");
            });

            assertEquals(thrown.getMessage(), "The asset my private asset 001 already exists");
        }

        @Test
        public void noTransient() {
            Map<String, byte[]> transientMap = new HashMap<>();

            when(stub.getTransient()).thenReturn(transientMap);
            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createMyPrivateAsset(ctx, "002");
            });

            assertEquals(thrown.getMessage(),
                    "The privateValue key was not specified in transient data. Please try again.");
        }

        @Test
        public void incorrectKeyTransient() throws UnsupportedEncodingException {
            Map<String, byte[]> transientMap = new HashMap<>();

            transientMap.put("someValue", "125".getBytes(StandardCharsets.UTF_8));

            when(stub.getTransient()).thenReturn(transientMap);
            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createMyPrivateAsset(ctx, "002");
            });

            assertEquals(thrown.getMessage(),
                    "The privateValue key was not specified in transient data. Please try again.");
        }

    }

    @Nested
    class AssetReads {
        @Test
        public void privateAssetRead() throws UnsupportedEncodingException {

            when(stub.getPrivateData(collection, "001"))
                    .thenReturn(("{\"privateValue\":\"125\"}").getBytes(StandardCharsets.UTF_8));
            String expectedString = "{\"privateValue\":\"125\"}";

            String returnedAssetString = contract.readMyPrivateAsset(ctx, "001");
            assertEquals(expectedString, returnedAssetString);
        }

        @Test
        public void noSuchPrivateAsset() {
            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.readMyPrivateAsset(ctx, "002");
            });

            assertEquals(thrown.getMessage(), "The asset my private asset 002 does not exist");
        }
    }

    @Nested
    class AssetUpdates {
        @Test
        public void updateExisting() throws UnsupportedEncodingException {
            when(stub.getPrivateData(collection, "001"))
                    .thenReturn(("{\"privateValue\":\"125\"}").getBytes(StandardCharsets.UTF_8));
            Map<String, byte[]> transientMap = new HashMap<>();

            transientMap.put("privateValue", "150".getBytes(StandardCharsets.UTF_8));

            when(stub.getTransient()).thenReturn(transientMap);

            contract.updateMyPrivateAsset(ctx, "001");

            verify(stub).putPrivateData(collection, "001",
                    ("{\"privateValue\":\"150\"}").getBytes(StandardCharsets.UTF_8));
        }

        @Test
        public void updateMissing() {
            when(stub.getPrivateData(collection, "002")).thenReturn(null);

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.updateMyPrivateAsset(ctx, "002");
            });

            assertEquals(thrown.getMessage(), "The asset my private asset 002 does not exist");
        }

    }

    @Nested
    class AssetDelete {
        @Test
        public void deleteExisting() {
            contract.deleteMyPrivateAsset(ctx, "001");
            verify(stub).delPrivateData(collection, "001");
        }

        @Test
        public void deleteMissing() {
            when(stub.getPrivateData(collection, "002")).thenReturn(null);

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.deleteMyPrivateAsset(ctx, "002");
            });
            assertEquals(thrown.getMessage(), "The asset my private asset 002 does not exist");
        }
    }

    @Nested
    class AssetVerify {
        @Test
        public void verifyExistingCorrect() throws NoSuchAlgorithmException {
            MyPrivateAsset someAsset = new MyPrivateAsset();
            someAsset.privateValue = "125";

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashByte = digest.digest(someAsset.toJSONString().getBytes(StandardCharsets.UTF_8));

            when(stub.getPrivateDataHash(collection, "001")).thenReturn(hashByte);

            boolean result = contract.verifyMyPrivateAsset(ctx, "001", someAsset);

            assertTrue(result);
        }

        @Test
        public void verifyExistingIncorrect() throws NoSuchAlgorithmException {
            MyPrivateAsset someAsset = new MyPrivateAsset();
            someAsset.privateValue = "125";

            when(stub.getPrivateDataHash(collection, "001")).thenReturn(("someAsset").getBytes(StandardCharsets.UTF_8));

            boolean result = contract.verifyMyPrivateAsset(ctx, "001", someAsset);

            assertFalse(result);
        }

        @Test
        public void verifyMissing() throws NoSuchAlgorithmException {
            MyPrivateAsset someAsset = new MyPrivateAsset();

            when(stub.getPrivateDataHash(collection, "002")).thenReturn(("").getBytes(StandardCharsets.UTF_8));

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.verifyMyPrivateAsset(ctx, "002", someAsset);
            });

            assertEquals(thrown.getMessage(), "No private data hash with the key: 002");

        }
    }

}
