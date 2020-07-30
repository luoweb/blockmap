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

public final class DidServiceEndpoint {

    /**
     * API endpoint.
     */
    public static final String API_ROOT = "did/api";
    public static final String ENCODE_TRANSACTION = "encode";
    public static final String SEND_TRANSACTION = "transact";
    public static final String INVOKE_FUNCTION = "invoke";
    public static final String VERIFY_FUNCTION = "verify";

    /**
     * EP Service endpoint.
     */
    public static final String EPS_ROOT = "endpoint";
    public static final String ADD_FUNCTION = "add";
    public static final String REMOVE_FUNCTION = "remove";
    public static final String FETCH_FUNCTION = "auto-fetch";

    /**
     * Separator.
     */
    public static final String EPS_SEPARATOR = "|||";

    /**
     * Misc items
     */
    public static final String ALL_INFO = "ALL";
}
