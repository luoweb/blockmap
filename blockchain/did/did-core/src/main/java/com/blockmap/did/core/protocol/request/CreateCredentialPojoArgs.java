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


package com.blockmap.did.core.protocol.request;

import com.blockmap.did.core.util.CredentialUtils;
import lombok.Data;

import com.blockmap.did.core.protocol.base.DidAuthentication;


/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: The Arguments for the following SDK API: createCredential().
 * @File: CreateCredentialPojoArgs
 * @Version: 1.0.0
 * @Date: 2019/12/16 19:26
 */


@Data
public class CreateCredentialPojoArgs<T> {

    /**
     * Required: The CPT type in standard integer format.
     */
    private Integer cptId;

    /**
     * Required: The issuer WeIdentity DID.
     */
    private String issuer;

    /**
     * Required: The expire date.
     */
    private Long expirationDate;

    /**
     * Required: The claim data.
     */
    private T claim;

    /**
     * Required: The private key structure used for signing.
     */
    private DidAuthentication didAuthentication;

    /**
     * Optional: The issuance date of the credential.
     */
    private Long issuanceDate = null;

    /**
     * 新增字段，issuer提前生成好的credential ID，对应credentialPojo里的ID.
     */
    private String id = null;

    /**
     * Optional:credential context.
     */
    private String context = CredentialUtils.getDefaultCredentialContext();
}
