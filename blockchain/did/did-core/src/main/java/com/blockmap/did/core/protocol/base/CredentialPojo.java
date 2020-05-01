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

import com.blockmap.did.core.exception.DataTypeCastException;
import com.blockmap.did.core.util.CredentialPojoUtils;
import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.constant.ParamKeyConstant;
import com.blockmap.did.core.protocol.inf.Hashable;
import com.blockmap.did.core.protocol.inf.IProof;
import com.blockmap.did.core.protocol.inf.JsonSerializer;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The base data structure to handle Credential info.
 *
 */
@Data
public class CredentialPojo implements IProof, JsonSerializer, Hashable {

    private static final Logger logger = LoggerFactory.getLogger(CredentialPojo.class);

    /**
     * the serialVersionUID.
     */
    private static final long serialVersionUID = 8197843857223846978L;

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
    private Map<String, Object> proof;

    /**
     * Required: The credential type default is VerifiableCredential.
     */
    private List<String> type;

    /**
     * 添加type.
     *
     * @param typeValue the typeValue
     */
    public void addType(String typeValue) {
        if (type == null) {
            type = new ArrayList<String>();
        }
        type.add(typeValue);
    }

    /**
     * Directly extract the signature value from credential.
     *
     * @return signature value
     */
    public String getSignature() {
        return toString(getValueFromProof(proof, ParamKeyConstant.PROOF_SIGNATURE));
    }

    /**
     * Directly extract the proof type from credential.
     *
     * @return proof type
     */
    public String getProofType() {
        return toString(getValueFromProof(proof, ParamKeyConstant.PROOF_TYPE));
    }

    /**
     * Directly extract the salt from credential.
     *
     * @return salt
     */
    public Map<String, Object> getSalt() {
        return (Map<String, Object>) getValueFromProof(proof, ParamKeyConstant.PROOF_SALT);
    }

    /**
     * put the salt into proof.
     *
     * @param salt map of salt
     */
    public void setSalt(Map<String, Object> salt) {
        putProofValue(ParamKeyConstant.PROOF_SALT, salt);
    }

    /**
     * put the key-value into proof.
     *
     * @param key the key of proof
     * @param value the value of proof
     */
    public void putProofValue(String key, Object value) {
        if (proof == null) {
            proof = new HashMap<>();
        }
        proof.put(key, value);
    }

    /**
     * convert CredentialPojo to JSON String.
     *
     * @return CredentialPojo
     */
    @Override
    public String toJson() {
        String json = DataToolUtils.convertTimestampToUtc(DataToolUtils.serialize(this));
        return DataToolUtils.addTagFromToJson(json);
    }

    /**
     * create CredentialPojo with JSON String.
     *
     * @param credentialJson the CredentialPojo JSON String
     * @return CredentialPojo
     */
    public static CredentialPojo fromJson(String credentialJson) {
        if (StringUtils.isBlank(credentialJson)) {
            logger.error("create credential with JSON String failed, "
                + "the credential JSON String is null");
            throw new DataTypeCastException("the credential JSON String is null");
        }
        String credentialString = credentialJson;
        if (DataToolUtils.isValidFromToJson(credentialJson)) {
            credentialString = DataToolUtils.removeTagFromToJson(credentialJson);
        }
        CredentialPojo credentialPojo = DataToolUtils.deserialize(
            DataToolUtils.convertUtcToTimestamp(credentialString),
            CredentialPojo.class
        );
        ErrorCode checkResp = CredentialPojoUtils.isCredentialPojoValid(credentialPojo);
        if (ErrorCode.SUCCESS.getCode() != checkResp.getCode()) {
            logger.error("create CredentialPojo with JSON String failed, {}",
                checkResp.getCodeDesc());
            throw new DataTypeCastException(checkResp.getCodeDesc());
        }
        if (!CredentialPojoUtils.validClaimAndSaltForMap(
            credentialPojo.getClaim(),
            credentialPojo.getSalt())) {
            logger.error("create PresentationE with JSON String failed, claim and salt of "
                + "credentialPojo not match.");
            throw new DataTypeCastException("claim and salt of credentialPojo not match.");
        }
        return credentialPojo;
    }

    /**
     * Generate the unique hash of this CredentialPojo.
     *
     * @return hash value
     */
    @Override
    public String getHash() {
        if (CredentialPojoUtils.isCredentialPojoValid(this) != ErrorCode.SUCCESS) {
            return StringUtils.EMPTY;
        }
        return CredentialPojoUtils.getCredentialPojoHash(this, null);
    }

    /**
     * Get the signature thumbprint for re-signing.
     *
     * @return thumbprint
     */
    public String getSignatureThumbprint() {
        return CredentialPojoUtils.getCredentialThumbprintWithoutSig(this, this.getSalt(), null);
    }
}
