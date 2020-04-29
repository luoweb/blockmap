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

package com.blockmap.did.core.protocol.base;

import com.blockmap.did.core.util.CredentialUtils;
import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.constant.ParamKeyConstant;
import com.blockmap.did.core.protocol.inf.Hashable;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * The base data structure to handle Credential info.
 *
 */
@Data
public class Credential implements Hashable {

    /**
     * Required: The context field.
     */
    private String context;

    /**
     * Required: The ID.
     */
    private String id;

    /**
     * Required: The CPT type in standard integer format.
     */
    private Integer cptId;

    /**
     * Required: The issuer  DID.
     */
    private String issuer;

    /**
     * Required: The create date.
     */
    private Long issuanceDate;

    /**
     * Required: The expire date.
     */
    private Long expirationDate;

    /**
     * Required: The claim data.
     */
    private Map<String, Object> claim;

    /**
     * Required: The credential proof data.
     */
    private Map<String, String> proof;

    /**
     * Directly extract the signature value from credential.
     *
     * @return signature value
     */
    public String getSignature() {
        return getValueFromProof(ParamKeyConstant.CREDENTIAL_SIGNATURE);
    }

    /**
     * Directly extract the proof type from credential.
     *
     * @return proof type
     */
    public String getProofType() {
        return getValueFromProof(ParamKeyConstant.PROOF_TYPE);
    }

    private String getValueFromProof(String key) {
        if (proof != null) {
            return proof.get(key);
        }
        return StringUtils.EMPTY;
    }

    /**
     * Get the unique hash value of this Credential.
     *
     * @return hash value
     */
    public String getHash() {
        if (CredentialUtils.isCredentialValid(this) != ErrorCode.SUCCESS) {
            return StringUtils.EMPTY;
        }
        return CredentialUtils.getCredentialHash(this);
    }

    /**
     * Get the signature thumbprint for re-signing.
     *
     * @return thumbprint
     */
    public String getSignatureThumbprint() {
        return CredentialUtils.getCredentialThumbprint(this, null);
    }
}
