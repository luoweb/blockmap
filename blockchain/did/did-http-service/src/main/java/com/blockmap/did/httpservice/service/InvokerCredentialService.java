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

import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import org.springframework.stereotype.Service;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description:
 * @File: InvokerCredentialService
 * @Version: 1.0.0
 * @Date: 2020/1/6 17:22
 */




@Service
public interface InvokerCredentialService {

    /**
     * Generate a credential for client to sign. The signature field is null, and both full claim
     * and claimHash will be returned. The returned json String is an key-ordered compact json.
     *
     * @param createCredentialFuncArgs the functionArgs
     * @return the Map contains Credential content and claimHash.
     */
    HttpResponseData<Object> createCredentialInvoke(InputArg createCredentialFuncArgs);

    /**
     * Verify the validity of a credential. Need format conversion (UTC date and @context)
     *
     * @param verifyCredentialFuncArgs the credential json args
     * @return the Boolean response data
     */
    HttpResponseData<Object> verifyCredentialInvoke(InputArg verifyCredentialFuncArgs);

    HttpResponseData<Object> getCredentialDetailInvoke(InputArg verifyCredentialFuncArgs);

    HttpResponseData<Object> getAllCredentialInvoke(InputArg verifyCredentialFuncArgs);

    HttpResponseData<Object> getCredentialsByQrCodeIdInvoke(InputArg verifyCredentialFuncArgs);


}