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


package com.blockmap.did.core.service.impl.engine;

import com.blockmap.did.core.protocol.base.DidDocument;
import com.blockmap.did.core.protocol.response.ResponseData;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description:
 *              this service engine calls the contract methods and process blockchain response data on FABRIC or FABRIC
 *              1.3.x or on FABRIC 2.0,
 *              0.6 or 1.x or 2.x FABRIC.
 * @File: DidServiceEngine
 * @Version: 1.0.0
 * @Date: 2019/12/18 10:01
 */


public interface DidServiceEngine {

    /**
     * call did contract to create a new did.
     *
     * @param didAddress identity on blockchain
     * @param publicKey public key of the identity
     * @param privateKey privateKey identity's private key
     * @return result
     */
    ResponseData<Boolean> createDid(String didAddress, String publicKey, String privateKey);

    /**
     * write attribute to blockchain.
     *
     * @param didAddress identity on blockchain
     * @param attributeKey the key of the attribute
     * @param value the value of the attribute
     * @param privateKey identity's private key
     * @return result
     */
    ResponseData<Boolean> setAttribute(
            String didAddress,
            String attributeKey,
            String value,
            String privateKey
    );

    /**
     * check if the did exists on blockchain.
     *
     * @param did the did of the entity
     * @return result
     */
    ResponseData<Boolean> isDidExist(String did);

    /**
     * get did document from blockchain.
     *
     * @param did the entity's did
     * @return did document
     */
    ResponseData<DidDocument> getDidDocument(String did);
}
