/*
 * SPDX-License-Identifier: Apache-2.0
 */
package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Contract(name = "MyPrivateAssetContract",
    info = @Info(title = "MyPrivateAsset contract",
                description = "My Private Data Smart Contract",
                version = "0.0.1",
                license =
                        @License(name = "Apache-2.0",
                                url = ""),
                                contact =  @Contact(email = "private_data_java@example.com",
                                                name = "private_data_java",
                                                url = "http://private_data_java.me")))
@Default
public class MyPrivateAssetContract implements ContractInterface {
    String collection = "CollectionOne";

    public  MyPrivateAssetContract() {

    }
    @Transaction()
    public boolean myPrivateAssetExists(Context ctx, String myPrivateAssetId) {
        byte[] buffer = ctx.getStub().getPrivateDataHash(collection, myPrivateAssetId);
        return (buffer != null && buffer.length > 0);
    }

    @Transaction()
    public void createMyPrivateAsset(Context ctx, String myPrivateAssetId) throws UnsupportedEncodingException {
        boolean exists = myPrivateAssetExists(ctx,myPrivateAssetId);
        if (exists) {
            throw new RuntimeException("The asset my private asset "+myPrivateAssetId+" already exists");
        }
        MyPrivateAsset privateAsset = new MyPrivateAsset();
        Map<String, byte[]> transientData = ctx.getStub().getTransient();

        if (transientData.size() == 0 | !transientData.containsKey("privateValue")) {
            throw new RuntimeException("The privateValue key was not specified in transient data. Please try again.");
        }
        privateAsset.privateValue = new String(transientData.get("privateValue"), "UTF-8");

        ctx.getStub().putPrivateData(collection, myPrivateAssetId, privateAsset.toJSONString().getBytes(StandardCharsets.UTF_8));
    }

    @Transaction()
    public String readMyPrivateAsset(Context ctx, String myPrivateAssetId) throws UnsupportedEncodingException {
        boolean exists = myPrivateAssetExists(ctx, myPrivateAssetId);
        if (!exists) {
            throw new RuntimeException("The asset my private asset " + myPrivateAssetId + " does not exist");
        }

        byte[] privateData = ctx.getStub().getPrivateData(collection, myPrivateAssetId);
        String privateDataString = new String(privateData, "UTF-8");

        return privateDataString;
    }

    @Transaction()
    public void updateMyPrivateAsset(Context ctx, String myPrivateAssetId) throws UnsupportedEncodingException {
        boolean exists = myPrivateAssetExists(ctx, myPrivateAssetId);
        if (!exists) {
            throw new RuntimeException("The asset my private asset " + myPrivateAssetId + " does not exist");
        }
        MyPrivateAsset privateAsset = new MyPrivateAsset();

        Map<String, byte[]> transientData = ctx.getStub().getTransient();

        if (transientData.size() == 0 | !transientData.containsKey("privateValue")) {
            throw new RuntimeException("The privateValue key was not specified in transient data. Please try again.");
        }
        privateAsset.privateValue = new String(transientData.get("privateValue"), "UTF-8");

        ctx.getStub().putPrivateData(collection, myPrivateAssetId, privateAsset.toJSONString().getBytes(StandardCharsets.UTF_8));
    }

    @Transaction()
    public void deleteMyPrivateAsset(Context ctx, String myPrivateAssetId) {
        boolean exists = myPrivateAssetExists(ctx, myPrivateAssetId);
        if (!exists) {
            throw new RuntimeException("The asset my private asset " + myPrivateAssetId + " does not exist");
        }
        ctx.getStub().delPrivateData(collection, myPrivateAssetId);
    }

    @Transaction()
    public boolean verifyMyPrivateAsset(Context ctx, String myPrivateAssetId, MyPrivateAsset objectToVerify) throws NoSuchAlgorithmException {

        // Convert user provided object into hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashByte = digest.digest(objectToVerify.toJSONString().getBytes(StandardCharsets.UTF_8));

        String hashToVerify = new BigInteger(1, hashByte).toString(16);

        // Get hash stored on the public ledger
        byte[] pdHashBytes = ctx.getStub().getPrivateDataHash(collection, myPrivateAssetId);
        if (pdHashBytes.length == 0) {
            throw new RuntimeException("No private data hash with the key: " + myPrivateAssetId);
        }

        String actualHash = new BigInteger(1, pdHashBytes).toString(16);

        if (hashToVerify.equals(actualHash)) {
            return true;
        } else {
            return false;
        }
    }

}
