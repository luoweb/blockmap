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


package com.blockmap.did.core.service.impl;

import com.blockmap.did.core.constant.DidConstant;
import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.exception.LoadContractException;
import com.blockmap.did.core.exception.PrivateKeyIllegalException;
import com.blockmap.did.core.protocol.base.DidDocument;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.base.DidPublicKey;
import com.blockmap.did.core.protocol.request.CreateDidArgs;
import com.blockmap.did.core.protocol.request.SetAuthenticationArgs;
import com.blockmap.did.core.protocol.request.SetPublicKeyArgs;
import com.blockmap.did.core.protocol.request.SetServiceArgs;
import com.blockmap.did.core.protocol.response.CreateDidDataResult;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.DidService;
import com.blockmap.did.core.util.DidUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blockmap.did.core.service.impl.engine.DidServiceEngine;
import com.blockmap.did.core.service.impl.engine.EngineFactory;
import com.blockmap.did.core.web3j.crypto.ECKeyPair;
import com.blockmap.did.core.web3j.crypto.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: Service implementations for operations on DID.
 * @File: DidServiceImpl
 * @Version: 1.0.0
 * @Date: 2019/12/17 14:41
 */

public class DidServiceImpl implements DidService {

    /**
     * log4j object, for recording log.
     */
    private static final Logger logger = LoggerFactory.getLogger(DidServiceImpl.class);


    private static DidServiceEngine didServiceEngine = EngineFactory.createDidServiceEngine();

    /**
     * Create a  DID with null input param.
     *
     * @return the response data
     */
    @Override
    public ResponseData<CreateDidDataResult> createDid() {

        CreateDidDataResult result = new CreateDidDataResult();
        ECKeyPair keyPair = null;

        try {
            keyPair = Keys.createEcKeyPair();
        } catch (Exception e) {
            logger.error("Create did failed.", e);
            return new ResponseData<>(null, ErrorCode.DID_KEYPAIR_CREATE_FAILED);
        }

        String publicKey = String.valueOf(keyPair.getPublicKey());
        String privateKey = String.valueOf(keyPair.getPrivateKey());
        DidPublicKey userDidPublicKey = new DidPublicKey();
        userDidPublicKey.setPublicKey(publicKey);
        result.setUserDidPublicKey(userDidPublicKey);
        DidPrivateKey userDidPrivateKey = new DidPrivateKey();
        userDidPrivateKey.setPrivateKey(privateKey);
        result.setUserDidPrivateKey(userDidPrivateKey);
        String did = DidUtils.convertPublicKeyToDid(publicKey);
        result.setDid(did);

        ResponseData<Boolean> innerResp = processCreateDid(did, publicKey, privateKey);
        if (innerResp.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
            logger.error(
                    "[createDid] Create did failed. error message is :{}",
                    innerResp.getErrorMessage()
            );
            return new ResponseData<>(null,
                    ErrorCode.getTypeByErrorCode(innerResp.getErrorCode()),
                    innerResp.getTransactionInfo());
        }
        return new ResponseData<>(result, ErrorCode.getTypeByErrorCode(innerResp.getErrorCode()),
                innerResp.getTransactionInfo());
    }

    /**
     * Create a DID.
     *
     * @param createDidArgs the create DID args
     * @return the response data
     */
    @Override
    public ResponseData<String> createDid(CreateDidArgs createDidArgs) {

        if (createDidArgs == null) {
            logger.error("[createDid]: input parameter createDidArgs is null.");
            return new ResponseData<>(StringUtils.EMPTY, ErrorCode.ILLEGAL_INPUT);
        }
        if (!DidUtils.isPrivateKeyValid(createDidArgs.getDidPrivateKey())) {
            return new ResponseData<>(StringUtils.EMPTY, ErrorCode.DID_PRIVATEKEY_INVALID);
        }
        String privateKey = createDidArgs.getDidPrivateKey().getPrivateKey();
        String publicKey = createDidArgs.getPublicKey();
        if (StringUtils.isNotBlank(publicKey)) {
            if (!DidUtils.isKeypairMatch(privateKey, publicKey)) {
                return new ResponseData<>(
                        StringUtils.EMPTY,
                        ErrorCode.DID_PUBLICKEY_AND_PRIVATEKEY_NOT_MATCHED
                );
            }
            String did = DidUtils.convertPublicKeyToDid(publicKey);
            ResponseData<Boolean> isDidExistResp = this.isDidExist(did);
            if (isDidExistResp.getResult() == null || isDidExistResp.getResult()) {
                logger.error("[createDid]: create did failed, the did :{} is already exist", did);
                return new ResponseData<>(StringUtils.EMPTY, ErrorCode.DID_ALREADY_EXIST);
            }
            ResponseData<Boolean> innerResp = processCreateDid(did, publicKey, privateKey);
            if (innerResp.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
                logger.error(
                        "[createDid]: create did failed. error message is :{}, public key is {}",
                        innerResp.getErrorMessage(),
                        publicKey
                );
                return new ResponseData<>(StringUtils.EMPTY,
                        ErrorCode.getTypeByErrorCode(innerResp.getErrorCode()),
                        innerResp.getTransactionInfo());
            }
            return new ResponseData<>(did,
                    ErrorCode.getTypeByErrorCode(innerResp.getErrorCode()),
                    innerResp.getTransactionInfo());
        } else {
            return new ResponseData<>(StringUtils.EMPTY, ErrorCode.DID_PUBLICKEY_INVALID);
        }
    }

    /**
     * Get a DID Document.
     *
     * @param did the DID
     * @return the DID document
     */
    @Override
    public ResponseData<DidDocument> getDidDocument(String did) {

        if (!DidUtils.isDidValid(did)) {
            logger.error("Input did : {} is invalid.", did);
            return new ResponseData<>(null, ErrorCode.DID_INVALID);
        }
        String address = DidUtils.convertDidToAddress(did);
        return didServiceEngine.getDidDocument(address);
    }

    /**
     * Get a  DID Document Json.
     *
     * @param did the  DID
     * @return the  DID document json
     */
    @Override
    public ResponseData<String> getDidDocumentJson(String did) {

        ResponseData<DidDocument> responseData = this.getDidDocument(did);
        DidDocument result = responseData.getResult();

        if (result == null) {
            return new ResponseData<>(
                    StringUtils.EMPTY,
                    ErrorCode.getTypeByErrorCode(responseData.getErrorCode())
            );
        }
        ObjectMapper mapper = new ObjectMapper();
        String didDocument;
        try {
            didDocument = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (Exception e) {
            return new ResponseData<>(
                    StringUtils.EMPTY,
                    ErrorCode.getTypeByErrorCode(responseData.getErrorCode())
            );
        }
        didDocument =
                new StringBuffer()
                        .append(didDocument)
                        .insert(1, DidConstant.DID_DOC_PROTOCOL_VERSION)
                        .toString();

        ResponseData<String> responseDataJson = new ResponseData<String>();
        responseDataJson.setResult(didDocument);
        responseDataJson.setErrorCode(ErrorCode.getTypeByErrorCode(responseData.getErrorCode()));

        return responseDataJson;
    }

    /**
     * Set Public Key.
     *
     * @param setPublicKeyArgs the set public key args
     * @return the response data
     */
    @Override
    public ResponseData<Boolean> setPublicKey(SetPublicKeyArgs setPublicKeyArgs) {

        if (!verifySetPublicKeyArgs(setPublicKeyArgs)) {
            logger.error("[setPublicKey]: input parameter setPublicKeyArgs is illegal.");
            return new ResponseData<>(false, ErrorCode.ILLEGAL_INPUT);
        }
        if (!DidUtils.isPrivateKeyValid(setPublicKeyArgs.getUserDidPrivateKey())) {
            return new ResponseData<>(false, ErrorCode.DID_PRIVATEKEY_INVALID);
        }

        String did = setPublicKeyArgs.getDid();
        String weAddress = DidUtils.convertDidToAddress(did);
        if (StringUtils.isEmpty(weAddress)) {
            logger.error("setPublicKey: did : {} is invalid.", did);
            return new ResponseData<>(false, ErrorCode.DID_INVALID);
        }
        String owner = setPublicKeyArgs.getOwner();
        if (StringUtils.isEmpty(owner)) {
            owner = weAddress;
        } else {
            if (DidUtils.isDidValid(owner)) {
                owner = DidUtils.convertDidToAddress(owner);
            } else {
                logger.error("setPublicKey: owner : {} is invalid.", owner);
                return new ResponseData<>(false, ErrorCode.DID_INVALID);
            }
        }
        String pubKey = setPublicKeyArgs.getPublicKey();

        String privateKey = setPublicKeyArgs.getUserDidPrivateKey().getPrivateKey();
        try {
            String attributeKey =
                    new StringBuffer()
                            .append(DidConstant.DID_DOC_PUBLICKEY_PREFIX)
                            .append(DidConstant.SEPARATOR)
                            .append(setPublicKeyArgs.getType())
                            .append(DidConstant.SEPARATOR)
                            .append("base64")
                            .toString();
            String attrValue = new StringBuffer().append(pubKey).append("/").append(owner)
                    .toString();
            return didServiceEngine.setAttribute(weAddress, attributeKey, attrValue, privateKey);
        } catch (PrivateKeyIllegalException e) {
            logger.error("[setPublicKey] set PublicKey failed because privateKey is illegal. ",
                    e);
            return new ResponseData<>(false, e.getErrorCode());
        } catch (Exception e) {
            logger.error("[setPublicKey] set PublicKey failed with com.blockmap.did.core.exception. ", e);
            return new ResponseData<>(false, ErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * Set Service.
     *
     * @param setServiceArgs the set com.blockmap.did.core.service args
     * @return the response data
     */
    @Override
    public ResponseData<Boolean> setService(SetServiceArgs setServiceArgs) {
        if (!verifySetServiceArgs(setServiceArgs)) {
            logger.error("[setService]: input parameter setServiceArgs is illegal.");
            return new ResponseData<>(false, ErrorCode.ILLEGAL_INPUT);
        }
        if (!DidUtils.isPrivateKeyValid(setServiceArgs.getUserDidPrivateKey())) {
            return new ResponseData<>(false, ErrorCode.DID_PRIVATEKEY_INVALID);
        }
        if (!verifyServiceType(setServiceArgs.getType())) {
            logger.error("[setService]: the length of com.blockmap.did.core.service type is overlimit");
            return new ResponseData<>(false, ErrorCode.DID_SERVICE_TYPE_OVERLIMIT);
        }
        String did = setServiceArgs.getDid();
        String serviceType = setServiceArgs.getType();
        String serviceEndpoint = setServiceArgs.getServiceEndpoint();
        if (DidUtils.isDidValid(did)) {
            String privateKey = setServiceArgs.getUserDidPrivateKey().getPrivateKey();
            try {
                String attributeKey = new StringBuffer()
                        .append(DidConstant.DID_DOC_SERVICE_PREFIX)
                        .append(DidConstant.SEPARATOR)
                        .append(serviceType)
                        .toString();
                return didServiceEngine
                        .setAttribute(DidUtils.convertDidToAddress(did), attributeKey,
                                serviceEndpoint, privateKey);

            } catch (PrivateKeyIllegalException e) {
                logger.error("[setService] set PublicKey failed because privateKey is illegal. ",
                                e);
                return new ResponseData<>(false, e.getErrorCode());
            } catch (Exception e) {
                logger.error("[setService] set com.blockmap.did.core.service failed. Error message :{}", e);
                return new ResponseData<>(false, ErrorCode.UNKNOW_ERROR);
            }
        } else {
            logger.error("[setService] set com.blockmap.did.core.service failed, did -->{} is invalid.", did);
            return new ResponseData<>(false, ErrorCode.DID_INVALID);
        }

    }

    /**
     * Check if  DID exists on Chain.
     *
     * @param did the  DID
     * @return true if exists, false otherwise
     */
    @Override
    public ResponseData<Boolean> isDidExist(String did) {
        if (!DidUtils.isDidValid(did)) {
            logger.error("[isDidExist] check did failed. did : {} is invalid.", did);
            return new ResponseData<>(false, ErrorCode.DID_INVALID);
        }
        return didServiceEngine.isDidExist(did);
    }

    /**
     * Set Authentication.
     *
     * @param setAuthenticationArgs the set authentication args
     * @return the response data
     */
    @Override
    public ResponseData<Boolean> setAuthentication(SetAuthenticationArgs setAuthenticationArgs) {

        if (!verifySetAuthenticationArgs(setAuthenticationArgs)) {
            logger.error("[setAuthentication]: input parameter setAuthenticationArgs is illegal.");
            return new ResponseData<>(false, ErrorCode.ILLEGAL_INPUT);
        }
        if (!DidUtils.isPrivateKeyValid(setAuthenticationArgs.getUserDidPrivateKey())) {
            return new ResponseData<>(false, ErrorCode.DID_PRIVATEKEY_INVALID);
        }
        String did = setAuthenticationArgs.getDid();
        if (DidUtils.isDidValid(did)) {
            String weAddress = DidUtils.convertDidToAddress(did);

            String owner = setAuthenticationArgs.getOwner();
            if (StringUtils.isEmpty(owner)) {
                owner = weAddress;
            } else {
                if (DidUtils.isDidValid(owner)) {
                    owner = DidUtils.convertDidToAddress(owner);
                } else {
                    logger.error("[setAuthentication]: owner : {} is invalid.", owner);
                    return new ResponseData<>(false, ErrorCode.DID_INVALID);
                }
            }
            String privateKey = setAuthenticationArgs.getUserDidPrivateKey().getPrivateKey();
            try {
                String attrValue = new StringBuffer()
                        .append(setAuthenticationArgs.getPublicKey())
                        .append(DidConstant.SEPARATOR)
                        .append(owner)
                        .toString();
                return didServiceEngine
                        .setAttribute(weAddress,
                                DidConstant.DID_DOC_AUTHENTICATE_PREFIX,
                                attrValue,
                                privateKey);
            } catch (PrivateKeyIllegalException e) {
                logger.error("Set authenticate with private key com.blockmap.did.core.exception. Error message :{}", e);
                return new ResponseData<>(false, e.getErrorCode());
            } catch (Exception e) {
                logger.error("Set authenticate failed. Error message :{}", e);
                return new ResponseData<>(false, ErrorCode.UNKNOW_ERROR);
            }
        } else {
            logger.error("Set authenticate failed. did : {} is invalid.", did);
            return new ResponseData<>(false, ErrorCode.DID_INVALID);
        }
    }

    private boolean verifySetServiceArgs(SetServiceArgs setServiceArgs) {

        return !(setServiceArgs == null
                || StringUtils.isBlank(setServiceArgs.getType())
                || setServiceArgs.getUserDidPrivateKey() == null
                || StringUtils.isBlank(setServiceArgs.getServiceEndpoint()));
    }

    private boolean verifyServiceType(String type) {
        String serviceType = new StringBuffer()
                .append(DidConstant.DID_DOC_SERVICE_PREFIX)
                .append(DidConstant.SEPARATOR)
                .append(type)
                .toString();
        int serviceTypeLength = serviceType.getBytes(StandardCharsets.UTF_8).length;
        return serviceTypeLength <= DidConstant.BYTES32_FIXED_LENGTH;
    }

    private ResponseData<Boolean> processCreateDid(String did, String publicKey,
                                                   String privateKey) {

        String address = DidUtils.convertDidToAddress(did);
        try {

            return didServiceEngine.createDid(address, publicKey, privateKey);
        } catch (PrivateKeyIllegalException e) {
            logger.error("[createDid] create did failed because privateKey is illegal. ",
                    e);
            return new ResponseData<>(false, e.getErrorCode());
        } catch (LoadContractException e) {
            logger.error("[createDid] create did failed because Load Contract with "
                            + "com.blockmap.did.core.exception. ",
                    e);
            return new ResponseData<>(false, e.getErrorCode());
        } catch (Exception e) {
            logger.error("[createDid] create did failed with com.blockmap.did.core.exception. ", e);
            return new ResponseData<>(false, ErrorCode.UNKNOW_ERROR);
        }
    }

    private boolean verifySetPublicKeyArgs(SetPublicKeyArgs setPublicKeyArgs) {

        return !(setPublicKeyArgs == null
                || setPublicKeyArgs.getType() == null
                || setPublicKeyArgs.getUserDidPrivateKey() == null
                || StringUtils.isBlank(setPublicKeyArgs.getPublicKey()));
    }

    private boolean verifySetAuthenticationArgs(SetAuthenticationArgs setAuthenticationArgs) {

        return !(setAuthenticationArgs == null
                || setAuthenticationArgs.getUserDidPrivateKey() == null
                || StringUtils.isEmpty(setAuthenticationArgs.getPublicKey()));
    }

}
