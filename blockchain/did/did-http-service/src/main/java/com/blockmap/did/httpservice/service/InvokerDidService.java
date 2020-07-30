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


import com.icbc.did.core.protocol.response.CreateDidDataResult;
import com.icbc.did.core.protocol.response.ResponseData;
import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import org.springframework.stereotype.Service;

@Service
public interface InvokerDidService {

    /**
     * Create  DID - a raw method for test purpose only.
     *
     * @return the response data
     */
    ResponseData<CreateDidDataResult> createDid();

    /**
     * Check if  DID exists on Chain - for test purpose only.
     *
     * @param did the  DID
     * @return true if exists, false otherwise
     */
    ResponseData<Boolean> isDidExist(String did);

    /**
     * Call to DID SDK with direct transaction hex String, to create DID.
     *
     * @param transactionHex the transactionHex value
     * @return String in ResponseData
     */
    HttpResponseData<String> createDidWithTransactionHex(String transactionHex);

    /**
     * Get a  DID Document Json via the InvokeFunction API.
     *
     * @param getDidDocumentJsonArgs the  DID
     * @return the  DID document json
     */
    HttpResponseData<Object> getDidDocumentJsonInvoke(InputArg getDidDocumentJsonArgs);

    /**
     * Create DID via the InvokeFunction API.
     *
     * @param createDidJsonArgs the input args, should be almost null
     * @return the  DID
     */
    public HttpResponseData<Object> createDidInvoke(InputArg createDidJsonArgs);
}
