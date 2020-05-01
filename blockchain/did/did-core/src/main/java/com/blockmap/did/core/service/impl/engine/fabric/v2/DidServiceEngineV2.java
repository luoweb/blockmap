/*
 *       Copyright© (2020) blockmap Co., Ltd.
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


package com.blockmap.did.core.service.impl.engine.fabric.v2;

import com.blockmap.did.core.protocol.base.DidDocument;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.impl.engine.DidServiceEngine;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description:
 * @File: DidServiceEngineV2
 * @Version: 1.0.0
 * @Date: 2020/1/9 16:21
 */

public class DidServiceEngineV2 implements DidServiceEngine {
    @Override
    public ResponseData<Boolean> createDid(String didAddress, String publicKey, String privateKey) {
        return null;
    }

    @Override
    public ResponseData<Boolean> setAttribute(String didAddress, String attributeKey, String value, String privateKey) {
        return null;
    }

    @Override
    public ResponseData<Boolean> isDidExist(String did) {
        return null;
    }

    @Override
    public ResponseData<DidDocument> getDidDocument(String did) {
        return null;
    }
}
