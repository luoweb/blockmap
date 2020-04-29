package com.blockmap.did.core.tmp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockmap.did.core.protocol.base.DidDocument;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.request.CreateDidArgs;
import com.blockmap.did.core.protocol.request.SetAuthenticationArgs;
import com.blockmap.did.core.protocol.request.SetPublicKeyArgs;
import com.blockmap.did.core.protocol.request.SetServiceArgs;
import com.blockmap.did.core.protocol.response.CreateDidDataResult;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.DidService;
import com.blockmap.did.core.service.impl.DidServiceImpl;
import org.junit.Test;

public class DidServiceTest {

    @Test
    public void createDid() {
        DidService didService = new DidServiceImpl();
        ResponseData<CreateDidDataResult> responseData = didService.createDid();
        System.out.println(responseData);
    }

    @Test
    public void testCreateDid() {
        DidService didService = new DidServiceImpl();

        CreateDidArgs createDidArgs = new CreateDidArgs();
        createDidArgs.setPublicKey(
                "2905679808560626772263712571437125497429146398815877180317365034921958007199576809718056336050058032599743534507469742764670961100255274766148096681073592");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("7581560237967740187496354914151086729152742173850631851769274217992481997665");

        createDidArgs.setDidPrivateKey(didPrivateKey);

        ResponseData<String> response = didService.createDid(createDidArgs);
    }

    @Test
    public void getDidDocumentJson() {
        DidService didService = new DidServiceImpl();
        ResponseData<String> response = didService.getDidDocumentJson("did:blockmapid:101:0x479f981b732699686e2489b0b0fe95b9e7eda4b9qq");
        System.out.println(response);
    }

    @Test
    public void getDidDocument() {
        DidService didService = new DidServiceImpl();
        ResponseData<DidDocument> response = didService.getDidDocument("did:blockmapid:101:0xd9aeaa982fc21ea9addaf09e4f0c6a23a08d306a");
    }

    @Test
    public void setPublicKey() {
        DidService didService = new DidServiceImpl();

        SetPublicKeyArgs setPublicKeyArgs = new SetPublicKeyArgs();
        setPublicKeyArgs.setDid("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        setPublicKeyArgs.setPublicKey(
                "13161444623157635919577071263152435729269604287924587017945158373362984739390835280704888860812486081963832887336483721952914804189509503053687001123007342");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        setPublicKeyArgs.setUserDidPrivateKey(didPrivateKey);

        ResponseData<Boolean> response = didService.setPublicKey(setPublicKeyArgs);
    }

    @Test
    public void setService() {
        DidService didService = new DidServiceImpl();

        SetServiceArgs setServiceArgs = new SetServiceArgs();
        setServiceArgs.setDid("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        setServiceArgs.setType("drivingCardService");
        setServiceArgs.setServiceEndpoint("https://blockmapidentity.webank.com/endpoint/8377464");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        setServiceArgs.setUserDidPrivateKey(didPrivateKey);

        ResponseData<Boolean> response = didService.setService(setServiceArgs);
    }

    @Test
    public void setAuthentication() {
        DidService didService = new DidServiceImpl();

        SetAuthenticationArgs setAuthenticationArgs = new SetAuthenticationArgs();
        setAuthenticationArgs.setDid("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        setAuthenticationArgs.setPublicKey(
                "13161444623157635919577071263152435729269604287924587017945158373362984739390835280704888860812486081963832887336483721952914804189509503053687001123007342");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        setAuthenticationArgs.setUserDidPrivateKey(didPrivateKey);

        ResponseData<Boolean> response = didService.setAuthentication(setAuthenticationArgs);
    }

    @Test
    public void isDidExist() {
        DidService didService = new DidServiceImpl();
        ResponseData<Boolean> response = didService.isDidExist("did:blockmapid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
    }


    @Test
    public void test() {
        
        JSONObject jsonObject = JSON.parseObject("{\"publicKeys\":\"[{\"id\":\"\",\"owner\":\"0x1f145818139112e1901028fa254f39856e22416d\",\"publicKey\":\"12370102686437304173311845212302061731200604889596132102102567375513106600203312947111316795315257174061099219935406539385371444962016095193511434730131386\",\"type\":\"Secp256k1\"}]\",\"id\":\"0x1f145818139112e1901028fa254f39856e22416d\",\"services\":\"[{\"serviceEndpoint\":\"\",\"type\":\"\"}]\",\"authentications\":\"[{\"publicKey\":\"12370102686437304173311845212302061731200604889596132102102567375513106600203312947111316795315257174061099219935406539385371444962016095193511434730131386\",\"type\":\"Secp256k1\"}]\"}");

        System.out.println(jsonObject);
    }
}