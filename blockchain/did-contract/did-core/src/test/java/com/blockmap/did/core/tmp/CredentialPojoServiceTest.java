package com.blockmap.did.core.tmp;

import com.blockmap.did.core.protocol.base.ClaimPolicy;
import com.blockmap.did.core.protocol.base.CredentialPojo;
import com.blockmap.did.core.protocol.base.DidAuthentication;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.request.CreateCredentialPojoArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.CredentialPojoService;
import com.blockmap.did.core.service.impl.CredentialPojoServiceImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CredentialPojoServiceTest {

    @Test
    public void createCredential() {
    }

    @Test
    public void prepareZkpCredential() {
    }

    @Test
    public void createSelectiveCredential() {
        CredentialPojoService credentialPojoService = new CredentialPojoServiceImpl();
        CreateCredentialPojoArgs<Map<String, Object>> createCredentialPojoArgs =
                new CreateCredentialPojoArgs<Map<String, Object>>();
        createCredentialPojoArgs.setCptId(1017);
        createCredentialPojoArgs
                .setIssuer("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        createCredentialPojoArgs
                .setExpirationDate(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 100);

       DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey(
                "60866441986950167911324536025850958917764441489874006048340539971987791929772");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        didAuthentication
                .setDidPublicKeyId("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7#key0");
        createCredentialPojoArgs.setDidAuthentication(didAuthentication);

        Map<String, Object> claim = new HashMap<String, Object>();
        claim.put("name", "zhangsan");
        claim.put("gender", "F");
        claim.put("age", 22);
        createCredentialPojoArgs.setClaim(claim);

        ResponseData<CredentialPojo> response =
                credentialPojoService.createCredential(createCredentialPojoArgs);

        // 选择性披露
        ClaimPolicy claimPolicy = new ClaimPolicy();
        claimPolicy.setFieldsToBeDisclosed("{\"name\":1,\"gender\":0,\"age\":1}");
        ResponseData<CredentialPojo> selectiveResponse =
                credentialPojoService.createSelectiveCredential(response.getResult(), claimPolicy);

        System.out.println(selectiveResponse);
    }

    @Test
    public void addSignature() {
    }

    @Test
    public void getCredentialPojoHash() {
    }

    @Test
    public void verify() {
    }

    @Test
    public void verify1() {
    }

    @Test
    public void verify2() {
    }

    @Test
    public void createPresentation() {
    }

    @Test
    public void createTrustedTimestamp() {
    }
}