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
import com.blockmap.did.core.protocol.inf.Hashable;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Credential response.
 *
 */
@Data
public class CredentialWrapper implements Hashable {

    /**
     * Required: The Credential.
     */
    private Credential credential;

    /**
     * Required: key is the credential field, and value "1" for disclosure to the third party, "0"
     * for no disclosure.
     */
    private Map<String, Object> disclosure;

    /**
     * Generate the unique hash of this CredentialWrapper.
     *
     * @return hash value
     */
    public String getHash() {
        if (this == null) {
            return StringUtils.EMPTY;
        }
        if (this.getDisclosure() == null || this.getDisclosure().size() == 0) {
            return this.getCredential().getHash();
        }
        Credential credential = this.getCredential();
        if (CredentialUtils.isCredentialValid(credential) != ErrorCode.SUCCESS) {
            return StringUtils.EMPTY;
        }
        return CredentialUtils.getCredentialWrapperHash(this);
    }

    /**
     * Directly extract the signature value from credential.
     *
     * @return signature value
     */
    public String getSignature() {
        return credential.getSignature();
    }

    /**
     * Get the signature thumbprint for re-signing.
     *
     * @return thumbprint
     */
    public String getSignatureThumbprint() {
        return CredentialUtils.getCredentialThumbprint(this.getCredential(), this.getDisclosure());
    }
}
