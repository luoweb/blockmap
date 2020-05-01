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

import com.blockmap.did.core.protocol.base.Cpt;
import com.blockmap.did.core.protocol.base.CptBaseInfo;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.web3j.crypto.Sign;
import com.webank.wedpr.selectivedisclosure.CredentialTemplateEntity;

/**
 * 针对不同版本的FABRIC，做不同的CPT合约接口调用和数据处理 目前分为支持FABRIC 1.3.x和FABRIC 2.0版本
 *
 * @author admin@xuzhijun.com.cn 2019年12月25日
 */
public interface CptServiceEngine {

    /**
     * call cpt contract to update cpt based on cptid.
     *
     * @param did publisher's cptId
     * @param cptId cptid
     * @param cptJsonSchemaNew cpt content
     * @return result
     */
    ResponseData<CptBaseInfo> updateCpt(
            String did,
            int cptId,
            String cptJsonSchemaNew
    );

    /**
     * call cpt contract to register cpt with the specific cptid.
     *
     * @param did publisher's address
     * @param cptId cptid
     * @param cptJsonSchemaNew cpt content
     * @param signatureData signature
     * @return result
     */
    ResponseData<CptBaseInfo> registerCpt(
            String did,
            int cptId,
            String cptJsonSchemaNew,
            Sign.SignatureData signatureData
    );

    /**
     * call cpt contract to register cpt.
     *
     * @param did publisher's did
     * @param cptJsonSchemaNew cpt content
     * @return result
     */
    ResponseData<CptBaseInfo> registerCpt(
            String did,
            String cptJsonSchemaNew
    );

    /**
     * call cpt contract method to query cpt info from blockchain.
     *
     * @param cptId the id of the cpt
     * @return cpt info
     */
    ResponseData<Cpt> queryCpt(int cptId);


    /**
     * query cpt credential template.
     *
     * @param cptId the id of the cpt
     * @return Cpt Credential Template
     */
    ResponseData<CredentialTemplateEntity> queryCredentialTemplate(Integer cptId);

}
