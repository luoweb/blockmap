/*
 *       Copyright© (2020) ICBC Co., Ltd.
 *
 *       This file is part of did-http-service.
 *
 *       did-http-service is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       did-http-service is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with did-http-service.  If not, see <https://www.gnu.org/licenses/>.
 */


package com.icbc.did.httpservice.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbc.did.core.constant.CredentialConstant;
import com.icbc.did.core.constant.ErrorCode;
import com.icbc.did.core.constant.ParamKeyConstant;
import com.icbc.did.core.protocol.base.Credential;
import com.icbc.did.core.protocol.base.DidPrivateKey;
import com.icbc.did.core.protocol.request.CreateCredentialArgs;
import com.icbc.did.core.protocol.response.ResponseData;
import com.icbc.did.core.service.CredentialService;
import com.icbc.did.core.service.impl.CredentialServiceImpl;
import com.icbc.did.core.suite.api.persistence.Persistence;
import com.icbc.did.core.suite.persistence.sql.driver.MysqlDriver;
import com.icbc.did.core.util.CredentialUtils;
import com.icbc.did.core.util.DateUtils;
import com.icbc.did.httpservice.constant.DidParamKeyConstant;
import com.icbc.did.httpservice.constant.HttpReturnCode;
import com.icbc.did.httpservice.protocol.base.Cache4QrCode;
import com.icbc.did.httpservice.protocol.base.QrCode;
import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import com.icbc.did.httpservice.service.BaseService;
import com.icbc.did.httpservice.service.InvokerCredentialService;
import com.icbc.did.httpservice.util.JsonUtil;
import com.icbc.did.httpservice.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description:
 * @File: InvokerCredentialServiceImpl
 * @Version: 1.0.0
 * @Date: 2020/1/6 21:52
 */

@Component("invokerCredentialServiceImpl")
public class InvokerCredentialServiceImpl extends BaseService implements InvokerCredentialService {

    private Logger logger = LoggerFactory.getLogger(InvokerCredentialServiceImpl.class);

    private CredentialService credentialService = new CredentialServiceImpl();


    private Persistence dataDriver;

    private Persistence getDataDriver() {
        if (dataDriver == null) {
            dataDriver = new MysqlDriver();
        }
        return dataDriver;
    }

    @Override
    public HttpResponseData<Object> getCredentialDetailInvoke(InputArg getCredentialDetailFuncArgs) {
        Credential credential = null;
        try {
            credential = (Credential) JsonUtil.jsonStrToObj(new Credential(), getCredentialDetailFuncArgs.getFunctionArg());
        } catch (Exception e) {
            // The input credential is a json, so keep moving down.
            logger.info("Detected Portable-Json format credential, continuing..");
        }

        if (credential == null) {
            try {
                Map<String, Object> credMap = (Map<String, Object>) JsonUtil.jsonStrToObj(new HashMap<String, Object>(), getCredentialDetailFuncArgs.getFunctionArg());
                credMap = JsonUtil.reformatCredentialPojoToJson(credMap);
                credential = (Credential) JsonUtil.jsonStrToObj(new Credential(), JsonUtil.mapToCompactJson(credMap));
            } catch (Exception e) {
                logger.error("Input credential format illegal: {}", getCredentialDetailFuncArgs);
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_ILLEGAL.getCode(),
                        HttpReturnCode.INPUT_ILLEGAL.getCodeDesc().concat(e.getMessage()));
            }
        }
        ResponseData responseData = this.getDataDriver().get("domain.credential", credential.getId());
        return new HttpResponseData<>(JSON.parseObject(responseData.getResult().toString()), responseData.getErrorCode(), responseData.getErrorMessage());
    }


    @Override
    public HttpResponseData<Object> getAllCredentialInvoke(InputArg getAllCredentialFuncArgs) {
        String invokerDid = JSON.parseObject(getAllCredentialFuncArgs.getFunctionArg()).get("invokerDid").toString();
        ResponseData responseData = this.getDataDriver().getAll("domain.credential", invokerDid);
        if (responseData.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
            return new HttpResponseData<>(responseData.getResult(), responseData.getErrorCode(), responseData.getErrorMessage());
        }
        List<String> dataList = (List<String>) responseData.getResult();
        JSONArray jsonArray = new JSONArray();
        for (String data : dataList) {
            JSONObject jsonObject = JSON.parseObject(data);
            jsonArray.add(jsonObject);
        }
        return new HttpResponseData<>(jsonArray, responseData.getErrorCode(), responseData.getErrorMessage());
    }

    @Override
    public HttpResponseData<Object> getCredentialsByQrCodeIdInvoke(InputArg getCredentialByQrCodeId) {
        String qrCodeId = JSON.parseObject(getCredentialByQrCodeId.getFunctionArg()).get("id").toString();
        QrCode qrCode = Cache4QrCode.getQrCodeById(qrCodeId);
        if (qrCode == null || !Cache4QrCode.isActive(qrCode)) {
            return new HttpResponseData<>(false, -1, "QR code is unavailable");
        }
        JSONArray jsonArray = new JSONArray();
        for (String credentialId : qrCode.getCredentialIds()) {
            ResponseData responseData = this.getDataDriver().get("domain.credential", credentialId);
            if (responseData.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
                return new HttpResponseData<>(responseData.getResult(), responseData.getErrorCode(), responseData.getErrorMessage());
            }
            JSONObject jsonObject = JSON.parseObject(responseData.getResult().toString());
            jsonArray.add(jsonObject);
        }
        Cache4QrCode.inActive(qrCode);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("functionArg",jsonArray);
        return verifyCredentialByJson(jsonObject);
    }

    /**
     * Generate a credential for client to sign. The signature field is null, and both full claim
     * and claimHash will be returned. The returned json String is an key-ordered compact json.
     *
     * @param createCredentialFuncArgs the functionArgs
     * @return the Map contains Credential content and claimHash.
     */
    @Override
    public HttpResponseData<Object> createCredentialInvoke(InputArg createCredentialFuncArgs) {
        try {
            JsonNode functionArgNode = new ObjectMapper().readTree(createCredentialFuncArgs.getFunctionArg());
            JsonNode cptIdNode = functionArgNode.get(ParamKeyConstant.CPT_ID);
            JsonNode issuerNode = functionArgNode.get(ParamKeyConstant.ISSUER);
            JsonNode expirationDateNode = functionArgNode.get(ParamKeyConstant.EXPIRATION_DATE);
            JsonNode claimNode = functionArgNode.get(ParamKeyConstant.CLAIM);
            if (cptIdNode == null || StringUtils.isEmpty(cptIdNode.toString())
                    || issuerNode == null || StringUtils.isEmpty(issuerNode.textValue())
                    || expirationDateNode == null || StringUtils.isEmpty(expirationDateNode.textValue())
                    || claimNode == null || StringUtils.isEmpty(claimNode.toString())) {
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_NULL);
            }

            Integer cptId;
            try {
                cptId = Integer.valueOf(JsonUtil.removeDoubleQuotes(cptIdNode.toString()));
            } catch (Exception e) {
                return new HttpResponseData<>(null, HttpReturnCode.VALUE_FORMAT_ILLEGAL);
            }

            Long expirationDate;
            try {
                expirationDate = DateUtils.convertUtcDateToTimeStamp(expirationDateNode.textValue());
            } catch (Exception e) {
                return new HttpResponseData<>(null,
                        ErrorCode.CREDENTIAL_EXPIRE_DATE_ILLEGAL.getCode(),
                        ErrorCode.CREDENTIAL_EXPIRE_DATE_ILLEGAL.getCodeDesc());
            }

            Credential credential = new Credential();
            credential.setId(UUID.randomUUID().toString());
            credential.setCptId(cptId);
            credential.setIssuer(issuerNode.textValue());
            credential.setExpirationDate(expirationDate);
            credential.setContext(CredentialConstant.DEFAULT_CREDENTIAL_CONTEXT);
            // Now here is a trick - timestamp granularity is too "fine". Need to make it coarse.
            Long issuanceDate = DateUtils.convertUtcDateToTimeStamp(DateUtils.convertTimestampToUtc(DateUtils.getCurrentTimeStamp()));
            credential.setIssuanceDate(issuanceDate);
            Map<String, Object> claimMap;
            try {
                claimMap = (Map<String, Object>) JsonUtil.jsonStrToObj(new HashMap<String, Object>(), claimNode.toString());
            } catch (Exception e) {
                return new HttpResponseData<>(null,
                        ErrorCode.CREDENTIAL_CLAIM_DATA_ILLEGAL.getCode(),
                        ErrorCode.CREDENTIAL_CLAIM_DATA_ILLEGAL.getCodeDesc());
            }
            credential.setClaim(claimMap);

            // check validity 1st round: the create args with an arbitrary private key
            DidPrivateKey didPrivateKey = new DidPrivateKey();
            didPrivateKey.setPrivateKey("111111");
            CreateCredentialArgs args = CredentialUtils.extractCredentialMetadata(credential);
            args.setDidPrivateKey(didPrivateKey);
            ErrorCode errorCode = CredentialUtils.isCreateCredentialArgsValid(args);
            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                return new HttpResponseData<>(null, errorCode.getCode(), errorCode.getCodeDesc());
            }

            JsonNode txnArgNode = new ObjectMapper().readTree(createCredentialFuncArgs.getTransactionArg());
            JsonNode keyIndexNode = txnArgNode.get(DidParamKeyConstant.KEY_INDEX);

            // Decide the key holding mechanism
            if (keyIndexNode == null || StringUtils.isEmpty(keyIndexNode.textValue())) {

                // this is the client-storage privkey approach
                String claimHash = CredentialUtils.getClaimHash(credential, null);
                // Construct return value - a middle term
                Map<String, Object> credMap = JsonUtil.objToMap(credential);
                credMap.put(DidParamKeyConstant.CLAIM_HASH, claimHash);
                HttpResponseData responseData = new HttpResponseData<>(
                        JsonUtil.convertJsonToSortedMap(JsonUtil.mapToCompactJson(credMap)),
                        HttpReturnCode.SUCCESS);
                //保存credential到数据库
                this.getDataDriver().save("domain.credential", credential.getId(), JsonUtil.objToJsonStr(credential), null);
                return responseData;
            } else {
                // this is the server-hosting privkey approach
                String privateKey = KeyUtil.getPrivateKeyByDid(KeyUtil.SDK_PRIVKEY_PATH, keyIndexNode.textValue());
                if (StringUtils.isEmpty(privateKey)) {
                    return new HttpResponseData<>(null, HttpReturnCode.INVOKER_ILLEGAL);
                }
                Map<String, String> credentialProof = CredentialUtils.buildCredentialProof(credential, privateKey, null);
                credential.setProof(credentialProof);

                // check validity 2nd round
                errorCode = CredentialUtils.isCredentialValid(credential);
                if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                    return new HttpResponseData<>(null, errorCode.getCode(), errorCode.getCodeDesc());
                }
                ResponseData<String> response = credentialService.getCredentialJson(credential);
                HttpResponseData responseData = new HttpResponseData<>(
                        JsonUtil.convertJsonToSortedMap(response.getResult()),
                        response.getErrorCode(), response.getErrorMessage());
                //保存credential到数据库
                this.getDataDriver().save("domain.credential", credential.getId(), JsonUtil.objToJsonStr(credential), keyIndexNode.textValue());
                return responseData;
            }
        } catch (IOException e) {
            return new HttpResponseData<>(null, HttpReturnCode.INPUT_ILLEGAL);
        } catch (Exception e) {
            logger.error("[createCredentialInvoke]: SDK error. reqCreateCredentialArgs:{}", createCredentialFuncArgs, e);
            return new HttpResponseData<>(null, HttpReturnCode.DID_SDK_ERROR.getCode(),
                    HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }

    /**
     * Verify the validity of a credential. Need format conversion (UTC date and @context)
     *
     * @param verifyCredentialFuncArgs the credential json args
     * @return the Boolean response data
     */
    @Override
    public HttpResponseData<Object> verifyCredentialInvoke(InputArg verifyCredentialFuncArgs) {
        Credential credential = null;
        try {
            credential = (Credential) JsonUtil.jsonStrToObj(new Credential(), verifyCredentialFuncArgs.getFunctionArg());
        } catch (Exception e) {
            // The input credential is a json, so keep moving down.
            logger.info("Detected Portable-Json format credential, continuing..");
        }

        if (credential == null) {
            try {
                Map<String, Object> credMap = (Map<String, Object>) JsonUtil.jsonStrToObj(new HashMap<String, Object>(),
                        verifyCredentialFuncArgs.getFunctionArg());
                credMap = JsonUtil.reformatCredentialPojoToJson(credMap);
                credential = (Credential) JsonUtil.jsonStrToObj(new Credential(), JsonUtil.mapToCompactJson(credMap));
            } catch (Exception e) {
                logger.error("Input credential format illegal: {}", verifyCredentialFuncArgs);
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_ILLEGAL.getCode(),
                        HttpReturnCode.INPUT_ILLEGAL.getCodeDesc().concat(e.getMessage()));
            }
        }

        try {
            ResponseData<Boolean> responseData = credentialService.verify(credential);
            return new HttpResponseData<>(responseData.getResult(),
                    responseData.getErrorCode(), responseData.getErrorMessage());
        } catch (Exception e) {
            logger.error("[verifyCredentialInvoke]: SDK error. reqCredentialArgs:{}",
                    verifyCredentialFuncArgs,
                    e);
            return new HttpResponseData<>(null, HttpReturnCode.DID_SDK_ERROR.getCode(),
                    HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }


    /**
     * Verify the validity of a credential.
     *
     * @param verifyCredentialJsonArgs the credential json args
     * @return the Boolean response data
     */
    public HttpResponseData<Object> verifyCredentialByJson(JSONObject verifyCredentialJsonArgs) {
        Credential credential = null;
        try {
            JSONArray jsonArray = verifyCredentialJsonArgs.getJSONArray("functionArg");
            credential = (Credential) JsonUtil.jsonStrToObj(new Credential(), String.valueOf(jsonArray.get(0)));
        } catch (Exception e) {
            // The input credential is a json, so keep moving down.
            logger.info("Detected Portable-Json format credential, continuing..");
        }

        if (credential == null) {
            try {
                Map<String, Object> credMap = (Map<String, Object>) JsonUtil.jsonStrToObj(new HashMap<String, Object>(),
                        verifyCredentialJsonArgs.toString());
                credMap = JsonUtil.reformatCredentialPojoToJson(credMap);
                credential = (Credential) JsonUtil.jsonStrToObj(new Credential(), JsonUtil.mapToCompactJson(credMap));
            } catch (Exception e) {
                logger.error("Input credential format illegal: {}", verifyCredentialJsonArgs);
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_ILLEGAL.getCode(),
                        HttpReturnCode.INPUT_ILLEGAL.getCodeDesc().concat(e.getMessage()));
            }
        }

        try {
            ResponseData<Boolean> responseData = credentialService.verify(credential);
            logger.info("Credentials verification result:{}", responseData.toString());
            return new HttpResponseData<>(responseData.getResult(), responseData.getErrorCode(), responseData.getErrorMessage());
        } catch (Exception e) {
            logger.error("[verifyCredentialInvoke]: SDK error. reqCredentialArgs:{}", verifyCredentialJsonArgs, e);
            return new HttpResponseData<>(null, HttpReturnCode.DID_SDK_ERROR.getCode(),
                    HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }
}
