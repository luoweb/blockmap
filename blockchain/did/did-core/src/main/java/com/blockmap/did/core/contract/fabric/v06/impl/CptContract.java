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
import com.blockmap.did.core.contract.fabric.v06.Contract;
import com.blockmap.did.core.protocol.base.Cpt;
import com.blockmap.did.core.protocol.base.CptBaseInfo;

import java.util.Map;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description:
 * @File: CptContract
 * @Version: 1.0.0
 * @Date: 2020/1/13 11:10
 */

public class CptContract extends Contract {

    private static final String FUNC_QUERY_CPT = "QueryCpt";
    private static final String FUNC_REGISTER_CPT = "RegisterCpt";
    private static final String FUNC_UPDATE_CPT = "UpdateCpt";


    public Map regidterCpt(CptBaseInfo cptBaseInfo, Cpt.MetaData cptMetaData, String cptJsonSchema) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cptBaseInfo", JSON.toJSON(cptBaseInfo));
        jsonObject.put("cptMetaData", JSON.toJSON(cptMetaData));
        jsonObject.put("cptJsonSchema", cptJsonSchema);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_REGISTER_CPT, param);
    }


    public Map updateCpt(CptBaseInfo cptBaseInfo, Cpt.MetaData cptMetaData, String cptJsonSchema) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cptBaseInfo", JSON.toJSON(cptBaseInfo));
        jsonObject.put("cptMetaData", JSON.toJSON(cptMetaData));
        jsonObject.put("cptJsonSchema", cptJsonSchema);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_INVOKE, FUNC_UPDATE_CPT, param);
    }

    public Map queryCpt(int cptId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cptId", cptId);
        String[] param = {jsonObject.toString()};
        return httpInvoke(fabricConfig06.getCptAddress(), TPYE_QUERY, FUNC_QUERY_CPT, param);
    }

}
