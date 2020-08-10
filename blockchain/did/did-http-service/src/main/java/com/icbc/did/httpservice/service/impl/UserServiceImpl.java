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

package com.icbc.did.httpservice.service.impl;


import com.alibaba.fastjson.JSON;
import com.icbc.did.httpservice.constant.HttpReturnCode;
import com.icbc.did.httpservice.protocol.base.User;
import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import com.icbc.did.httpservice.service.InvokerDidService;
import com.icbc.did.httpservice.service.UserService;
import com.icbc.did.httpservice.util.JsonUtil;
import com.icbc.did.core.constant.ErrorCode;
import com.icbc.did.core.protocol.response.ResponseData;
import com.icbc.did.core.suite.api.persistence.Persistence;
import com.icbc.did.core.suite.persistence.sql.driver.MysqlDriver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by xuzhijun.online on 2019/11/13.
 */
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private InvokerDidService invokerDidService = new InvokerDidServiceImpl();

    private Persistence dataDriver;

    private Persistence getDataDriver() {
        if (dataDriver == null) {
            dataDriver = new MysqlDriver();
        }
        return dataDriver;
    }

    @Override
    public HttpResponseData<Object> login(InputArg loginFuncArgs) {
        User user = null;
        try {
            user = (User) JsonUtil
                    .jsonStrToObj(new User(), loginFuncArgs.getFunctionArg());
        } catch (Exception e) {
            // The input credential is a json, so keep moving down.
            logger.info("Detected Portable-Json format user, continuing..");
        }

        if (user == null) {
            try {
                Map<String, Object> credMap = (Map<String, Object>) JsonUtil
                        .jsonStrToObj(new HashMap<String, Object>(),
                                loginFuncArgs.getFunctionArg());
                credMap = JsonUtil.reformatCredentialPojoToJson(credMap);
                user = (User) JsonUtil
                        .jsonStrToObj(new User(), JsonUtil.mapToCompactJson(credMap));
            } catch (Exception e) {
                logger.error("Input user format illegal: {}", loginFuncArgs);
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_ILLEGAL.getCode(),
                        HttpReturnCode.INPUT_ILLEGAL.getCodeDesc().concat(e.getMessage()));
            }
        }
        ResponseData responseData = this.getDataDriver().get("domain.user", user.getId());
        //是否查询成功
        if (responseData.getErrorCode() == ErrorCode.SUCCESS.getCode()) {
            //是否有数据
            if (StringUtils.isNotBlank(responseData.getResult().toString())) {
                return new HttpResponseData<>(JSON.parseObject(responseData.getResult().toString()), responseData.getErrorCode(), responseData.getErrorMessage());
            } else {
                //创建Did
                HttpResponseData createDid = invokerDidService.createDidInvoke(loginFuncArgs);
                if (createDid.getErrorCode() == ErrorCode.SUCCESS.getCode()) {
                    String did = createDid.getRespBody().toString();
                    user.setDid(did);
                    //保存用户
                    ResponseData saveUser = this.getDataDriver().save("domain.user", user.getId(), JsonUtil.objToJsonStr(user), did);
                    if (saveUser.getErrorCode() == ErrorCode.SUCCESS.getCode()) {
                        return new HttpResponseData(user, saveUser.getErrorCode(), saveUser.getErrorMessage());
                    } else {
                        return new HttpResponseData<>(null, saveUser.getErrorCode(), saveUser.getErrorMessage());
                    }
                } else {
                    return new HttpResponseData<>(null, createDid.getErrorCode(), createDid.getErrorMessage());
                }
            }
        }
        return new HttpResponseData<>(null, responseData.getErrorCode(), responseData.getErrorMessage());
    }
}
