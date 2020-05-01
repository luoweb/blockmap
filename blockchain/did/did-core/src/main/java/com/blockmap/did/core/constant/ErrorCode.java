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


package com.blockmap.did.core.constant;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: Define Error Code and the corresponding Error Message.
 * @File: ErrorCode
 * @Version: 1.0.0
 * @Date: 2019/12/15 20:03
 */

public enum ErrorCode {

    /**
     * The success.
     */
    SUCCESS(0, "success"),

    /**
     * No Permission to perform com.blockmap.did.core.contract level tasks.
     */
    CONTRACT_ERROR_NO_PERMISSION(
            500000,
            "com.blockmap.did.core.contract error: no permission to perform this task"
    ),

    /**
     * The cpt not exists.
     */
    CPT_NOT_EXISTS(500301, "cpt does not exist"),

    /**
     * cpt id generated for authority issuer exceeds limited max value.
     */
    CPT_ID_AUTHORITY_ISSUER_EXCEED_MAX(
            500302,
            "cpt id generated for authority issuer exceeds limited max value"
    ),

    /**
     * cpt publisher does not exist.
     */
    CPT_PUBLISHER_NOT_EXIST(500303, "cpt publisher does not exist"),

    /**
     * This cpt id already exists on chain.
     */
    CPT_ALREADY_EXIST(500304, "cpt already exist on chain"),

    /**
     * No permission to perform this CPT task.
     */
    CPT_NO_PERMISSION(500305, "no permission to perform this cpt task"),

    /**
     * The cpt json schema invalid.
     */
    CPT_JSON_SCHEMA_INVALID(100301, "cpt json schema is invalid"),

    /**
     * cptId illegal.
     */
    CPT_ID_ILLEGAL(100303, "cptId illegal"),

    /**
     * cpt event log is null.
     */
    CPT_EVENT_LOG_NULL(100304, "cpt event log is null."),

    /**
     * Credential main error code.
     */
    CREDENTIAL_ERROR(100400, "error occured during processing credential tasks"),

    /**
     * The credential expired.
     */
    CREDENTIAL_EXPIRED(100402, "credential is expired"),

    /**
     * The credential issuer mismatch.
     */
    CREDENTIAL_ISSUER_MISMATCH(100403, "issuer did does not match the did of credential"),

    /**
     * The credential signature broken.
     */
    CREDENTIAL_SIGNATURE_BROKEN(100405, "credential signature cannot be extracted"),

    /**
     * The credential issuer not exists.
     */
    CREDENTIAL_ISSUER_NOT_EXISTS(100407, "credential issuer does not exist"),

    /**
     * The credential issuance date illegal.
     */
    CREDENTIAL_ISSUANCE_DATE_ILLEGAL(100408, "credential issuance date illegal"),

    /**
     * The credential expire date illegal.
     */
    CREDENTIAL_EXPIRE_DATE_ILLEGAL(100409, "expire date illegal"),

    /**
     * The credential claim not exists.
     */
    CREDENTIAL_CLAIM_NOT_EXISTS(100410, "claim data does not exist"),

    /**
     * The credential claim data illegal.
     */
    CREDENTIAL_CLAIM_DATA_ILLEGAL(100411, "claim data illegal"),

    /**
     * The credential id not exists.
     */
    CREDENTIAL_ID_NOT_EXISTS(100412, "credential id does not exist"),

    /**
     * The credential context not exists.
     */
    CREDENTIAL_CONTEXT_NOT_EXISTS(100413, "credential context does not exist"),

    /**
     * The credential type is null.
     */
    CREDENTIAL_TYPE_IS_NULL(100414, "credential type is null"),

    /**
     * The credential private key not exists.
     */
    CREDENTIAL_PRIVATE_KEY_NOT_EXISTS(100415, "private key for signing credential does not exist"),

    /**
     * The credential CPT info is empty.
     */
    CREDENTIAL_CPT_NOT_EXISTS(100416, "cpt does not exist"),

    /**
     * The credential issuer does not have a valid  DID document.
     */
    CREDENTIAL_DID_DOCUMENT_ILLEGAL(100417, "did document illegal"),

    /**
     * The credential issuer is invalid.
     */
    CREDENTIAL_ISSUER_INVALID(100418, "credential issuer invalid"),

    /**
     * The credential credential verify signature is com.blockmap.did.core.exception.
     */
    CREDENTIAL_EXCEPTION_VERIFYSIGNATURE(100419, "credential verify signature com.blockmap.did.core.exception"),

    /**
     * claim policy is null.
     */
    CREDENTIAL_CLAIM_POLICY_NOT_EXIST(100420, "claim policy is null"),

    /**
     * The credential private key not exists.
     */
    CREDENTIAL_PUBLIC_KEY_NOT_EXISTS(
            100421,
            "public key for verifying credential signature does not exist"
    ),

    /**
     * The signature for verifying credential does not exist.
     */
    CREDENTIAL_SIGNATURE_NOT_EXISTS(100422, "signature for verifying credential does not exist"),

    /**
     * The credential policy disclosurevalue illegal.
     */
    CREDENTIAL_POLICY_DISCLOSUREVALUE_ILLEGAL(100423, "policy disclosurevalue illegal"),

    /**
     * The credential disclosurevalue notmatch saltvalue.
     */
    CREDENTIAL_DISCLOSUREVALUE_NOTMATCH_SALTVALUE(100424, "disclosurevalue notmatch saltvalue"),

    /**
     * The credential cptId notmatch.
     */
    CREDENTIAL_CPTID_NOTMATCH(100425, "credential cptId notmatch"),

    /**
     * The credential presenterDid notmatch.
     */
    CREDENTIAL_PRESENTERDID_NOTMATCH(100426, "credential presenter Did not match"),

    /**
     * The credential evidence id mismatch.
     */
    CREDENTIAL_POLICY_FORMAT_DOSE_NOT_MATCH_CLAIM(
            100427,
            "credential disclosure format does not match claim"
    ),

    /**
     * credential disclosure data illegal.
     */
    CREDENTIAL_DISCLOSURE_DATA_TYPE_ILLEGAL(100428, "credential disclosure data illegal"),

    /**
     * The credential signature broken.
     */
    CREDENTIAL_SIGNATURE_TYPE_ILLEGAL(100429, "credential signature type unknown"),

    /**
     * credential salt illegal.
     */
    CREDENTIAL_SALT_ILLEGAL(100430, "credential salt illegal"),

    /**
     * credential evidence cannot be extracted.
     */
    CREDENTIAL_EVIDENCE_SIGNATURE_BROKEN(100431, "credential evidence cannot be extracted"),

    /**
     * credential has already been disclosed once.
     */
    CREDENTIAL_RE_DISCLOSED(100432,
            "credential has already been disclosed once, can not be re-disclosed"),
    /**
     * Timestamp service error.
     */
    TIMESTAMP_SERVICE_BASE_ERROR(100433, "timestamp service error"),

    /**
     * System CPT Claim verification failure.
     */
    CREDENTIAL_SYSTEM_CPT_CLAIM_VERIFY_ERROR(100434, "external credential verify"
            + " succeeded, but inner content verify failed"),


    /**
     * Timestamp service unavailable.
     */
    TIMESTAMP_SERVICE_UNCONFIGURED(100435, "timestamp service not configured"),

    /**
     * Timestamp service: WeSign workflow error.
     */
    TIMESTAMP_SERVICE_WESIGN_ERROR(100436, "wesign timestamp service error: check log for details"),

    /**
     * Timestamp verification failed.
     */
    TIMESTAMP_VERIFICATION_FAILED(100437, "timestamp verification failed"),

    /**
     * Timestamp service does not support selectively-disclosed credential's presence.
     */
    TIMESTAMP_CREATION_FAILED_FOR_SELECTIVELY_DISCLOSED(100438,
            "timestamp creation does not support selectively disclosed credential"),
    /**
     * The credential evidence com.blockmap.did.core.contract failure: illegal input.
     */
    CREDENTIAL_EVIDENCE_CONTRACT_FAILURE_ILLEAGAL_INPUT(
            500401,
            "credential evidence com.blockmap.did.core.contract failure: illegal input."
    ),

    /**
     * The credential evidence base error.
     */
    CREDENTIAL_EVIDENCE_BASE_ERROR(
            100500,
            "generic error when processing credential evidence tasks"
    ),

    /**
     * The credential evidence hash mismatch.
     */
    CREDENTIAL_EVIDENCE_HASH_MISMATCH(100501, "credential evidence hash mismatch"),

    /**
     * The challenge is invalid.
     */
    PRESENTATION_CHALLENGE_INVALID(100600, "the challenge is invalid."),

    /**
     * The did of challenge does not match the user's did.
     */
    PRESENTATION_CHALLENGE_DID_MISMATCH(
            100601,
            "the did of challenge does not match the user's did."
    ),

    /**
     * The presentation policy is invalid.
     */
    PRESENTATION_POLICY_INVALID(100602, "the presentation policy is invalid."),

    /**
     * the credentialList of presentation don't match the claim policy.
     */
    PRESENTATION_CREDENTIALLIST_MISMATCH_CLAIM_POLICY(
            100603,
            "the credentiallist of presentation don't match the claim policy."
    ),

    /**
     * the publicKeyId is invalid.
     */
    PRESENTATION_DID_PUBLICKEY_ID_INVALID(100604, "the publicKeyId is invalid."),

    /**
     * the nonce of challenge does not match the nonce of presentation.
     */
    PRESENTATION_CHALLENGE_NONCE_MISMATCH(
            100605,
            "the nonce of challenge does not match the nonce of presentation."
    ),

    /**
     * the signature of presentation does not match the presenter.
     */
    PRESENTATION_SIGNATURE_MISMATCH(
            100606,
            "the signature of presentation does not match the presenter."
    ),

    /**
     * the presenter did of presentation does not match the credential.
     */
    PRESENTATION_DID_CREDENTIAL_DID_MISMATCH(
            100607,
            "the presenter did of presentation does not match the credential."
    ),

    /**
     * the did of the claim of the presentation does not exist.
     */
    PRESENTATION_CREDENTIAL_CLAIM_DID_NOT_EXIST(
            100608,
            "the did of the claim of the presentation does not exist."
    ),

    /**
     * the publisherDid of policy is invalid.
     */
    PRESENTATION_POLICY_PUBLISHER_DID_INVALID(
            100609,
            "the publisherDid of policy is invalid."
    ),

    /**
     * the publisherDid of policy does not exist.
     */
    PRESENTATION_POLICY_PUBLISHER_DID_NOT_EXIST(
            100610,
            "the publisherDid of policy does not exist."
    ),

    /**
     * the encrypt key is not exists.
     */
    ENCRYPT_KEY_NOT_EXISTS(100700, "the encrypt key not exists."),

    /**
     * the policy com.blockmap.did.core.service is not exists.
     */
    POLICY_SERVICE_NOT_EXISTS(100701, "no policy com.blockmap.did.core.service."),

    /**
     * the policy com.blockmap.did.core.service call fail.
     */
    POLICY_SERVICE_CALL_FAIL(100702, "the policy com.blockmap.did.core.service call fail, please check the error log."),

    /**
     * the policy com.blockmap.did.core.service call fail.
     */
    ENCRYPT_KEY_NO_PERMISSION(100703, "no permission to get the key."),

    /**
     * the key is invalid.
     */
    ENCRYPT_KEY_INVALID(100704, "the key is invalid."),

    /**
     * transportation base error.
     */
    TRANSPORTATION_BASE_ERROR(100800, "com.blockmap.did.core.suite baes com.blockmap.did.core.exception error, please check the error log."),

    /**
     * transportation com.blockmap.did.core.protocol error.
     */
    TRANSPORTATION_PROTOCOL_PROPERTY_ERROR(100801, "the com.blockmap.did.core.protocol property is error."),

    /**
     * transportation com.blockmap.did.core.protocol version error.
     */
    TRANSPORTATION_PROTOCOL_VERSION_ERROR(100802, "the com.blockmap.did.core.protocol version is error."),

    /**
     * transportation com.blockmap.did.core.protocol encode error.
     */
    TRANSPORTATION_PROTOCOL_ENCODE_ERROR(100803, "the com.blockmap.did.core.protocol encode is error."),

    /**
     * transportation com.blockmap.did.core.protocol value error.
     */
    TRANSPORTATION_PROTOCOL_STRING_INVALID(100804, "the com.blockmap.did.core.protocol string is invalid."),

    /**
     * transportation com.blockmap.did.core.protocol data error.
     */
    TRANSPORTATION_PROTOCOL_DATA_INVALID(100805, "the com.blockmap.did.core.protocol data is invalid."),

    /**
     * transportation com.blockmap.did.core.protocol field invalid.
     */
    TRANSPORTATION_PROTOCOL_FIELD_INVALID(
            100806,
            "the com.blockmap.did.core.protocol field values cannot be included '|'."
    ),

    /**
     * transportation com.blockmap.did.core.protocol encode error.
     */
    TRANSPORTATION_ENCODE_BASE_ERROR(100807, "encode base error, please check the error log."),

    /**
     * pdf transfer error.
     */
    TRANSPORTATION_PDF_TRANSFER_ERROR(100808, "pdf transfer error, please check the error log."),

    /**
     * Authority issuer main error code.
     */
    AUTHORITY_ISSUER_ERROR(100200, "error occured during processing authority issuer tasks"),

    /**
     * The authority issuer private key param is illegal.
     */
    AUTHORITY_ISSUER_PRIVATE_KEY_ILLEGAL(100202, "the private key is illegal"),

    /**
     * The authority issuer opcode mismatch.
     */
    AUTHORITY_ISSUER_OPCODE_MISMATCH(
            100205,
            "opcode in event log does not match the desired opcode"
    ),

    /**
     * The authority issuer name illegal.
     */
    AUTHORITY_ISSUER_NAME_ILLEGAL(100206, "the registered authority issuer name is illegal"),

    /**
     * The authority issuer accvalue illegal.
     */
    AUTHORITY_ISSUER_ACCVALUE_ILLEAGAL(
            100207,
            "the authority issuer accumulator value is illegal (integer value required)"
    ),

    /**
     * The specific issuer type illegal.
     */
    SPECIFIC_ISSUER_TYPE_ILLEGAL(
            100208,
            "the specific issuer type is illegal"
    ),

    /**
     * the key of the data is empty.
     */
    PRESISTENCE_DATA_KEY_INVALID(
            100901,
            "the key of the data is empty."
    ),

    /**
     * the domain is illegal.
     */
    PRESISTENCE_DOMAIN_ILLEGAL(
            100902,
            "the domain is illegal."
    ),

    /**
     * the domain is illegal.
     */
    PRESISTENCE_DOMAIN_INVALID(
            100903,
            "the domain is invalid."
    ),

    /**
     * the data does not match for batch save.
     */
    PRESISTENCE_BATCH_SAVE_DATA_MISMATCH(
            100904,
            "the data does not match for batch save."
    ),

    /**
     * The Authority Issuer Contract level error: subject already exists.
     */
    AUTHORITY_ISSUER_CONTRACT_ERROR_ALREADY_EXIST(
            500201,
            "the authority issuer com.blockmap.did.core.contract error: the subject already exists"
    ),

    /**
     * The Authority Issuer Contract level error: subject already exists.
     */
    AUTHORITY_ISSUER_CONTRACT_ERROR_NOT_EXISTS(
            500202,
            "the authority issuer com.blockmap.did.core.contract error: the subject does not exist"
    ),

    /**
     * The Authority Issuer Contract level error: name already exists.
     */
    AUTHORITY_ISSUER_CONTRACT_ERROR_NAME_ALREADY_EXISTS(
            500203,
            "the authority issuer name already exists."
    ),

    /**
     * The Specific Issuer Contract level error: already exists.
     */
    SPECIFIC_ISSUER_CONTRACT_ERROR_ALREADY_EXISTS(
            500501,
            "the specific issuer type or address already exists."
    ),

    /**
     * The Specific Issuer Contract level error: already exists.
     */
    SPECIFIC_ISSUER_CONTRACT_ERROR_ALREADY_NOT_EXIST(
            500502,
            "the specific issuer type or address does not exist."
    ),

    /**
     * The did invalid.
     */
    DID_INVALID(100101, "the  DID is invalid."),

    /**
     * public key is invalid.
     */
    DID_PUBLICKEY_INVALID(100102, "the input public key is invalid."),

    /**
     * private key is invalid.
     */
    DID_PRIVATEKEY_INVALID(
            100103,
            "the input private key is invalid, please check and input your private key."
    ),

    /**
     * did does not exist.
     */
    DID_DOES_NOT_EXIST(100104, "the did does not exist on blockchain."),

    /**
     * did has already exist.
     */
    DID_ALREADY_EXIST(100105, "the did has already exist on blockchain."),

    /**
     * the private key is not the did's.
     */
    DID_PRIVATEKEY_DOES_NOT_MATCH(100106, "the private key does not match the current did."),

    /**
     * create keypair com.blockmap.did.core.exception.
     */
    DID_KEYPAIR_CREATE_FAILED(100107, "create keypair faild."),

    /**
     * public key and private key are not a keypair.
     */
    DID_PUBLICKEY_AND_PRIVATEKEY_NOT_MATCHED(
            100108,
            "the public key and private key are not matched."
    ),

    /**
     * the authority of the  DID is invalid.
     */
    DID_AUTHORITY_INVALID(100109, "the authority of the  DID is invalid."),

    /**
     * the length of the setService type is overlimit.
     */
    DID_SERVICE_TYPE_OVERLIMIT(
            100110, "the length of com.blockmap.did.core.service type is overlimit."
    ),

    /**
     * transaction timeout.
     */
    TRANSACTION_TIMEOUT(160001, "the transaction is timeout."),

    /**
     * com.blockmap.did.core.exception happens when transaction executes.
     */
    TRANSACTION_EXECUTE_ERROR(160002, "the transaction does not correctly executed."),

    /**
     * input parameter is illegal.
     */
    ILLEGAL_INPUT(160004, "input parameter is illegal."),

    /**
     * smart com.blockmap.did.core.contract load failed.
     */
    LOAD_CONTRACT_FAILED(160005, "load com.blockmap.did.core.contract failed."),

    /**
     * com.blockmap.did.core.web3j load failed.
     */
    LOAD_WEB3J_FAILED(160006, "load com.blockmap.did.core.web3j failed."),

    /**
     *  base exceptions or error.
     */
    BASE_ERROR(160007, "baes com.blockmap.did.core.exception error, please check the error log."),

    /**
     * did data type case exceptions or error.
     */
    DATA_TYPE_CASE_ERROR(160008, "data type cast com.blockmap.did.core.exception error, please check the error log."),

    DIRECT_ROUTE_REQUEST_TIMEOUT(160009, "amop timeout"),
    DIRECT_ROUTE_MSG_BASE_ERROR(160010, "amop response messageBody error."),

    /**
     * sql execute failed.
     */
    SQL_EXECUTE_FAILED(160011, "sql execute failed."),

    /**
     * AMOP server side has no direct route callback.
     */
    AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE(
            160012,
            "amop server side has no direct route callback."
    ),

    /**
     * can not get the connection from pool.
     */
    SQL_GET_CONNECTION_ERROR(
            160013,
            "can not get the connection from pool, please check the error log."
    ),

    /**
     * the orgid is null.
     */
    ORG_ID_IS_NULL(160014, "the orgid is null."),

    /**
     * the data is expire.
     */
    SQL_DATA_EXPIRE(160015, "the data is expire."),


    /**
     * there are some error happened in blockchain-components
     */
    BLOCKCHAIN_COMPONENTS_ERROR(200001, "there are some error happened in blockchain-components."),

    /**
     * the json is error in invoke blockchain contract
     */
    BLOCKCHIN_JSON_ERROR(93000001, "the json is error in invoke blockchain contract."),

    /**
     * the json is error in invoke blockchain contract
     */
    BLOCKCHIN_DID_NOT_EXIST(93000003, "the did is not exsit  blockchain."),

    /**
     * the cpt is not exsit  blockchain
     */
    BLOCKCHIN_CPT_NOT_EXIST(93000004, "the cpt is not exsit  blockchain."),
    /**
     * the cpt is not exsit  blockchain
     */
    BLOCKCHIN_CPT_PUBLISHER_NOT_EXIST(93000005, "the cpt publisher is not exsit  blockchain."),
    /**
     * user not login
     */
    USER_NOT_LOGIN(-32000, "user not login."),
    /**
     * other uncatched exceptions or error.
     */
    UNKNOW_ERROR(160003, "unknow error, please check the error log.");

    /**
     * error code.
     */
    private int code;

    /**
     * error message.
     */
    private String codeDesc;

    /**
     * Error Code Constructor.
     *
     * @param code The ErrorCode
     * @param codeDesc The ErrorCode Description
     */
    ErrorCode(int code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    /**
     * get ErrorType By errcode.
     *
     * @param errorCode the ErrorCode
     * @return errorCode
     */
    public static ErrorCode getTypeByErrorCode(int errorCode) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getCode() == errorCode) {
                return type;
            }
        }
        return ErrorCode.UNKNOW_ERROR;
    }

    /**
     * Get the Error Code.
     *
     * @return the ErrorCode
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the Error Code.
     *
     * @param code the new ErrorCode
     */
    protected void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the ErrorCode Description.
     *
     * @return the ErrorCode Description
     */
    public String getCodeDesc() {
        return codeDesc;
    }

    /**
     * Sets the ErrorCode Description.
     *
     * @param codeDesc the new ErrorCode Description
     */
    protected void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
}

