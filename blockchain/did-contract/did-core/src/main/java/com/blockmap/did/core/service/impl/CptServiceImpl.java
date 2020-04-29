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
import com.blockmap.did.core.constant.JsonSchemaConstant;
import com.blockmap.did.core.protocol.base.Cpt;
import com.blockmap.did.core.protocol.base.CptBaseInfo;
import com.blockmap.did.core.protocol.base.DidAuthentication;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.request.CptMapArgs;
import com.blockmap.did.core.protocol.request.CptStringArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.CptService;
import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.util.DidUtils;
import com.blockmap.did.core.service.impl.engine.CptServiceEngine;
import com.blockmap.did.core.service.impl.engine.EngineFactory;
import com.blockmap.did.core.web3j.crypto.Sign.SignatureData;
import com.webank.wedpr.selectivedisclosure.CredentialTemplateEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Service implementation for operation on CPT (Claim Protocol Type).
 *
 */
public class CptServiceImpl implements CptService {

    private static final Logger logger = LoggerFactory.getLogger(CptServiceImpl.class);

    private CptServiceEngine cptServiceEngine = EngineFactory.createCptServiceEngine();

    /**
     * Register a new CPT with a pre-set CPT ID, to the blockchain.
     *
     * @param args the args
     * @param cptId the CPT ID
     * @return response data
     */
    @Override
    public ResponseData<CptBaseInfo> registerCpt(CptStringArgs args, Integer cptId) {
        if (args == null || cptId == null || cptId <= 0) {
            logger.error(
                "[registerCpt1] input argument is illegal");
            return new ResponseData<>(null, ErrorCode.ILLEGAL_INPUT);
        }
        try {
            CptMapArgs cptMapArgs = new CptMapArgs();
            cptMapArgs.setDidAuthentication(args.getDidAuthentication());
            Map<String, Object> cptJsonSchemaMap =
                DataToolUtils.deserialize(args.getCptJsonSchema(), HashMap.class);
            cptMapArgs.setCptJsonSchema(cptJsonSchemaMap);
            return this.registerCpt(cptMapArgs, cptId);
        } catch (Exception e) {
            logger.error("[registerCpt1] register cpt failed due to unknown error. ", e);
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * This is used to register a new CPT to the blockchain.
     *
     * @param args the args
     * @return the response data
     */
    @Override
    public ResponseData<CptBaseInfo> registerCpt(CptStringArgs args) {

        try {
            if (args == null) {
                logger.error(
                    "[registerCpt1]input CptStringArgs is null");
                return new ResponseData<>(null, ErrorCode.ILLEGAL_INPUT);
            }

            CptMapArgs cptMapArgs = new CptMapArgs();
            cptMapArgs.setDidAuthentication(args.getDidAuthentication());
            Map<String, Object> cptJsonSchemaMap =
                DataToolUtils.deserialize(args.getCptJsonSchema(), HashMap.class);
            cptMapArgs.setCptJsonSchema(cptJsonSchemaMap);
            return this.registerCpt(cptMapArgs);
        } catch (Exception e) {
            logger.error("[registerCpt1] register cpt failed due to unknown error. ", e);
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * Register a new CPT with a pre-set CPT ID, to the blockchain.
     *
     * @param args the args
     * @param cptId the CPT ID
     * @return response data
     */
    @Override
    public ResponseData<CptBaseInfo> registerCpt(CptMapArgs args, Integer cptId) {
        if (args == null || cptId == null || cptId <= 0) {
            logger.error("[registerCpt] input argument is illegal");
            return new ResponseData<>(null, ErrorCode.ILLEGAL_INPUT);
        }
        try {
            ErrorCode errorCode =
                this.validateCptArgs(
                    args.getDidAuthentication(),
                    args.getCptJsonSchema()
                );
            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                return new ResponseData<>(null, errorCode);
            }
            String did = args.getDidAuthentication().getDid();
            String cptJsonSchemaNew = this.cptSchemaToString(args.getCptJsonSchema());
            DidPrivateKey didPrivateKey = args.getDidAuthentication().getDidPrivateKey();
            SignatureData signatureData = sign(did, cptJsonSchemaNew, didPrivateKey);
            return cptServiceEngine.registerCpt(did, cptId, cptJsonSchemaNew, signatureData);
        } catch (Exception e) {
            logger.error("[registerCpt] register cpt failed due to unknown error. ", e);
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * This is used to register a new CPT to the blockchain.
     *
     * @param args the args
     * @return the response data
     */
    @Override
    public ResponseData<CptBaseInfo> registerCpt(CptMapArgs args) {

        try {
            if (args == null) {
                logger.error("[registerCpt]input CptMapArgs is null");
                return new ResponseData<>(null, ErrorCode.ILLEGAL_INPUT);
            }
            ErrorCode validateResult =
                this.validateCptArgs(
                    args.getDidAuthentication(),
                    args.getCptJsonSchema()
                );

            if (validateResult.getCode() != ErrorCode.SUCCESS.getCode()) {
                return new ResponseData<>(null, validateResult);
            }

            String did = args.getDidAuthentication().getDid();

            String cptJsonSchemaNew = this.cptSchemaToString(args.getCptJsonSchema());

            String address = DidUtils.convertDidToAddress(did);
            return cptServiceEngine.registerCpt(address, cptJsonSchemaNew);
        } catch (Exception e) {
            logger.error("[registerCpt] register cpt failed due to unknown error. ", e);
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * this is used to query cpt with the latest version which has been registered.
     *
     * @param cptId the cpt id
     * @return the response data
     */
    @Override
    public ResponseData<Cpt> queryCpt(Integer cptId) {

        try {
            if (cptId == null || cptId < 0) {
                return new ResponseData<>(null, ErrorCode.CPT_ID_ILLEGAL);
            }
            return cptServiceEngine.queryCpt(cptId);
        } catch (Exception e) {
            logger.error("[updateCpt] query cpt failed due to unknown error. ", e);
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * This is used to update a CPT data which has been register.
     *
     * @param args the args
     * @return the response data
     */
    @Override
    public ResponseData<CptBaseInfo> updateCpt(CptStringArgs args, Integer cptId) {

        try {
            if (args == null) {
                logger.error("[updateCpt1]input UpdateCptArgs is null");
                return new ResponseData<>(null, ErrorCode.ILLEGAL_INPUT);
            }

            CptMapArgs cptMapArgs = new CptMapArgs();
            cptMapArgs.setDidAuthentication(args.getDidAuthentication());
            cptMapArgs.setCptJsonSchema(
                DataToolUtils.deserialize(args.getCptJsonSchema(), HashMap.class));
            return this.updateCpt(cptMapArgs, cptId);
        } catch (Exception e) {
            logger.error("[updateCpt1] update cpt failed due to unkown error. ", e);
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    /**
     * This is used to update a CPT data which has been register.
     *
     * @param args the args
     * @return the response data
     */
    @Override
    public ResponseData<CptBaseInfo> updateCpt(CptMapArgs args, Integer cptId) {

        try {
            if (args == null) {
                logger.error("[updateCpt]input UpdateCptArgs is null");
                return new ResponseData<>(null, ErrorCode.ILLEGAL_INPUT);
            }
            if (cptId == null || cptId.intValue() < 0) {
                logger.error("[updateCpt]input cptId illegal");
                return new ResponseData<>(null, ErrorCode.CPT_ID_ILLEGAL);
            }
            ErrorCode errorCode =
                this.validateCptArgs(
                    args.getDidAuthentication(),
                    args.getCptJsonSchema()
                );

            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                return new ResponseData<>(null, errorCode);
            }
            String did = args.getDidAuthentication().getDid();
            String cptJsonSchemaNew = this.cptSchemaToString(args.getCptJsonSchema());
            return cptServiceEngine.updateCpt(did, cptId, cptJsonSchemaNew);
        } catch (Exception e) {
            logger.error("[updateCpt] update cpt failed due to unkown error. ", e);
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }


    private SignatureData sign(
        String cptPublisher,
        String jsonSchema,
        DidPrivateKey cptPublisherPrivateKey) {

        StringBuilder sb = new StringBuilder();
        sb.append(cptPublisher);
        sb.append(DidConstant.PIPELINE);
        sb.append(jsonSchema);
        SignatureData signatureData =
            DataToolUtils.signMessage(sb.toString(), cptPublisherPrivateKey.getPrivateKey());
        return signatureData;
    }

    private ErrorCode validateCptArgs(
        DidAuthentication didAuthentication,
        Map<String, Object> cptJsonSchemaMap) throws Exception {

        if (didAuthentication == null) {
            logger.error("Input cpt didAuthentication is invalid.");
            return ErrorCode.DID_AUTHORITY_INVALID;
        }

        String did = didAuthentication.getDid();
        if (!DidUtils.isDidValid(did)) {
            logger.error("Input cpt publisher : {} is invalid.", did);
            return ErrorCode.DID_INVALID;
        }

        ErrorCode errorCode = validateCptJsonSchemaMap(cptJsonSchemaMap);
        if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
            return errorCode;
        }
        String cptJsonSchema = DataToolUtils.serialize(cptJsonSchemaMap);
        if (!DataToolUtils.isCptJsonSchemaValid(cptJsonSchema)) {
            logger.error("Input cpt json schema : {} is invalid.", cptJsonSchemaMap);
            return ErrorCode.CPT_JSON_SCHEMA_INVALID;
        }
        DidPrivateKey didPrivateKey = didAuthentication.getDidPrivateKey();
        if (didPrivateKey == null
            || StringUtils.isEmpty(didPrivateKey.getPrivateKey())) {
            logger.error(
                "Input cpt publisher private key : {} is in valid.",
                didPrivateKey
            );
            return ErrorCode.DID_PRIVATEKEY_INVALID;
        }

        if (!DidUtils.validatePrivateKeyDidMatches(didPrivateKey, did)) {
            return ErrorCode.DID_PRIVATEKEY_DOES_NOT_MATCH;
        }
        return ErrorCode.SUCCESS;
    }

    private ErrorCode validateCptJsonSchemaMap(
        Map<String, Object> cptJsonSchemaMap) throws Exception {
        if (cptJsonSchemaMap == null || cptJsonSchemaMap.isEmpty()) {
            logger.error("Input cpt json schema is invalid.");
            return ErrorCode.CPT_JSON_SCHEMA_INVALID;
        }
        //String cptJsonSchema = JsonUtil.objToJsonStr(cptJsonSchemaMap);
        String cptJsonSchema = DataToolUtils.serialize(cptJsonSchemaMap);
        if (!DataToolUtils.isCptJsonSchemaValid(cptJsonSchema)) {
            logger.error("Input cpt json schema : {} is invalid.", cptJsonSchemaMap);
            return ErrorCode.CPT_JSON_SCHEMA_INVALID;
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * create new cpt json schema.
     *
     * @param cptJsonSchema Map
     * @return String
     */
    private String cptSchemaToString(Map<String, Object> cptJsonSchema) throws Exception {

        Map<String, Object> cptJsonSchemaNew = new HashMap<String, Object>();
        cptJsonSchemaNew.put(JsonSchemaConstant.SCHEMA_KEY, JsonSchemaConstant.SCHEMA_VALUE);
        cptJsonSchemaNew.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_OBJECT);
        cptJsonSchemaNew.putAll(cptJsonSchema);
        return DataToolUtils.serialize(cptJsonSchemaNew);
    }

    @Override
    public ResponseData<CredentialTemplateEntity> queryCredentialTemplate(Integer cptId) {

        return cptServiceEngine.queryCredentialTemplate(cptId);
    }
}
