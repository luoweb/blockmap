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

package com.icbc.did.httpservice.service;
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


import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import org.springframework.stereotype.Service;


/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: Handling Transaction related services.
 * @File: TransactionService
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */


@Service
public interface TransactionService {

    /**
     * Create an Encoded Transaction.
     *
     * @param encodeTransactionJsonArgs json format args. It should contain 4 keys: functionArgs
     * (including all business related params), transactionArgs, functionName and apiVersion.
     * Hereafter, functionName will decide which DID SDK method to engage, and assemble all input
     * params into SDK readable format to send there; apiVersion is for extensibility purpose.
     * @return encoded transaction in Base64 format, and the data segment in RawTransaction.
     */
    HttpResponseData<Object> encodeTransaction(String encodeTransactionJsonArgs);

    /**
     * Send Transaction to Blockchain.
     *
     * @param sendTransactionJsonArgs the json format args. It should contain 4 keys: functionArgs
     * (including all business related params), transactionArgs, functionName and apiVersion.
     * @return the json string from SDK response.
     */
    HttpResponseData<Object> sendTransaction(String sendTransactionJsonArgs);

    /**
     * Directly invoke an SDK function. No client-side sign needed.
     *
     * @param invokeFunctionJsonArgs the json format args. It should contain 4 keys: functionArgs,
     * (including all business related params), EMPTY transactionArgs, functionName and apiVersion.
     * @return the json string from SDK response.
     */
    HttpResponseData<Object> invokeFunction(String invokeFunctionJsonArgs);
}
