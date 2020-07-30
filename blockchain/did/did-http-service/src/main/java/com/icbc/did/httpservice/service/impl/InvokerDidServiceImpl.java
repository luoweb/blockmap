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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbc.did.core.constant.ErrorCode;
import com.icbc.did.core.constant.ParamKeyConstant;
import com.icbc.did.core.protocol.base.DidPrivateKey;
import com.icbc.did.core.protocol.request.SetAuthenticationArgs;
import com.icbc.did.core.protocol.request.SetPublicKeyArgs;
import com.icbc.did.core.protocol.response.CreateDidDataResult;
import com.icbc.did.core.protocol.response.ResponseData;
import com.icbc.did.core.service.DidService;
import com.icbc.did.core.service.impl.DidServiceImpl;
import com.icbc.did.httpservice.constant.HttpReturnCode;
import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import com.icbc.did.httpservice.service.BaseService;
import com.icbc.did.httpservice.service.InvokerDidService;
import com.icbc.did.httpservice.util.JsonUtil;
import com.icbc.did.httpservice.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: Handles all Json related tasks.
 * @File: InvokerDIdServiceImpl
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

@Component
public class InvokerDidServiceImpl extends BaseService implements InvokerDidService {

    private Logger logger = LoggerFactory.getLogger(InvokerDidServiceImpl.class);

    private DidService didService = new DidServiceImpl();

//    private RawTransactionService rawTransactionService = new RawTransactionServiceImpl();


    /**
     * Create DID - a raw method for test purpose only.
     *
     * @return the response data
     */
    @Override
    public ResponseData<CreateDidDataResult> createDid() {

        ResponseData<CreateDidDataResult> response = new ResponseData<CreateDidDataResult>();
        try {
            response = didService.createDid();
        } catch (Exception e) {
            logger.error("[createDid]: unknow error, please check the error log.", e);
            return new ResponseData<>(null, ErrorCode.BASE_ERROR);
        }
        return response;
    }

    /**
     * Check if DID exists on Chain - for test purpose only.
     *
     * @param did the DID
     * @return true if exists, false otherwise
     */
    @Override
    public ResponseData<Boolean> isDidExist(String did) {

        ResponseData<Boolean> response = new ResponseData<Boolean>();
        try {
            response = didService.isDidExist(did);
        } catch (Exception e) {
            logger.error(
                "[isDidExist]: unknow error. did:{}.",
                did,
                e);
            return new ResponseData<>(false, ErrorCode.BASE_ERROR);
        }
        return response;
    }

    /**
     * Call to DID SDK with direct transaction hex String, to create DID.
     *
     * @param transactionHex the transactionHex value
     * @return String in ResponseData
     */
    @Override
    public HttpResponseData<String> createDidWithTransactionHex(String transactionHex) {
//        try {
//            ResponseData<String> responseData = rawTransactionService.createDid(transactionHex);
//            if (responseData.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
//                logger.error("[createDid]: error occurred: {}, {}", responseData.getErrorCode(),
//                    responseData.getErrorMessage());
//            }
//            return new HttpResponseData<>(responseData.getResult(), responseData.getErrorCode(),
//                responseData.getErrorMessage());
//        } catch (Exception e) {
//            logger.error("[createDid]: unknown error, input arguments:{}",
//                transactionHex,
//                e);
//            return new HttpResponseData<>(StringUtils.EMPTY,
//                HttpReturnCode.DID_SDK_ERROR.getCode(),
//                HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
//        }
        return null;
    }

    /**
     * Get a  DID Document Json via the InvokeFunction API.
     *
     * @param getDidDocumentJsonArgs the  DID
     * @return the DID document json
     */
    @Override
    public HttpResponseData<Object> getDidDocumentJsonInvoke(
        InputArg getDidDocumentJsonArgs) {
        try {
            JsonNode didNode = new ObjectMapper()
                .readTree(getDidDocumentJsonArgs.getFunctionArg())
                .get(ParamKeyConstant.DID);
            if (didNode == null || StringUtils.isEmpty(didNode.textValue())) {
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_NULL);
            }
            ResponseData<String> response = didService.getDidDocumentJson(didNode.textValue());
            return new HttpResponseData<>(
                JsonUtil.convertJsonToSortedMap(response.getResult()),
                response.getErrorCode(),
                response.getErrorMessage());
        } catch (Exception e) {
            logger.error(
                "[getDidDocument]: unknow error. did:{}.",
                getDidDocumentJsonArgs,
                e);
            return new HttpResponseData<>(null, HttpReturnCode.DID_SDK_ERROR.getCode(),
                HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }

    /**
     * Create DID via the InvokeFunction API.
     *
     * @param createDidJsonArgs the input args, should be almost null
     * @return the  DID
     */
    @Override
    public HttpResponseData<Object> createDidInvoke(InputArg createDidJsonArgs) {
        try {
            ResponseData<CreateDidDataResult> response = didService.createDid();
            CreateDidDataResult createDidDataResult = response.getResult();
            if (createDidDataResult != null) {
                try {
                    // host the did which just got created
                    KeyUtil.savePrivateKey(KeyUtil.SDK_PRIVKEY_PATH,
                        createDidDataResult.getDid(),
                        createDidDataResult.getUserDidPrivateKey().getPrivateKey());
                } catch (Exception e) {
                    return new HttpResponseData<>(null, HttpReturnCode.INVOKER_ILLEGAL);
                }

                // set publicKey
                SetPublicKeyArgs setPublicKeyArgs = new SetPublicKeyArgs();
                setPublicKeyArgs.setDid(createDidDataResult.getDid());
                setPublicKeyArgs.setPublicKey(createDidDataResult.getUserDidPublicKey().getPublicKey());
                setPublicKeyArgs.setType("secp256k1");
                DidPrivateKey didPrivateKey = new DidPrivateKey();
                didPrivateKey.setPrivateKey(createDidDataResult.getUserDidPrivateKey().getPrivateKey());
                setPublicKeyArgs.setUserDidPrivateKey(didPrivateKey);
                //ResponseData<Boolean> responseSetPub = didService.setPublicKey(setPublicKeyArgs);

                // set authentication
                SetAuthenticationArgs setAuthenticationArgs = new SetAuthenticationArgs();
                setAuthenticationArgs.setDid(createDidDataResult.getDid());
                setAuthenticationArgs.setPublicKey(createDidDataResult.getUserDidPublicKey().getPublicKey());
                setAuthenticationArgs.setUserDidPrivateKey(didPrivateKey);
                //ResponseData<Boolean> responseSetAuth = didService.setAuthentication(setAuthenticationArgs);

                return new HttpResponseData<>(createDidDataResult.getDid(),
                        response.getErrorCode(), response.getErrorMessage());
            } else {
                return new HttpResponseData<>(null, response.getErrorCode(),
                    response.getErrorMessage());
            }
        } catch (Exception e) {
            logger.error(
                "[getDidDocumentJson]: unknow error. did:{}.",
                createDidJsonArgs,
                e);
            return new HttpResponseData<>(new HashMap<>(), HttpReturnCode.DID_SDK_ERROR.getCode(),
                HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }
}
