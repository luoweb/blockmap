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


package com.blockmap.did.core.service;

import com.blockmap.did.core.protocol.base.DidDocument;
import com.blockmap.did.core.protocol.request.CreateDidArgs;
import com.blockmap.did.core.protocol.request.SetAuthenticationArgs;
import com.blockmap.did.core.protocol.request.SetPublicKeyArgs;
import com.blockmap.did.core.protocol.request.SetServiceArgs;
import com.blockmap.did.core.protocol.response.CreateDidDataResult;
import com.blockmap.did.core.protocol.response.ResponseData;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: Service inf for operations on  DID.
 * @File: DidService
 * @Version: 1.0.0
 * @Date: 2019/12/16 20:20
 */


public interface DidService {

    /**
     * Create a  DID without a keypair. SDK will generate a keypair for the caller.
     *
     * @return a data set including a  DID and a keypair
     */
    ResponseData<CreateDidDataResult> createDid();

    /**
     * Create a  DID from the provided public key.
     *
     * @param createDidArgs you need to input a public key
     * @return  DID
     */
    ResponseData<String> createDid(CreateDidArgs createDidArgs);

    /**
     * Query  DID document.
     *
     * @param did the  DID
     * @return  document in json type
     */
    ResponseData<String> getDidDocumentJson(String did);

    /**
     * Query DID document.
     *
     * @param did the DID
     * @return did document in java object type
     */
    ResponseData<DidDocument> getDidDocument(String did);

    /**
     * Set public key in the DID Document.
     *
     * @param setPublicKeyArgs the set public key args
     * @return true if the "set" operation succeeds, false otherwise.
     */
    ResponseData<Boolean> setPublicKey(SetPublicKeyArgs setPublicKeyArgs);

    /**
     * Set com.blockmap.did.core.service properties.
     *
     * @param setServiceArgs your com.blockmap.did.core.service name and endpoint
     * @return true if the "set" operation succeeds, false otherwise.
     */
    ResponseData<Boolean> setService(SetServiceArgs setServiceArgs);

    /**
     * Set authentications in  DID.
     *
     * @param setAuthenticationArgs A public key is needed.
     * @return true if the "set" operation succeeds, false otherwise.
     */
    ResponseData<Boolean> setAuthentication(SetAuthenticationArgs setAuthenticationArgs);

    /**
     * Check if the DID exists on chain.
     *
     * @param did The DID.
     * @return true if exists, false otherwise.
     */
    ResponseData<Boolean> isDidExist(String did);
}
