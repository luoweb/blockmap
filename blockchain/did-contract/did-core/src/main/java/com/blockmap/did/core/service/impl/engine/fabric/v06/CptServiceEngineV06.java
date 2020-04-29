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
import com.alibaba.fastjson.JSONObject;
import com.blockmap.did.core.constant.ContractConstant;
import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.contract.fabric.v06.impl.CptContract;
import com.blockmap.did.core.protocol.base.Cpt;
import com.blockmap.did.core.protocol.base.CptBaseInfo;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.impl.engine.CptServiceEngine;
import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.util.DateUtils;
import com.blockmap.did.core.util.DidUtils;
import com.blockmap.did.core.web3j.crypto.Sign;
import com.webank.wedpr.selectivedisclosure.CredentialTemplateEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * CptServiceEngine calls the authority issuer contract which runs on FABRIC 0.6.
 *
 * @author xuzj01@sdc.blockmap.com
 */

public class CptServiceEngineV06 implements CptServiceEngine {

    private static final Logger logger = LoggerFactory.getLogger(CptServiceEngineV06.class);

    private CptContract cptContract = new CptContract();

    @Override
    public ResponseData<CptBaseInfo> updateCpt(String did, int cptId, String cptJsonSchemaNew) {
        Cpt.MetaData metaData = new Cpt.MetaData();
        metaData.setCptPublisher(did);
        metaData.setUpdated(DateUtils.getCurrentTimeStampString());
        CptBaseInfo cptBaseInfo = new CptBaseInfo();
        cptBaseInfo.setCptId(cptId);
        Map response = cptContract.updateCpt(cptBaseInfo, metaData, cptJsonSchemaNew);
        logger.info("[updateCpt] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            return new ResponseData<CptBaseInfo>(null, ErrorCode.SUCCESS);
        } else {
            logger.error("[updateCpt] update cpt failed because com.blockmap.did.core.contract invoke error.");
            return new ResponseData<>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    @Override
    public ResponseData<CptBaseInfo> registerCpt(String did, int cptId, String cptJsonSchemaNew, Sign.SignatureData signatureData){

        Cpt.MetaData metaData = new Cpt.MetaData();
        metaData.setCptPublisher(DidUtils.convertDidToAddress(did));
        metaData.setCreated(DateUtils.getCurrentTimeStampString());
        metaData.setUpdated(DateUtils.getCurrentTimeStampString());
        String cptSignature = new String(DataToolUtils.base64Encode(DataToolUtils.simpleSignatureSerialization(signatureData)), StandardCharsets.UTF_8);
        metaData.setCptSignature(cptSignature);
        CptBaseInfo cptBaseInfo = new CptBaseInfo();
        cptBaseInfo.setCptId(cptId);
        cptBaseInfo.setCptVersion(1);

        Map response = cptContract.regidterCpt(cptBaseInfo, metaData, cptJsonSchemaNew);
        logger.info("[registerCpt] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            return new ResponseData<CptBaseInfo>(cptBaseInfo, ErrorCode.SUCCESS);
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            if (blockchainMessage == null){
                JSONObject blockchainResponse = JSON.parseObject(response.get("blockchainResponse").toString());
                JSONObject result = JSON.parseObject(blockchainResponse.getString("result"));
                JSONObject error = JSON.parseObject(result.getString("error"));
                if(error.getString("code").equalsIgnoreCase("SYSERR01")){
                    logger.error("[registerCpt] registerCpt failed, because " + ErrorCode.TRANSACTION_TIMEOUT);
                    return new ResponseData<>(null, ErrorCode.TRANSACTION_TIMEOUT);
                }
                if (error.getInteger("code") == -32000){
                    logger.error("[registerCpt] registerCpt failed, because " + ErrorCode.USER_NOT_LOGIN);
                    return new ResponseData<>(null, ErrorCode.USER_NOT_LOGIN);
                }
                logger.error("[registerCpt] registerCpt failed, because " + ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
                return new ResponseData<>(null, ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
            }else {
                logger.error("[registerCpt] registerCpt failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
                return new ResponseData<>(null, ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")));
            }
        }
    }

    @Override
    public ResponseData<CptBaseInfo> registerCpt(String did, String cptJsonSchemaNew) {
        return null;
    }

    @Override
    public ResponseData<Cpt> queryCpt(int cptId) {
        Map response = cptContract.queryCpt(cptId);
        logger.info("[queryCpt] " + StringUtils.join(response));
        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
        if (isSuccess) {
            Cpt cpt = new Cpt();
            cpt.setCptId(cptId);
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            JSONObject queryResult = blockchainMessage.getJSONObject("queryResult");
            String cptJsonSchema = queryResult.getString("cptJsonSchema");
            Map<String, Object> jsonSchemaMap = DataToolUtils.deserialize(cptJsonSchema.trim(), HashMap.class);
            cpt.setCptJsonSchema(jsonSchemaMap);
            cpt.setCptBaseInfo(JSON.parseObject(queryResult.get("cptBaseInfo").toString(), CptBaseInfo.class));
            cpt.setMetaData(JSON.parseObject(queryResult.get("cptMetaData").toString(), Cpt.MetaData.class));
            ResponseData<Cpt> responseData = new ResponseData<Cpt>(cpt, ErrorCode.SUCCESS);
            return responseData;
        } else {
            JSONObject blockchainMessage = JSON.parseObject(response.get("blockchainMessage").toString());
            if (blockchainMessage == null){
                JSONObject blockchainResponse = JSON.parseObject(response.get("blockchainResponse").toString());
                JSONObject result = JSON.parseObject(blockchainResponse.getString("result"));
                JSONObject error = JSON.parseObject(result.getString("error"));
                if(error.getString("code").equalsIgnoreCase("SYSERR01")){
                    logger.error("[queryCpt] queryCpt failed, because " + ErrorCode.TRANSACTION_TIMEOUT);
                    return new ResponseData<>(null, ErrorCode.TRANSACTION_TIMEOUT);
                }
                if (error.getInteger("code") == -32000){
                    logger.error("[queryCpt] queryCpt failed, because " + ErrorCode.USER_NOT_LOGIN);
                    return new ResponseData<>(null, ErrorCode.USER_NOT_LOGIN);
                }
                logger.error("[queryCpt] queryCpt failed, because " + ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
                return new ResponseData<>(null, ErrorCode.BLOCKCHAIN_COMPONENTS_ERROR);
            }else {
                logger.error("[queryCpt] queryCpt failed, because " + ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")).getCodeDesc());
                return new ResponseData<>(null, ErrorCode.getTypeByErrorCode(blockchainMessage.getInteger("errorCode")));
            }
        }
    }

    @Override
    public ResponseData<CredentialTemplateEntity> queryCredentialTemplate(Integer cptId) {
        return null;
    }

}
