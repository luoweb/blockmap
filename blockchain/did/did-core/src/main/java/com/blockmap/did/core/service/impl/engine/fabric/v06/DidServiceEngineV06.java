/*
 *       Copyright© (2020) blockmap Co., Ltd.
 *
 *       This file is part of did-core.
 *
 *       did-core is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       did-core is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with did-core.  If not, see <https://www.gnu.org/licenses/>.
 */


package com.blockmap.did.core.service.impl.engine.fabric.v06;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockmap.did.core.constant.ContractConstant;
import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.contract.fabric.v06.impl.DidContract;
import com.blockmap.did.core.protocol.base.AuthenticationProperty;
import com.blockmap.did.core.protocol.base.DidDocument;
import com.blockmap.did.core.protocol.base.PublicKeyProperty;
import com.blockmap.did.core.protocol.base.ServiceProperty;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.impl.engine.DidServiceEngine;
import com.blockmap.did.core.util.DidUtils;
;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: DidServiceEngine calls the did com.blockmap.did.core.contract which runs on FABRIC 0.6 version.
 * @File: DidServiceEngineV06
 * @Version: 1.0.0
 * @Date: 2019/12/18 11:16
 */


public class DidServiceEngineV06 implements DidServiceEngine {

    private static final Logger logger = LoggerFactory.getLogger(DidServiceEngineV06.class);

    DidContract didContract = new DidContract();


    @Override
    public ResponseData<Boolean> createDid(String didAddress, String publicKey, String privateKey) {

        List<PublicKeyProperty> pps = new ArrayList<>();
        PublicKeyProperty publicKeyProperty = new PublicKeyProperty();
        publicKeyProperty.setId(publicKey);
        publicKeyProperty.setPublicKey(publicKey);
        publicKeyProperty.setOwner(DidUtils.convertAddressToDid(didAddress));
        pps.add(publicKeyProperty);

        List<AuthenticationProperty> aps = new ArrayList<>();
        AuthenticationProperty authenticationProperty = new AuthenticationProperty();
        authenticationProperty.setPublicKey(publicKey);
        aps.add(authenticationProperty);

        List<ServiceProperty> sps = new ArrayList<>();
        ServiceProperty serviceProperty = new ServiceProperty();
        serviceProperty.setServiceEndpoint("");
        serviceProperty.setType("");
        sps.add(serviceProperty);

        Map response = didContract.createDid(didAddress, pps, aps ,sps);
        logger.info("[createDid] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            return new ResponseData<Boolean>(true, ErrorCode.SUCCESS);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            if (blockchainMessage == null){
                JSONObject blockchainResponse = JSON.parseObject(response.get("blockchainResponse").toString());
                JSONObject result = JSON.parseObject(blockchainResponse.getString("result"));
                JSONObject error = JSON.parseObject(result.getString("error"));
                if(error.getString("code").equalsIgnoreCase("SYSERR01")){
                    logger.error("[createDid] createDid failed, because " + ErrorCode.TRANSACTION_TIMEOUT);
                    return new ResponseData<>(null, ErrorCode.TRANSACTION_TIMEOUT);
                }
                if (error.getInteger("code") == -32000){
                    logger.error("[createDid] createDid failed, because " + ErrorCode.USER_NOT_LOGIN);
                    return new ResponseData<>(null, ErrorCode.USER_NOT_LOGIN);
                }
                logger.error("[createDid] createDid failed, because " + ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
                return new ResponseData<>(null, ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
            }else {
                logger.error("[createDid] createDid failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
                return new ResponseData<>(null, ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")));
            }
        }
    }

    @Override
    public ResponseData<Boolean> setAttribute(String didAddress, String attributeKey, String value, String privateKey) {
        Map response = didContract.setAttribute(didAddress, attributeKey, value);
        logger.info("[setAttribute] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            return new ResponseData<Boolean>(true, ErrorCode.SUCCESS);
        } else {
            logger.error("[setAttribute] set did attribute failed because com.blockmap.did.core.contract invoke error.");
            return new ResponseData<>(false, ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
        }
    }

//    @Override
//    public ResponseData<Boolean> isDidExist(String did) {
//        return new ResponseData<>(didContract.isIdentityExist(did), ErrorCode.SUCCESS);
//    }
    @Override
    public ResponseData<Boolean> isDidExist(String did) {
        Map response = didContract.isIdentityExist(did);
        logger.info("[isIdentityExist] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            return new ResponseData<>(true, ErrorCode.SUCCESS);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            if (blockchainMessage == null){
                JSONObject blockchainResponse = JSON.parseObject(response.get("blockchainResponse").toString());
                JSONObject result = JSON.parseObject(blockchainResponse.getString("result"));
                JSONObject error = JSON.parseObject(result.getString("error"));
                if(error.getString("code").equalsIgnoreCase("SYSERR01")){
                    logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.TRANSACTION_TIMEOUT);
                    return new ResponseData<>(false, ErrorCode.TRANSACTION_TIMEOUT);
                }
                if (error.getInteger("code") == -32000){
                    logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.USER_NOT_LOGIN);
                    return new ResponseData<>(false, ErrorCode.USER_NOT_LOGIN);
                }
                logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
                return new ResponseData<>(false, ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
            }else {
                logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
                return new ResponseData<>(false, ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")));
            }
        }
    }

    @Override
    public ResponseData<DidDocument> getDidDocument(String did) {
        DidDocument didDocument = new DidDocument();
        Map response = didContract.getDidDocument(did);
        logger.info("[getDidDocument] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            didDocument.setId(DidUtils.convertAddressToDid(did));
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            JSONObject queryResult = blockchainMessage.getJSONObject("queryResult");
            didDocument.setCreated(queryResult.getLong("created"));
            didDocument.setUpdated(queryResult.getLong("updated"));

            JSONArray  authentications = queryResult.getJSONArray("authentications");
            List<AuthenticationProperty> aps = authentications.toJavaList(AuthenticationProperty.class);
            didDocument.setAuthentication(aps);

            JSONArray  publicKeys = queryResult.getJSONArray("publicKeys");
            List<PublicKeyProperty> pps = publicKeys.toJavaList(PublicKeyProperty.class);
            didDocument.setPublicKey(pps);

            return new ResponseData<>(didDocument, ErrorCode.SUCCESS);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            if (blockchainMessage == null){
                JSONObject blockchainResponse = JSON.parseObject(response.get("blockchainResponse").toString());
                JSONObject result = JSON.parseObject(blockchainResponse.getString("result"));
                JSONObject error = JSON.parseObject(result.getString("error"));
                if(error.getString("code").equalsIgnoreCase("SYSERR01")){
                    logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.TRANSACTION_TIMEOUT);
                    return new ResponseData<>(null, ErrorCode.TRANSACTION_TIMEOUT);
                }
                if (error.getInteger("code") == -32000){
                    logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.USER_NOT_LOGIN);
                    return new ResponseData<>(null, ErrorCode.USER_NOT_LOGIN);
                }
                logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
                return new ResponseData<>(null, ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
            }else {
                logger.error("[getDidDocument] getDidDocument failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
                return new ResponseData<>(null, ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")));
            }
        }
    }

}
