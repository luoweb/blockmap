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

import com.blockmap.did.core.protocol.base.Credential;
import com.blockmap.did.core.protocol.base.CredentialWrapper;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.base.DidPublicKey;
import com.blockmap.did.core.protocol.request.CreateCredentialArgs;
import com.blockmap.did.core.protocol.response.ResponseData;

import java.util.List;

/**
 * Service inf for operations on Credentials.
 *
 */
public interface CredentialService {

    /**
     * Generate a credential for full claim content.
     *
     * @param args the args
     * @return credential wrapper
     */
    ResponseData<CredentialWrapper> createCredential(CreateCredentialArgs args);

    /**
     * WARNING: To be deprecated in near future. We strongly suggest to use CredentialPojo for
     * multi-signature purpose. This can add an extra signer and signature to a Credential. Multiple
     * signatures will be appended in an embedded manner.
     *
     * @param credentialList original credential
     * @param didPrivateKey the passed-in privateKey to add sign
     * @return the modified CredentialWrapper
     */
    @Deprecated
    ResponseData<Credential> addSignature(
        List<Credential> credentialList,
        DidPrivateKey didPrivateKey);

    /**
     * Generate a credential with selected data. Embedded multi-signed Credential are not allowed.
     *
     * @param credential the credential
     * @param disclosure the setting of disclosure, such as: {@code{"name":1,"gender":0,"age":1}},
     *     which means you WILL disclose "name" and "age", and "gender" WILL NOT be disclosed
     * @return CredentialWrapper
     */
    ResponseData<CredentialWrapper> createSelectiveCredential(
        Credential credential,
        String disclosure
    );

    /**
     * Verify the validity of a credential. Public key will be fetched from chain. If the credential
     * is multi-signed, it will verify each signature in an embedded manner.
     *
     * @param credential the credential
     * @return the verification result. True if yes, false otherwise with exact verify error codes
     */
    ResponseData<Boolean> verify(Credential credential);

    /**
     * Verify the validity of a credential. Public key will be fetched from chain. If the credential
     * * is multi-signed, it will verify each signature in an embedded manner.
     *
     * @param credentialWrapper the credentialWrapper
     * @return the verification result. True if yes, false otherwise with exact verify error codes
     */
    ResponseData<Boolean> verify(CredentialWrapper credentialWrapper);

    /**
     * Verify the validity of a credential. Public key must be provided. Embedded multi-signed
     * Credential are not allowed.
     *
     * @param credentialWrapper the credential wrapper
     * @param didPublicKey the specified public key which used to verify credential signature
     * @return the verification result. True if yes, false otherwise with exact verify error codes
     */
    ResponseData<Boolean> verifyCredentialWithSpecifiedPubKey(
        CredentialWrapper credentialWrapper,
        DidPublicKey didPublicKey
    );

    /**
     * Get the full hash value of a Credential. All fields in the Credential will be included. This
     * method should be called when creating and verifying the Credential Evidence.
     *
     * @param credential the args
     * @return the Credential Hash value
     */
    ResponseData<String> getCredentialHash(Credential credential);

    /**
     * Get the full hash value of a Credential with its selectively-disclosure map. All fields in
     * the Credential will be included. This method should be called when creating and verifying the
     * Credential Evidence and the result is selectively-disclosure irrelevant.
     *
     * @param credential the args
     * @return the Credential Hash value
     */
    ResponseData<String> getCredentialHash(CredentialWrapper credential);

    /**
     * WARNING: To be deprecated in near future - use DataToolUtils.serialize() instead! Get the
     * Json String of a Credential. All fields in the Credential will be included. This also
     * supports the selectively disclosed Credential.
     *
     * @param credential the credential
     * @return the Credential Json value in String
     */
    @Deprecated
    ResponseData<String> getCredentialJson(Credential credential);

}
