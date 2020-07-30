/*
 *       Copyright© (2020) ICBC Co., Ltd.
 *
 *       This file is part of did-http-com.icbc.did.core.service.
 *
 *       did-http-com.icbc.did.core.service is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       did-http-com.icbc.did.core.service is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with did-http-com.icbc.did.core.service.  If not, see <https://www.gnu.org/licenses/>.
 */


package com.icbc.did.httpservice.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbc.did.httpservice.constant.DidParamKeyConstant;
import com.icbc.did.httpservice.constant.HttpReturnCode;
import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: Handling all tasks related to encoding and sending transactions.
 * @File: TransactionEncoderUtil
 * @Version: 1.0.0
 * @Date: 2019/12/24 14:43
 */

public class TransactionEncoderUtil {

    private static Logger logger = LoggerFactory.getLogger(TransactionEncoderUtil.class);

    /**
     * Extract and build Input arg for all Service APIs.
     *
     * @param inputJson the inputJson String
     * @return An InputArg instance
     */
    public static HttpResponseData<InputArg> buildInputArg(String inputJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputJson);
            if (jsonNode == null) {
                logger.error("Null input within: {}", inputJson);
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_NULL);
            }
            JsonNode functionNameNode = jsonNode.get(DidParamKeyConstant.FUNCTION_NAME);
            JsonNode versionNode = jsonNode.get(DidParamKeyConstant.API_VERSION);
            if (functionNameNode == null || StringUtils.isEmpty(functionNameNode.textValue())) {
                logger.error("Null input within: {}", jsonNode.toString());
                return new HttpResponseData<>(null, HttpReturnCode.FUNCTION_NAME_ILLEGAL);
            }
            if (versionNode == null || StringUtils.isEmpty(versionNode.textValue())) {
                logger.error("Null input within: {}", jsonNode.toString());
                return new HttpResponseData<>(null, HttpReturnCode.VER_ILLEGAL);
            }
            // Need to use toString() for pure Objects and textValue() for pure String
            JsonNode functionArgNode = jsonNode.get(DidParamKeyConstant.FUNCTION_ARG);
            if (functionArgNode == null || StringUtils.isEmpty(functionArgNode.toString())) {
                logger.error("Null input within: {}", jsonNode.toString());
                return new HttpResponseData<>(null, HttpReturnCode.FUNCARG_ILLEGAL);
            }
            JsonNode txnArgNode = jsonNode.get(DidParamKeyConstant.TRANSACTION_ARG);
            if (txnArgNode == null || StringUtils.isEmpty(txnArgNode.toString())) {
                logger.error("Null input within: {}", jsonNode.toString());
                return new HttpResponseData<>(null, HttpReturnCode.TXNARG_ILLEGAL);
            }

            String functionArg = functionArgNode.toString();
            String txnArg = txnArgNode.toString();
            InputArg inputArg = new InputArg();
            inputArg.setFunctionArg(functionArg);
            inputArg.setTransactionArg(txnArg);
            inputArg.setFunctionName(functionNameNode.textValue());
            inputArg.setV(versionNode.textValue());
            return new HttpResponseData<>(inputArg, HttpReturnCode.SUCCESS);
        } catch (Exception e) {
            logger.error("Json Extraction error within: {}", inputJson);
            return new HttpResponseData<>(null, HttpReturnCode.UNKNOWN_ERROR.getCode(),
                    HttpReturnCode.UNKNOWN_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }

}
