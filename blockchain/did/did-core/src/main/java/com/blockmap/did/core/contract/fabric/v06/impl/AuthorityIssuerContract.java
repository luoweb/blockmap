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

package com.blockmap.did.core.contract.fabric.v06.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockmap.did.core.protocol.base.AuthorityIssuer;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.constant.ContractConstant;
import com.blockmap.did.core.contract.fabric.v06.Contract;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AuthorityIssuerContract extends Contract {

    private static final String FUNC_IS_AUTHORITY_ISSUER = "IsAuthorityIssuer";
    private static final String FUNC_ADD_AUTHORITY_ISSUER = "AddAuthorityIssuer";
    private static final String FUNC_GET_AUTHORITY_ISSUER_ADDRESS_LIST = "GetAuthorityIssuerAddressList";
    private static final String FUNC_REMOVE_ISSUER = "RemoveIssuer";
    private static final String FUNC_IS_SPECIFIC_TYPE_ISSUER = "IsSpecificTypeIssuer";
    private static final String FUNC_REGISTER_ISSUER_TYPE = "RegisterIssuerType";
    private static final String FUNC_GET_SPECIFIC_TYPE_ISSUER_LIST = "GetSpecificTypeIssuerList";
    private static final String FUNC_REMOVE_AUTHORITY_ISSUER = "RegisterIssuerType";
    private static final String FUNC_ADD_ISSUER = "AddIssuer";
    private static final String FUNC_GET_AUTHORITY_ISSUER_INFO_NON_ACC_VALUE = "GetAuthorityIssuerInfoNonAccValue";

    private static final Logger logger = LoggerFactory.getLogger(AuthorityIssuerContract.class);

    public boolean isAuthorityIssuer(String address) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address);
        String[] param = {jsonObject.toString()};
        Map response = httpInvoke(fabricConfig06.getCptAddress(), TPYE_QUERY, FUNC_IS_AUTHORITY_ISSUER, param);
        logger.info("[isAuthorityIssuer] " + StringUtils.join(response));
        return Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
    }

    public Map addAuthorityIssuer(AuthorityIssuer authorityIssuer, DidPrivateKey didPrivateKey) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authorityIssuer", JSON.toJSON(authorityIssuer));
        jsonObject.put("didPrivateKey", JSON.toJSON(didPrivateKey));
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_ADD_AUTHORITY_ISSUER, param);
    }

    public Map getAuthorityIssuerAddressList(int index, int num) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("index", index);
        jsonObject.put("num", num);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_QUERY, FUNC_GET_AUTHORITY_ISSUER_ADDRESS_LIST, param);
    }

    public Map removeIssuer(String issuerType, String issuerAddress){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("issuerType", issuerType);
        jsonObject.put("issuerAddress", issuerAddress);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_REMOVE_ISSUER, param);
    }

    public boolean isSpecificTypeIssuer(String issuerType, String address){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("issuerType", issuerType);
        jsonObject.put("issuerAddress", address);
        String[] param = {jsonObject.toString()};
        Map response = httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_IS_SPECIFIC_TYPE_ISSUER, param);
        logger.info("[isSpecificTypeIssuer] " + StringUtils.join(response));
        return  Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
    }

    public Map registerIssuerType(String issuerType){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("issuerType", issuerType);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_REGISTER_ISSUER_TYPE, param);
    }

    public Map getSpecificTypeIssuerList(String issuerType, int index, int num){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("issuerType", issuerType);
        jsonObject.put("index", index);
        jsonObject.put("num", num);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_QUERY, FUNC_GET_SPECIFIC_TYPE_ISSUER_LIST, param);
    }

    public Map removeAuthorityIssuer(String address){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_REMOVE_AUTHORITY_ISSUER, param);
    }

    public Map addIssuer(String issuerType, String issuerAddress){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("issuerType", issuerType);
        jsonObject.put("issuerAddress", issuerAddress);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_ADD_ISSUER, param);
    }


    public Map getAuthorityIssuerInfoNonAccValue(String address){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_GET_AUTHORITY_ISSUER_INFO_NON_ACC_VALUE, param);
    }

}
