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

import com.blockmap.did.core.protocol.base.Cpt;
import com.blockmap.did.core.protocol.base.CptBaseInfo;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.impl.engine.CptServiceEngine;
import com.blockmap.did.core.web3j.crypto.Sign;
import com.webank.wedpr.selectivedisclosure.CredentialTemplateEntity;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description:
 * @File: CptServiceEngineV2
 * @Version: 1.0.0
 * @Date: 2020/1/9 16:33
 */

public class CptServiceEngineV2 implements CptServiceEngine {

    @Override
    public ResponseData<CptBaseInfo> updateCpt(String did, int cptId, String cptJsonSchemaNew) {
        return null;
    }

    @Override
    public ResponseData<CptBaseInfo> registerCpt(String did, int cptId, String cptJsonSchemaNew, Sign.SignatureData signatureData) {
        return null;
    }

    @Override
    public ResponseData<CptBaseInfo> registerCpt(String did, String cptJsonSchemaNew) {
        return null;
    }

    @Override
    public ResponseData<Cpt> queryCpt(int cptId) {
        return null;
    }

    @Override
    public ResponseData<CredentialTemplateEntity> queryCredentialTemplate(Integer cptId) {
        return null;
    }
}
