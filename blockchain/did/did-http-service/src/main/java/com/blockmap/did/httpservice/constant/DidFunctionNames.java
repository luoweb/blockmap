/*
 *       Copyright© (2020) ICBC Co., Ltd.
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


package com.icbc.did.httpservice.constant;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: Define function names to be picked for calls to  Java SDK
 * @File: InputArg
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */
public final class DidFunctionNames {

    /**
     * Function names to be compared against the passed in encode/send transaction parameters. Case
     * insensitive.
     */
    public static final String FUNCNAME_CREATE_DID = "createDid";
    public static final String FUNCNAME_REGISTER_AUTHORITY_ISSUER = "registerAuthorityIssuer";
    public static final String FUNCNAME_REGISTER_CPT = "registerCpt";
    public static final String FUNCNAME_CREATE_CREDENTIAL = "createCredential";
    public static final String FUNCNAME_VERIFY_CREDENTIAL = "verifyCredential";
    public static final String FUNCNAME_GET_DID_DOCUMENT = "getDidDocument";
    public static final String FUNCNAME_GET_DID_DOCUMENT_JSON = "getDidDocumentJson";
    public static final String FUNCNAME_QUERY_AUTHORITY_ISSUER = "queryAuthorityIssuer";
    public static final String FUNCNAME_QUERY_CPT = "queryCpt";

    public static final String FUNCNAME_CREATE_QRCODE = "createQrCode";
    public static final String FUNCNAME_ACTIVE_QRCODE = "activeQrCode";
    public static final String FUNCNAME_GET_CREDENTIAL_DETAIL = "getCredentialDetail";
    public static final String FUNCNAME_GET_ALL_CREDENTIAL = "getAllCredential";
    public static final String FUNCNAME_GET_CREDENTIAL_BY_QRCODE_ID = "getCredentialByQrCodeId";
    public static final String FUNCNAME_LOGIN = "login";

    /**
     * Function names to be assembled in SDK Function call. Case sensitive.
     */
    public static final String FUNCCALL_SET_ATTRIBUTE = "setAttribute";
    public static final String FUNCCALL_ADD_AUTHORITY_ISSUER = "addAuthorityIssuer";
    public static final String FUNCCALL_REGISTER_CPT = "registerCpt";
}
