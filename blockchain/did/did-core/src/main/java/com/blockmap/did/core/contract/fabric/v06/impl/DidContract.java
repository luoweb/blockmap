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
import com.blockmap.did.core.protocol.base.AuthenticationProperty;
import com.blockmap.did.core.protocol.base.PublicKeyProperty;
import com.blockmap.did.core.protocol.base.ServiceProperty;
import com.blockmap.did.core.util.DateUtils;
import com.blockmap.did.core.util.DidUtils;
import com.blockmap.did.core.contract.fabric.v06.Contract;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description:
 * @File: DidContract
 * @Version: 1.0.0
 * @Date: 2019/12/18 16:00
 */

public class DidContract extends Contract {

    private static final Logger logger = LoggerFactory.getLogger(DidContract.class);

    private static final String FUNC_QUERY_DID = "QueryDid";
    private static final String FUNC_CREATE_DID = "CreateDid";
    private static final String FUNC_SET_ATTRIBUTE = "SetAttribute";


//    public boolean isIdentityExist(String did) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("did", DidUtils.convertDidToAddress(did));
//        String[] param = {jsonObject.toString()};
//        Map response = httpInvoke(DID_CONTRACT_ADDRESS, TPYE_QUERY, FUNC_QUERY_DID, param);
//        logger.info("[isIdentityExist] " + StringUtils.join(response));
//        boolean isSuccess = Boolean.valueOf(response.get(ContractConstant.OUTPUT_BLOCKCHAIN_SUCCESS).toString());
//        return isSuccess;
//    }
    public Map isIdentityExist(String did) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("did", DidUtils.convertDidToAddress(did));
        String[] param = {jsonObject.toString()};
        Map response = httpInvoke(DID_CONTRACT_ADDRESS, TPYE_QUERY, FUNC_QUERY_DID, param);
        return response;
    }


    public Map createDid(String didAddress, List<PublicKeyProperty> pps, List<AuthenticationProperty> aps, List<ServiceProperty> sps) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", didAddress);
        jsonObject.put("created", DateUtils.getCurrentTimeStampString());
        jsonObject.put("updated", DateUtils.getCurrentTimeStampString());
        jsonObject.put("publicKeys", JSON.toJSON(pps));
        jsonObject.put("authentications", JSON.toJSON(aps));
        jsonObject.put("services", JSON.toJSON(sps));
        String[] param = {jsonObject.toString()};
        return httpInvoke(DID_CONTRACT_ADDRESS, TPYE_INVOKE, FUNC_CREATE_DID, param);
    }

    public Map setAttribute(String didAddress, String attributeKey, String value) {

        String updated = DateUtils.getNoMillisecondTimeStampString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("did", didAddress);
        jsonObject.put("key", attributeKey);
        jsonObject.put("value", value);
        jsonObject.put("updated", updated);
        String[] param = {jsonObject.toString()};
        return httpInvoke(DID_CONTRACT_ADDRESS, TPYE_INVOKE, FUNC_SET_ATTRIBUTE, param);
    }

    public Map getDidDocument(String didAddress) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("did", didAddress);
        String[] param = {jsonObject.toString()};
        return httpInvoke(DID_CONTRACT_ADDRESS, TPYE_QUERY, FUNC_QUERY_DID, param);
    }

}
