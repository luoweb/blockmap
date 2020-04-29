package com.blockmap.did.core.tmp;

import com.blockmap.did.core.protocol.base.Credential;
import com.blockmap.did.core.protocol.base.CredentialWrapper;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.request.CreateCredentialArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.CredentialService;
import com.blockmap.did.core.service.impl.CredentialServiceImpl;
import org.junit.Test;

import java.util.HashMap;

public class CredentialServiceTest {

    @Test
    public void createCredential() {
        CredentialService credentialService = new CredentialServiceImpl();

//        HashMap<String, Object> claim = new HashMap<String, Object>(3);
//        claim.put("name", "zhang san");
//        claim.put("id", "10000");
//        claim.put("gender", "F");
//        claim.put("age", 18);
//
//        CreateCredentialArgs createCredentialArgs = new CreateCredentialArgs();
//        createCredentialArgs.setClaim(claim);
//        createCredentialArgs.setCptId(103);
//        createCredentialArgs.setExpirationDate(1851448312461L);
//        createCredentialArgs.setIssuer("did:blockmapid:101:0x14ecbe3132e65e6203255595d1e4e48ad22a0b80");
//
//        DidPrivateKey didPrivateKey = new DidPrivateKey();
//        didPrivateKey.setPrivateKey("80881103349160766647835385061087138242118126915444039157301610793919703550746");

        HashMap<String, Object> claim = new HashMap<String, Object>(3);
        claim.put("name", "gfdgdfg");
        claim.put("gender", "F");
        claim.put("age", "10");
        CreateCredentialArgs createCredentialArgs = new CreateCredentialArgs();
        createCredentialArgs.setClaim(claim);
        createCredentialArgs.setCptId(103);
        createCredentialArgs.setExpirationDate(1851448312461L);
        createCredentialArgs.setIssuer("did:blockmapid:101:0x40d1a3b5ba4e3e5e5d07691f4c354eb5c241b544");
        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("57057203537529168669894331826659936672614685324072058636870339393635375446236");

        createCredentialArgs.setDidPrivateKey(didPrivateKey);
//        createCredentialArgs.setIssuanceDate(1100l);
        ResponseData<CredentialWrapper> response = credentialService.createCredential(null);

        System.out.println(response);
    }

    @Test
    public void addSignature() {
    }

    @Test
    public void createSelectiveCredential() {
    }

    @Test
    public void verify() {
        CredentialService credentialService = new CredentialServiceImpl();

        HashMap<String, Object> claim = new HashMap<String, Object>(3);
        claim.put("name", "gfdgdfg");
        claim.put("gender", "F");
        claim.put("age", "-10");

        CreateCredentialArgs createCredentialArgs = new CreateCredentialArgs();
        createCredentialArgs.setClaim(claim);
        createCredentialArgs.setCptId(103);
        createCredentialArgs.setExpirationDate(1851448312461L);
        createCredentialArgs.setIssuer("did:blockmapid:101:0xeea3aea8f68f68f5473dbdd665fca165264e67c8");


        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("100306882754039443041408575175535095866238234499282939533514544003418728606681");

        createCredentialArgs.setDidPrivateKey(didPrivateKey);
        //创建Credential
        ResponseData<CredentialWrapper> response = credentialService.createCredential(createCredentialArgs);
        //修改Credential信息
//        Credential credential = response.getResult().getCredential();
        Credential credential = null;
//        credential.setClaim(new HashMap<>());
//        credential.setContext(xxx);
//        credential.setExpirationDate(xxxx);
//        credential.setCptId(-12);
//        credential.setIssuanceDate(xxx);
//        credential.setIssuer(xxxx);q
//        Map proof = credential.getProof();
//        proof.put("type","xxx");
//        credential.setProof(proof);
        //验证Credential
        ResponseData<Boolean> responseVerify = credentialService.verify(credential);

        System.out.println(responseVerify);
    }

    @Test
    public void verify1() {
    }

    @Test
    public void verifyCredentialWithSpecifiedPubKey() {
    }

    @Test
    public void getCredentialHash() {
        CredentialService credentialService = new CredentialServiceImpl();

        HashMap<String, Object> claim = new HashMap<String, Object>(3);
        claim.put("name", "zhang san");
        claim.put("gender", "F");
        claim.put("age", 18);

        CreateCredentialArgs createCredentialArgs = new CreateCredentialArgs();
        createCredentialArgs.setClaim(claim);
        createCredentialArgs.setCptId(1017);
        createCredentialArgs.setExpirationDate(1861448312461L);
        createCredentialArgs.setIssuer("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        createCredentialArgs.setDidPrivateKey(didPrivateKey);

        //创建Credentia
        ResponseData<CredentialWrapper> response = credentialService.createCredential(createCredentialArgs);

        //获取Credentia的Hash
        ResponseData<String> responseHash = credentialService.getCredentialHash(response.getResult().getCredential());

        System.out.println(responseHash);
    }

    @Test
    public void getCredentialHash1() {
    }

    @Test
    public void getCredentialJson() {
    }
}