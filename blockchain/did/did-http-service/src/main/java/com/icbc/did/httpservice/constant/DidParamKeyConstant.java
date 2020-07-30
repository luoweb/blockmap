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
 * @Description: Define param key names to be allowed to enable calls to  Java SDK
 * @File: InputArg
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

public final class DidParamKeyConstant {

    /**
     * Universal param key names. Treated as case-INSENSITIVE when being checked.
     */
    public static final String FUNCTION_NAME = "functionName";
    public static final String TRANSACTION_DATA = "data";
    public static final String SIGNED_MESSAGE = "signedMessage";
    public static final String API_VERSION = "v";
    public static final String NONCE = "nonce";
    public static final String FUNCTION_ARG = "functionArg";
    public static final String TRANSACTION_ARG = "transactionArg";
    public static final String CLAIM_HASH = "claimHash";
    public static final String KEY_INDEX = "invokerDid";
    public static final String BODY = "body";

    public static final String DEFAULT_API_VERSION = "1.0.0";
    public static final String DEFAULT_PRIVATE_KEY_FILE_NAME = "ecdsa_key";
}
