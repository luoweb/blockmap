/*
 *       Copyright© (2018-2019) blockmap Co., Ltd.
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
import com.blockmap.did.core.contract.fabric.v06.impl.AuthorityIssuerContract;
import com.blockmap.did.core.protocol.base.AuthorityIssuer;
import com.blockmap.did.core.protocol.request.RegisterAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.request.RemoveAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.protocol.response.TransactionInfo;
import com.blockmap.did.core.service.impl.engine.AuthorityIssuerServiceEngine;
import com.blockmap.did.core.util.DidUtils;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AuthorityIssuerEngine calls authority issuer contract which runs on FABRIC 2.0.
 *
 * @author admin@xuzhijun.com.cn 2019年12月25日
 */
public class AuthorityIssuerEngineV06 implements AuthorityIssuerServiceEngine {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityIssuerEngineV06.class);

    private static AuthorityIssuerContract authorityIssuerContract = new AuthorityIssuerContract();


    /* (non-Javadoc)
     * @see com.blockmap.did.core.service.impl.engine.AuthorityIssuerContract
     * #addAuthorityIssuer(com.blockmap.did.core.protocol.request.RegisterAuthorityIssuerArgs)
     */
    @Override
    public ResponseData<Boolean> addAuthorityIssuer(RegisterAuthorityIssuerArgs args) {
        Map response = authorityIssuerContract.addAuthorityIssuer(args.getAuthorityIssuer(), args.getDidPrivateKey());
        logger.info("[addAuthorityIssuer] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        TransactionInfo info = new TransactionInfo();
        if (isSuccess) {
            return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS, info);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[addAuthorityIssuer] add authority issuer failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
            return new ResponseData<>(Boolean.FALSE, ErrorCode.AUTHORITY_ISSUER_ERROR, info);
        }
    }

    @Override
    public ResponseData<Boolean> removeAuthorityIssuer(RemoveAuthorityIssuerArgs args) {
        String did = args.getDid();
        Map response = authorityIssuerContract.removeAuthorityIssuer(DidUtils.convertDidToAddress(did));
        logger.info("[removeAuthorityIssuer] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        TransactionInfo info = new TransactionInfo();
        if (isSuccess) {
            return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS, info);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[removeAuthorityIssuer] remove authority issuer failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
            return new ResponseData<>(Boolean.FALSE, ErrorCode.AUTHORITY_ISSUER_ERROR, info);
        }
    }


    /* (non-Javadoc)
     * @see com.blockmap.did.core.service.impl.engine.AuthorityIssuerContract
     * #isAuthorityIssuer(java.lang.String)
     */
    @Override
    public ResponseData<Boolean> isAuthorityIssuer(String address) {
        ResponseData<Boolean> resultData = new ResponseData<Boolean>();
        Boolean result = authorityIssuerContract.isAuthorityIssuer(address);
        resultData.setResult(result);
        if (result) {
            resultData.setErrorCode(ErrorCode.SUCCESS);
        } else {
            logger.error("check authority issuer id failed.", ErrorCode.AUTHORITY_ISSUER_CONTRACT_ERROR_NOT_EXISTS.getCodeDesc());
            resultData.setErrorCode(ErrorCode.AUTHORITY_ISSUER_CONTRACT_ERROR_NOT_EXISTS);
        }
        return resultData;
    }

    /* (non-Javadoc)
     * @see com.blockmap.did.core.service.impl.engine.AuthorityIssuerContract
     * #getAuthorityIssuerInfoNonAccValue(java.lang.String)
     */
    @Override
    public ResponseData<AuthorityIssuer> getAuthorityIssuerInfoNonAccValue(String did) {
        Map response = authorityIssuerContract.getAuthorityIssuerInfoNonAccValue(DidUtils.convertDidToAddress(did));
        logger.info("[getAuthorityIssuerInfoNonAccValue] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        TransactionInfo info = new TransactionInfo();
        if (isSuccess) {
            AuthorityIssuer result = new AuthorityIssuer();
            result.setDid(did);
            ResponseData<AuthorityIssuer> resultData = new ResponseData<AuthorityIssuer>();
            resultData.setResult(result);
            return resultData;
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[getAuthorityIssuerInfoNonAccValue] query authority issuer failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
            return new ResponseData<>(null, ErrorCode.AUTHORITY_ISSUER_ERROR);
        }
    }

    /* (non-Javadoc)
     * @see com.blockmap.did.core.service.impl.engine.AuthorityIssuerContract
     * #getAuthorityIssuerAddressList(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<String> getAuthorityIssuerAddressList(Integer index, Integer num) {
        List<String> didList = new ArrayList<>();
        Map response = authorityIssuerContract.getAuthorityIssuerAddressList(index, num);
        logger.info("[getAuthorityIssuerAddressList] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            JSONArray authorityIssuers = blockchainMessage.getJSONArray("authorityIssuers");
            List<AuthorityIssuer> auths = authorityIssuers.toJavaList(AuthorityIssuer.class);
            auths.forEach(authorityIssuer -> didList.add(authorityIssuer.getDid()));
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[getAuthorityIssuerAddressList] query authority issuer failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
        }
        return didList;
    }

    @Override
    public ResponseData<Boolean> removeIssuer(String issuerType, String issuerAddress, String privateKey) {
        Map response = authorityIssuerContract.removeIssuer(issuerType, issuerAddress);
        logger.info("[removeIssuer] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        TransactionInfo info = new TransactionInfo();
        if (isSuccess) {
            return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS, info);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[removeIssuer] remove issuer from type failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
            return new ResponseData<>(Boolean.FALSE, ErrorCode.AUTHORITY_ISSUER_ERROR);
        }
    }

    @Override
    public ResponseData<Boolean> isSpecificTypeIssuer(String issuerType, String address) {
        Boolean response = authorityIssuerContract.isSpecificTypeIssuer(issuerType, address);
        if (response) {
            return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS);
        } else {
            logger.error("check issuer type failed.", ErrorCode.SPECIFIC_ISSUER_CONTRACT_ERROR_ALREADY_NOT_EXIST.getCodeDesc());
            return new ResponseData<>(false, ErrorCode.SPECIFIC_ISSUER_CONTRACT_ERROR_ALREADY_NOT_EXIST);
        }
    }

    @Override
    public ResponseData<List<String>> getSpecificTypeIssuerList(String issuerType, Integer index, Integer num) {
        List<String> didList = new ArrayList<>();
        Map response = authorityIssuerContract.getSpecificTypeIssuerList(issuerType, index, num);
        logger.info("[getSpecificTypeIssuerList] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            JSONArray authorityIssuers = blockchainMessage.getJSONArray("authorityIssuers");
            List<AuthorityIssuer> auths = authorityIssuers.toJavaList(AuthorityIssuer.class);
            auths.forEach(authorityIssuer -> didList.add(authorityIssuer.getDid()));
           return new ResponseData<List<String>>(didList, ErrorCode.SUCCESS);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[getSpecificTypeIssuerList] check issuer type failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
            return new ResponseData<List<String>>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    @Override
    public ResponseData<Boolean> registerIssuerType(String issuerType, String privateKey) {
        Map response = authorityIssuerContract.registerIssuerType(issuerType);
        logger.info("[registerIssuerType] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        TransactionInfo info = new TransactionInfo();
        if (isSuccess) {
            return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS, info);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[registerIssuerType] register issuer type failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
            return new ResponseData<>(Boolean.FALSE, ErrorCode.AUTHORITY_ISSUER_ERROR);
        }
    }

    @Override
    public ResponseData<Boolean> addIssuer(String issuerType, String issuerAddress, String privateKey) {
        Map response = authorityIssuerContract.addIssuer(issuerType, issuerAddress);
        logger.info("[addIssuer] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        TransactionInfo info = new TransactionInfo();
        if (isSuccess) {
            return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS, info);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            logger.error("[addIssuer] add issuer into type failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
            return new ResponseData<>(Boolean.FALSE, ErrorCode.AUTHORITY_ISSUER_ERROR);
        }
    }

}