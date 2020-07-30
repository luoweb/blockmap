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

import com.icbc.did.httpservice.constant.DidFunctionNames;
import com.icbc.did.httpservice.constant.HttpReturnCode;
import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import com.icbc.did.httpservice.service.*;
import com.icbc.did.httpservice.util.TransactionEncoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: Handling Transaction related services.
 * @File: TransactionServiceImpl
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

@Component
public class TransactionServiceImpl extends BaseService implements TransactionService {

    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


    private InvokerDidService invokerDidService = new InvokerDidServiceImpl();
    private InvokerQrCodeService invokerQrCodeService = new InvokerQrCodeServiceImpl();
    private UserService userService = new UserServiceImpl();
    private InvokerCredentialService invokerCredentialService = new InvokerCredentialServiceImpl();

    /**
     * Create an Encoded Transaction.
     *
     * @param encodeTransactionJsonArgs json format args. It should contain 4 keys: functionArgs
     * (including all business related params), transactionArgs, functionName and apiVersion.
     * Hereafter, functionName will decide which DID SDK method to engage, and assemble all input
     * params into SDK readable format to send there; apiVersion is for extensibility purpose.
     * @return encoded transaction in Base64 format, and the data segment in RawTransaction.
     */
    @Override
    public HttpResponseData<Object> encodeTransaction(
        String encodeTransactionJsonArgs) {
        return null;
    }

    /**
     * Send Transaction to Blockchain.
     *
     * @param sendTransactionJsonArgs the json format args. It should contain 4 keys: functionArgs
     * (including all business related params), transactionArgs, functionName and apiVersion.
     * @return the json string from SDK response.
     */
    @Override
    public HttpResponseData<Object> sendTransaction(String sendTransactionJsonArgs) {
        return null;
    }

    /**
     * Directly invoke an SDK function. No client-side sign needed.
     *
     * @param invokeFunctionJsonArgs the json format args. It should contain 4 keys: functionArgs,
     * (including all business related params), EMPTY transactionArgs, functionName and apiVersion.
     * @return the json string from SDK response.
     */
    @Override
    public HttpResponseData<Object> invokeFunction(String invokeFunctionJsonArgs) {
        HttpResponseData<InputArg> resp = TransactionEncoderUtil
            .buildInputArg(invokeFunctionJsonArgs);
        InputArg inputArg = resp.getRespBody();
        if (inputArg == null) {
            logger.error("Failed to build input argument: {}", invokeFunctionJsonArgs);
            return new HttpResponseData<>(null, resp.getErrorCode(), resp.getErrorMessage());
        }
        String functionName = inputArg.getFunctionName();
        try {

            if (functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_CREATE_DID)) {
                return invokerDidService.createDidInvoke(inputArg);
            }
            if(functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_CREATE_QRCODE)){
                return invokerQrCodeService.createQrCodeInvoke(inputArg);
            }
            if(functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_ACTIVE_QRCODE)){
                return invokerQrCodeService.activeQrCodeInvoke(inputArg);
            }
            if(functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_LOGIN)){
                return userService.login(inputArg);
            }
            if (functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_GET_DID_DOCUMENT)
                    || functionName
                    .equalsIgnoreCase(DidFunctionNames.FUNCNAME_GET_DID_DOCUMENT_JSON)) {
                return invokerDidService.getDidDocumentJsonInvoke(inputArg);
            }
            if (functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_CREATE_CREDENTIAL)) {
                return invokerCredentialService.createCredentialInvoke(inputArg);
            }
            if (functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_VERIFY_CREDENTIAL)) {
                return invokerCredentialService.verifyCredentialInvoke(inputArg);
            }
            if(functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_GET_CREDENTIAL_DETAIL)){
                return invokerCredentialService.getCredentialDetailInvoke(inputArg);
            }
            if(functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_GET_ALL_CREDENTIAL)){
                return invokerCredentialService.getAllCredentialInvoke(inputArg);
            }
            if(functionName.equalsIgnoreCase(DidFunctionNames.FUNCNAME_GET_CREDENTIAL_BY_QRCODE_ID)){
                return invokerCredentialService.getCredentialsByQrCodeIdInvoke(inputArg);
            }
            logger.error("Function name undefined: {}.", functionName);
            return new HttpResponseData<>(null, HttpReturnCode.FUNCTION_NAME_ILLEGAL);
        } catch (Exception e) {
            logger.error("[invokeFunction]: unknown error with input argument {}",
                invokeFunctionJsonArgs,
                e);
            return new HttpResponseData<>(null, HttpReturnCode.UNKNOWN_ERROR.getCode(),
                HttpReturnCode.UNKNOWN_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }
}
