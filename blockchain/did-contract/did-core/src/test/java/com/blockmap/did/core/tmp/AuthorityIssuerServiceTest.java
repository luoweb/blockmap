package com.blockmap.did.core.tmp;

import com.blockmap.did.core.protocol.base.AuthorityIssuer;
import com.blockmap.did.core.protocol.base.DidAuthentication;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.request.RegisterAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.request.RemoveAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.AuthorityIssuerService;
import com.blockmap.did.core.service.impl.AuthorityIssuerServiceImpl;
import org.junit.Test;

import java.util.List;

public class AuthorityIssuerServiceTest {

    @Test
    public void registerAuthorityIssuer() {
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        AuthorityIssuer authorityIssuer = new AuthorityIssuer();
        authorityIssuer.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        authorityIssuer.setName("webank1");
        authorityIssuer.setAccValue("0");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("36162289879206412028682370838615850457668262092955617990245744195910144330785");

        RegisterAuthorityIssuerArgs registerAuthorityIssuerArgs = new RegisterAuthorityIssuerArgs();
        registerAuthorityIssuerArgs.setAuthorityIssuer(authorityIssuer);
        registerAuthorityIssuerArgs.setDidPrivateKey(didPrivateKey);

        ResponseData<Boolean> response = authorityIssuerService.registerAuthorityIssuer(registerAuthorityIssuerArgs);
    }

    @Test
    public void removeAuthorityIssuer() {
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("36162289879206412028682370838615850457668262092955617990245744195910144330785");

        RemoveAuthorityIssuerArgs removeAuthorityIssuerArgs = new RemoveAuthorityIssuerArgs();
        removeAuthorityIssuerArgs.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        removeAuthorityIssuerArgs.setDidPrivateKey(didPrivateKey);

        ResponseData<Boolean> response = authorityIssuerService.removeAuthorityIssuer(removeAuthorityIssuerArgs);
    }

    @Test
    public void isAuthorityIssuer() {
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();
        String did = "did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7";
        ResponseData<Boolean> response = authorityIssuerService.isAuthorityIssuer(did);
    }

    @Test
    public void queryAuthorityIssuerInfo() {
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();
        String weId = "did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7";
        ResponseData<AuthorityIssuer> response = authorityIssuerService.queryAuthorityIssuerInfo(weId);
    }

    @Test
    public void getAllAuthorityIssuerList() {
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();
        ResponseData<List<AuthorityIssuer>> response = authorityIssuerService.getAllAuthorityIssuerList(0, 2);
    }

    @Test
    public void registerIssuerType() {
        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        didAuthentication.setDidPublicKeyId("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7#key0");
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();
        ResponseData<Boolean> response = authorityIssuerService.registerIssuerType(didAuthentication, "College");
    }

    @Test
    public void addIssuerIntoIssuerType() {
        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        didAuthentication.setDidPublicKeyId("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7#key0");
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();
        ResponseData<Boolean> response = authorityIssuerService.addIssuerIntoIssuerType(didAuthentication, "College", "did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
    }

    @Test
    public void removeIssuerFromIssuerType() {
        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        didAuthentication.setDidPublicKeyId("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7#key0");
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();
        ResponseData<Boolean> response = authorityIssuerService.removeIssuerFromIssuerType(didAuthentication, "College", "did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
    }

    @Test
    public void isSpecificTypeIssuer() {
        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();
        String did = "did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7";
        ResponseData<Boolean> response = authorityIssuerService.isAuthorityIssuer(did);
    }

    @Test
    public void getAllSpecificTypeIssuerList() {
    }
}