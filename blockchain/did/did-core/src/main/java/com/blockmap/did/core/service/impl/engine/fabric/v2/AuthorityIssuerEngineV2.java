package com.blockmap.did.core.service.impl.engine.fabric.v2;

import com.blockmap.did.core.protocol.base.AuthorityIssuer;
import com.blockmap.did.core.protocol.request.RegisterAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.request.RemoveAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.impl.engine.AuthorityIssuerServiceEngine;

import java.util.List;

public class AuthorityIssuerEngineV2 implements AuthorityIssuerServiceEngine {

    @Override
    public ResponseData<Boolean> addAuthorityIssuer(RegisterAuthorityIssuerArgs args) {
        return null;
    }

    @Override
    public ResponseData<Boolean> removeAuthorityIssuer(RemoveAuthorityIssuerArgs args) {
        return null;
    }

    @Override
    public ResponseData<Boolean> isAuthorityIssuer(String address) {
        return null;
    }

    @Override
    public ResponseData<AuthorityIssuer> getAuthorityIssuerInfoNonAccValue(String did) {
        return null;
    }

    @Override
    public List<String> getAuthorityIssuerAddressList(Integer index, Integer num) {
        return null;
    }

    @Override
    public ResponseData<Boolean> removeIssuer(String issuerType, String issuerAddress, String privateKey) {
        return null;
    }

    @Override
    public ResponseData<Boolean> isSpecificTypeIssuer(String issuerType, String address) {
        return null;
    }

    @Override
    public ResponseData<List<String>> getSpecificTypeIssuerList(String issuerType, Integer index, Integer num) {
        return null;
    }

    @Override
    public ResponseData<Boolean> registerIssuerType(String issuerType, String privateKey) {
        return null;
    }

    @Override
    public ResponseData<Boolean> addIssuer(String issuerType, String issuerAddress, String privateKey) {
        return null;
    }
}
