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

package com.blockmap.did.core.service.impl;

import com.blockmap.did.core.constant.CredentialConstant;
import com.blockmap.did.core.constant.CredentialFieldDisclosureValue;
import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.constant.ParamKeyConstant;
import com.blockmap.did.core.exception.DidBaseException;
import com.blockmap.did.core.protocol.base.*;
import com.blockmap.did.core.protocol.request.CreateCredentialArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.CptService;
import com.blockmap.did.core.service.CredentialService;
import com.blockmap.did.core.service.DidService;
import com.blockmap.did.core.util.CredentialUtils;
import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.util.DateUtils;
import com.blockmap.did.core.util.DidUtils;
import com.blockmap.did.core.protocol.base.*;
import org.apache.commons.lang3.StringUtils;
import com.blockmap.did.core.web3j.crypto.ECKeyPair;
import com.blockmap.did.core.web3j.crypto.Keys;
import com.blockmap.did.core.web3j.crypto.Sign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.*;

/**
 * Service implementations for operations on Credential.
 *
 * @author xuzj01@sdc.blockmap.com 2019.1
 */
public class CredentialServiceImpl implements CredentialService {

    private static final Logger logger = LoggerFactory.getLogger(CredentialServiceImpl.class);

    private CptService cptService = new CptServiceImpl();

    private DidService didService = new DidServiceImpl();


    /**
     * Generate a credential.
     *
     * @param args the args
     * @return the Credential response data
     */
    @Override
    public ResponseData<CredentialWrapper> createCredential(CreateCredentialArgs args) {

        CredentialWrapper credentialWrapper = new CredentialWrapper();
        try {
            ErrorCode innerResponse = checkCreateCredentialArgsValidity(args, true);
            if (ErrorCode.SUCCESS.getCode() != innerResponse.getCode()) {
                logger.error("Generate Credential input format error!");
                return new ResponseData<>(null, innerResponse);
            }

            Credential result = new Credential();
            String context = CredentialUtils.getDefaultCredentialContext();
            result.setContext(context);
            result.setId(UUID.randomUUID().toString());
            result.setCptId(args.getCptId());

            result.setIssuer(args.getIssuer());
            Long issuanceDate = args.getIssuanceDate();
            if (issuanceDate == null) {
                result.setIssuanceDate(DateUtils.getNoMillisecondTimeStamp());
            } else {
                Long newIssuanceDate =
                    DateUtils.convertToNoMillisecondTimeStamp(args.getIssuanceDate());
                if (newIssuanceDate == null) {
                    logger.error("Create Credential Args illegal.");
                    return new ResponseData<>(null, ErrorCode.CREDENTIAL_ISSUANCE_DATE_ILLEGAL);
                } else {
                    result.setIssuanceDate(newIssuanceDate);
                }
            }
            Long newExpirationDate =
                DateUtils.convertToNoMillisecondTimeStamp(args.getExpirationDate());
            if (newExpirationDate == null) {
                logger.error("Create Credential Args illegal.");
                return new ResponseData<>(null, ErrorCode.CREDENTIAL_EXPIRE_DATE_ILLEGAL);
            } else {
                result.setExpirationDate(newExpirationDate);
            }
            result.setClaim(args.getClaim());
            Map<String, Object> disclosureMap = new HashMap<>(args.getClaim());
            for (Map.Entry<String, Object> entry : disclosureMap.entrySet()) {
                disclosureMap.put(
                    entry.getKey(),
                    CredentialFieldDisclosureValue.DISCLOSED.getStatus()
                );
            }
            credentialWrapper.setDisclosure(disclosureMap);

            // Construct Credential Proof
            Map<String, String> credentialProof = CredentialUtils.buildCredentialProof(
                result,
                args.getDidPrivateKey().getPrivateKey(),
                disclosureMap);
            result.setProof(credentialProof);

            credentialWrapper.setCredential(result);
            ResponseData<CredentialWrapper> responseData = new ResponseData<>(
                credentialWrapper,
                ErrorCode.SUCCESS
            );

            return responseData;
        } catch (Exception e) {
            logger.error("Generate Credential failed due to system error. ", e);
            return new ResponseData<>(null, ErrorCode.CREDENTIAL_ERROR);
        }
    }

    private boolean isMultiSignedCredential(Credential credential) {
        if (credential == null) {
            return false;
        }
        return (credential.getCptId() == CredentialConstant.CREDENTIAL_EMBEDDED_SIGNATURE_CPT
            .intValue());
    }

    /**
     * Add an extra signer and signature to a Credential. Multiple signatures will be appended in an
     * embedded manner.
     *
     * @param credentialList original credential
     * @param didPrivateKey the passed-in privateKey and DID bundle to sign
     * @return the modified CredentialWrapper
     */
    @Override
    public ResponseData<Credential> addSignature(
        List<Credential> credentialList,
        DidPrivateKey didPrivateKey) {
        if (credentialList == null || credentialList.size() == 0 || !DidUtils
            .isPrivateKeyValid(didPrivateKey)) {
            return new ResponseData<>(null, ErrorCode.ILLEGAL_INPUT);
        }
        Credential result = new Credential();
        result.setCptId(CredentialConstant.CREDENTIAL_EMBEDDED_SIGNATURE_CPT);
        result.setIssuanceDate(DateUtils.getNoMillisecondTimeStamp());
        result.setId(UUID.randomUUID().toString());
        result.setContext(CredentialUtils.getDefaultCredentialContext());
        Long expirationDate = 0L;
        for (Credential arg : credentialList) {
            if (arg.getExpirationDate() > expirationDate) {
                expirationDate = arg.getExpirationDate();
            }
        }
        Long newExpirationDate =
            DateUtils.convertToNoMillisecondTimeStamp(expirationDate);
        if (newExpirationDate == null) {
            logger.error("Create Credential Args illegal.");
            return new ResponseData<>(null, ErrorCode.CREDENTIAL_EXPIRE_DATE_ILLEGAL);
        } else {
            result.setExpirationDate(newExpirationDate);
        }
        String privateKey = didPrivateKey.getPrivateKey();
        ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privateKey));
        String keyDid = DidUtils
            .convertAddressToDid(Keys.getAddress(keyPair)).toString();
        if (!didService.isDidExist(keyDid).getResult()) {
            return new ResponseData<>(null, ErrorCode.DID_DOES_NOT_EXIST);
        }
        result.setIssuer(keyDid);

        // Check and remove duplicates in the credentialList
        List<Credential> trimmedCredentialList = new ArrayList<>();
        for (Credential arg : credentialList) {
            boolean found = false;
            for (Credential credAlive : trimmedCredentialList) {
                if (CredentialUtils.isEqual(arg, credAlive)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                trimmedCredentialList.add(arg);
            }
        }

        Map<String, Object> claim = new HashMap<>();
        claim.put("credentialList", trimmedCredentialList);
        result.setClaim(claim);
        Map<String, String> credentialProof = CredentialUtils
            .buildCredentialProof(result, privateKey, null);
        result.setProof(credentialProof);
        return new ResponseData<>(result, ErrorCode.SUCCESS);
    }

    /**
     * Verify the validity of a credential without public key provided.
     *
     * @param credentialWrapper the credential wrapper.
     * @return the Boolean response data
     */
    @Override
    public ResponseData<Boolean> verify(CredentialWrapper credentialWrapper) {
        return verifyCredentialContent(credentialWrapper, null);
    }

    /**
     * Verify Credential validity.
     */
    @Override
    public ResponseData<Boolean> verify(Credential credential) {
        Map<String, Object> disclosureMap = new HashMap<>(credential.getClaim());
        for (Map.Entry<String, Object> entry : disclosureMap.entrySet()) {
            disclosureMap.put(entry.getKey(), CredentialFieldDisclosureValue.DISCLOSED.getStatus());
        }
        CredentialWrapper credentialWrapper = new CredentialWrapper();
        credentialWrapper.setCredential(credential);
        credentialWrapper.setDisclosure(disclosureMap);
        return verifyCredentialContent(credentialWrapper, null);
    }

    /**
     * Verify the validity of a credential with public key provided.
     *
     * @param credentialWrapper the args
     * @param didPublicKey the specific public key to verify the credential.
     * @return the Boolean response data
     */
    @Override
    public ResponseData<Boolean> verifyCredentialWithSpecifiedPubKey(CredentialWrapper credentialWrapper, DidPublicKey didPublicKey) {
        if (credentialWrapper == null) {
            return new ResponseData<Boolean>(false, ErrorCode.ILLEGAL_INPUT);
        }
        if (didPublicKey == null) {
            return new ResponseData<Boolean>(false, ErrorCode.CREDENTIAL_ISSUER_MISMATCH);
        }
        return verifyCredentialContent(credentialWrapper, didPublicKey.getPublicKey());
    }

    /**
     * The only standardized inf to create a full Credential Hash for a given Credential.
     *
     * @param args the args
     * @return the Boolean response data
     */
    @Override
    public ResponseData<String> getCredentialHash(Credential args) {
        ErrorCode innerResponse = CredentialUtils.isCredentialValid(args);
        if (ErrorCode.SUCCESS.getCode() != innerResponse.getCode()) {
            return new ResponseData<>(StringUtils.EMPTY, innerResponse);
        }
        return new ResponseData<>(CredentialUtils.getCredentialHash(args), ErrorCode.SUCCESS);
    }

    /**
     * Get the full hash value of a Credential with its selectively-disclosure map. All fields in
     * the Credential will be included. This method should be called when creating and verifying the
     * Credential Evidence and the result is selectively-disclosure irrelevant.
     *
     * @param credentialWrapper the args
     * @return the Credential Hash value in byte array, fixed to be 32 Bytes length
     */
    @Override
    public ResponseData<String> getCredentialHash(CredentialWrapper credentialWrapper) {
        if (credentialWrapper == null) {
            return new ResponseData<>(StringUtils.EMPTY, ErrorCode.ILLEGAL_INPUT);
        }
        if (credentialWrapper.getDisclosure() == null
            || credentialWrapper.getDisclosure().size() == 0) {
            return getCredentialHash(credentialWrapper.getCredential());
        }
        Credential credential = credentialWrapper.getCredential();
        ErrorCode innerResponse = CredentialUtils.isCredentialValid(credential);
        if (ErrorCode.SUCCESS.getCode() != innerResponse.getCode()) {
            return new ResponseData<>(StringUtils.EMPTY, innerResponse);
        }
        return new ResponseData<>(CredentialUtils.getCredentialWrapperHash(credentialWrapper),
            ErrorCode.SUCCESS);
    }

    private ResponseData<Boolean> verifyCredentialContent(CredentialWrapper credentialWrapper,
        String publicKey) {
        Credential credential = credentialWrapper.getCredential();
        ErrorCode innerResponse = CredentialUtils.isCredentialValid(credential);
        if (ErrorCode.SUCCESS.getCode() != innerResponse.getCode()) {
            logger.error("Credential input format error!");
            return new ResponseData<>(false, innerResponse);
        }
        if (credential.getCptId() == CredentialConstant.CREDENTIALPOJO_EMBEDDED_SIGNATURE_CPT
            .intValue()) {
            return new ResponseData<>(false, ErrorCode.CPT_ID_ILLEGAL);
        }
        if (credential.getCptId() == CredentialConstant.CREDENTIAL_EMBEDDED_SIGNATURE_CPT
            .intValue()) {
            // This is a multi-signed Credential, and its disclosure is against its leaf
            Map<String, Object> disclosure = credentialWrapper.getDisclosure();
            // We firstly verify itself
            credentialWrapper.setDisclosure(null);
            ResponseData<Boolean> innerResp = verifySingleSignedCredential(credentialWrapper,
                publicKey);
            if (!innerResp.getResult()) {
                return new ResponseData<>(false, innerResp.getErrorCode(),
                    innerResp.getErrorMessage());
            }
            // Then, we verify its list members one-by-one
            credentialWrapper.setDisclosure(disclosure);
            List<Credential> innerCredentialList;
            try {
                if (credentialWrapper.getCredential().getClaim()
                    .get("credentialList") instanceof String) {
                    // For selectively-disclosed credential, just skip - external check is enough
                    return new ResponseData<>(true, ErrorCode.SUCCESS);
                } else {
                    innerCredentialList = (ArrayList) credentialWrapper.getCredential().getClaim()
                        .get("credentialList");
                }
            } catch (Exception e) {
                return new ResponseData<>(false, ErrorCode.CREDENTIAL_CLAIM_DATA_ILLEGAL);
            }
            for (Credential innerCredential : innerCredentialList) {
                credentialWrapper.setCredential(innerCredential);
                // Make sure that this disclosure is a meaningful one
                if (disclosure != null && disclosure.size() <= 1
                    && disclosure.size() != innerCredential.getClaim().size()
                    && disclosure.containsKey("credentialList")) {
                    credentialWrapper.setDisclosure(null);
                }
                if (disclosure == null) {
                    credentialWrapper.setDisclosure(null);
                }
                innerResp = verifyCredentialContent(credentialWrapper, publicKey);
                if (!innerResp.getResult()) {
                    return new ResponseData<>(false, innerResp.getErrorCode(),
                        innerResp.getErrorMessage());
                }
            }
            return new ResponseData<>(true, ErrorCode.SUCCESS);
        }
        return verifySingleSignedCredential(credentialWrapper, publicKey);
    }

    private ResponseData<Boolean> verifySingleSignedCredential(CredentialWrapper credentialWrapper,
        String publicKey) {
        Credential credential = credentialWrapper.getCredential();
        ResponseData<Boolean> responseData = verifyIssuerExistence(credential.getIssuer());
        if (!responseData.getResult()) {
            return responseData;
        }

        ErrorCode errorCode = verifyCptFormat(
            credential.getCptId(),
            credential.getClaim()
        );
        if (ErrorCode.SUCCESS.getCode() != errorCode.getCode()) {
            return new ResponseData<>(false, errorCode);
        }

        responseData = verifyNotExpired(credential);
        if (!responseData.getResult()) {
            return responseData;
        }
        responseData = verifySignature(credentialWrapper, publicKey);
        return responseData;
    }

    private ErrorCode checkCreateCredentialArgsValidity(
        CreateCredentialArgs args, boolean privateKeyRequired) {
        ErrorCode innerResponseData = CredentialUtils.isCreateCredentialArgsValid(args);
        if (ErrorCode.SUCCESS.getCode() != innerResponseData.getCode()) {
            logger.error("Create Credential Args illegal: {}", innerResponseData.getCodeDesc());
            return innerResponseData;
        }
        if (privateKeyRequired
            && StringUtils.isEmpty(args.getDidPrivateKey().getPrivateKey())) {
            logger.error(ErrorCode.CREDENTIAL_PRIVATE_KEY_NOT_EXISTS.getCodeDesc());
            return ErrorCode.CREDENTIAL_PRIVATE_KEY_NOT_EXISTS;
        }
        return ErrorCode.SUCCESS;
    }


    private ResponseData<Boolean> verifyIssuerExistence(String issuerDid) {
        ResponseData<Boolean> responseData = didService.isDidExist(issuerDid);
//        if (responseData == null || !responseData.getResult()) {
//            return new ResponseData<>(false, ErrorCode.CREDENTIAL_ISSUER_NOT_EXISTS);
//        }
//        return responseData;
        return responseData;
    }

    private ErrorCode verifyCptFormat(Integer cptId, Map<String, Object> claim) {
        if (cptId == CredentialConstant.CREDENTIAL_EMBEDDED_SIGNATURE_CPT.intValue()) {
            if (!claim.containsKey("credentialList")) {
                return ErrorCode.CREDENTIAL_CLAIM_DATA_ILLEGAL;
            } else {
                return ErrorCode.SUCCESS;
            }
        }
        try {
            //String claimStr = JsonUtil.objToJsonStr(claim);
            String claimStr = DataToolUtils.serialize(claim);
            Cpt cpt = cptService.queryCpt(cptId).getResult();
            if (cpt == null) {
                logger.error(ErrorCode.CREDENTIAL_CPT_NOT_EXISTS.getCodeDesc());
                return ErrorCode.CREDENTIAL_CPT_NOT_EXISTS;
            }
            //String cptJsonSchema = JsonUtil.objToJsonStr(cpt.getCptJsonSchema());
            String cptJsonSchema = DataToolUtils.serialize(cpt.getCptJsonSchema());

            if (!DataToolUtils.isCptJsonSchemaValid(cptJsonSchema)) {
                logger.error(ErrorCode.CPT_JSON_SCHEMA_INVALID.getCodeDesc());
                return ErrorCode.CPT_JSON_SCHEMA_INVALID;
            }
            if (!DataToolUtils.isValidateJsonVersusSchema(claimStr, cptJsonSchema)) {
                logger.error(ErrorCode.CREDENTIAL_CLAIM_DATA_ILLEGAL.getCodeDesc());
                return ErrorCode.CREDENTIAL_CLAIM_DATA_ILLEGAL;
            }
            return ErrorCode.SUCCESS;
        } catch (Exception e) {
            logger.error(
                "Generic error occurred during verify cpt format when verifyCredential: " + e);
            return ErrorCode.CREDENTIAL_ERROR;
        }
    }

    private ResponseData<Boolean> verifyNotExpired(Credential credential) {
        try {
            boolean result = DateUtils.isAfterCurrentTime(credential.getExpirationDate());
            ResponseData<Boolean> responseData = new ResponseData<>(result, ErrorCode.SUCCESS);
            if (!result) {
                responseData.setErrorCode(ErrorCode.CREDENTIAL_EXPIRED);
            }
            return responseData;
        } catch (Exception e) {
            logger.error(
                "Generic error occurred during verify expiration when verifyCredential: " + e);
            return new ResponseData<>(false, ErrorCode.CREDENTIAL_ERROR);
        }
    }

    private ResponseData<Boolean> verifySignature(
        CredentialWrapper credentialWrapper,
        String publicKey) {

        try {
            Credential credential = credentialWrapper.getCredential();
            Map<String, Object> disclosureMap = credentialWrapper.getDisclosure();
            String rawData = CredentialUtils
                .getCredentialThumbprintWithoutSig(credential, disclosureMap);
            Sign.SignatureData signatureData =
                DataToolUtils.simpleSignatureDeserialization(
                    DataToolUtils.base64Decode(
                        credential.getSignature().getBytes(StandardCharsets.UTF_8)
                    )
                );

            if (StringUtils.isEmpty(publicKey)) {
                // Fetch public key from chain
                String credentialIssuer = credential.getIssuer();
                ResponseData<DidDocument> innerResponseData =
                    didService.getDidDocument(credentialIssuer);
                if (innerResponseData.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
                    logger.error(
                        "Error occurred when fetching DID document for: {}, msg: {}",
                        credentialIssuer, innerResponseData.getErrorMessage());
                    return new ResponseData<>(false, ErrorCode.CREDENTIAL_DID_DOCUMENT_ILLEGAL);
                } else {
                    DidDocument didDocument = innerResponseData.getResult();
                    ErrorCode errorCode = DataToolUtils
                        .verifySignatureFromDid(rawData, signatureData, didDocument);
                    if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                        return new ResponseData<>(false, errorCode);
                    }
                    return new ResponseData<>(true, ErrorCode.SUCCESS);
                }
            } else {
                boolean result =
                    DataToolUtils
                        .verifySignature(rawData, signatureData, new BigInteger(publicKey));
                if (!result) {
                    return new ResponseData<>(false, ErrorCode.CREDENTIAL_SIGNATURE_BROKEN);
                }
                return new ResponseData<>(true, ErrorCode.SUCCESS);
            }
        } catch (SignatureException e) {
            logger.error(
                "Generic signatureException occurred during verify signature "
                    + "when verifyCredential: ", e);
            return new ResponseData<>(false, ErrorCode.CREDENTIAL_EXCEPTION_VERIFYSIGNATURE);
        } catch (DidBaseException e) {
            logger.error(
                "Generic signatureException occurred during verify signature ", e);
            return new ResponseData<>(false, ErrorCode.CREDENTIAL_SIGNATURE_BROKEN);
        } catch (Exception e) {
            logger.error(
                "Generic exception occurred during verify signature when verifyCredential: ", e);
            return new ResponseData<>(false, ErrorCode.CREDENTIAL_ERROR);
        }
    }

    /**
     * Generate a credential with selected data.
     *
     * @param credential the credential
     * @param disclosure the keys which select to disclosure
     * @return credential
     */
    @Override
    public ResponseData<CredentialWrapper> createSelectiveCredential(
        Credential credential,
        String disclosure) {

        //setp 1: check if the input args is illegal.
        CredentialWrapper credentialResult = new CredentialWrapper();
        ErrorCode checkResp = CredentialUtils.isCredentialValid(credential);
        if (ErrorCode.SUCCESS.getCode() != checkResp.getCode()) {
            return new ResponseData<>(credentialResult, checkResp);
        }
        if (isMultiSignedCredential(credential)) {
            return new ResponseData<>(credentialResult, ErrorCode.CPT_ID_ILLEGAL);
        }

        //step 2: convet values of claim to hash by disclosure status
        Map<String, Object> claim = credential.getClaim();
        Map<String, Object> hashMap = new HashMap<String, Object>(claim);

        for (Map.Entry<String, Object> entry : claim.entrySet()) {
            claim.put(entry.getKey(), CredentialUtils.getFieldHash(entry.getValue()));
        }
        Map<String, Object> disclosureMap = DataToolUtils.deserialize(disclosure, HashMap.class);

        for (Map.Entry<String, Object> entry : disclosureMap.entrySet()) {
            if (CredentialFieldDisclosureValue.DISCLOSED.getStatus()
                .equals(entry.getValue())) {
                claim.put(entry.getKey(), hashMap.get(entry.getKey()));
            }
        }

        // step 3: build response of selective credential to caller.
        credentialResult.setCredential(credential);
        credentialResult.setDisclosure(disclosureMap);

        return new ResponseData<>(credentialResult, ErrorCode.SUCCESS);
    }

    /**
     * Get the Json String of a Credential. All fields in the Credential will be included. This also
     * supports the selectively disclosed Credential.
     *
     * @param credential the credential wrapper
     * @return the Credential Json value in String
     */
    @Override
    public ResponseData<String> getCredentialJson(Credential credential) {
        ErrorCode errorCode = CredentialUtils.isCredentialValid(credential);
        if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
            return new ResponseData<>(
                StringUtils.EMPTY,
                ErrorCode.getTypeByErrorCode(errorCode.getCode())
            );
        }
        // Convert timestamp into UTC timezone
        try {
            Map<String, Object> credMap = DataToolUtils.objToMap(credential);
            String issuanceDate = DateUtils.convertTimestampToUtc(credential.getIssuanceDate());
            String expirationDate = DateUtils.convertTimestampToUtc(credential.getExpirationDate());
            credMap.put(ParamKeyConstant.ISSUANCE_DATE, issuanceDate);
            credMap.put(ParamKeyConstant.EXPIRATION_DATE, expirationDate);
            credMap.remove(ParamKeyConstant.CONTEXT);
            credMap.put(CredentialConstant.CREDENTIAL_CONTEXT_PORTABLE_JSON_FIELD,
                CredentialConstant.DEFAULT_CREDENTIAL_CONTEXT);
            String credentialString = DataToolUtils.mapToCompactJson(credMap);
            return new ResponseData<>(credentialString, ErrorCode.SUCCESS);
        } catch (Exception e) {
            logger.error("Json conversion failed in getCredentialJson: ", e);
            return new ResponseData<>(StringUtils.EMPTY, ErrorCode.CREDENTIAL_ERROR);
        }
    }
}
