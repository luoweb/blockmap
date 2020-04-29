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

package com.blockmap.did.core.suite.transportation;

import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.exception.DidBaseException;
import com.blockmap.did.core.protocol.base.DidAuthentication;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.DidService;
import com.blockmap.did.core.service.impl.DidServiceImpl;
import com.blockmap.did.core.util.DidUtils;
import com.blockmap.did.core.suite.api.transportation.params.ProtocolProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;


public abstract class AbstractTransportation {

    private static final Logger logger =
            LoggerFactory.getLogger(AbstractTransportation.class);
    private static DidService didService = new DidServiceImpl();
    private List<String> verifierDidList;
    private DidAuthentication weIdAuthentication;

    /**
     * 验证协议配置.
     *
     * @param encodeProperty 协议配置实体
     * @return Error Code and Message
     */
    protected ErrorCode checkEncodeProperty(ProtocolProperty encodeProperty) {
        if (encodeProperty == null) {
            return ErrorCode.TRANSPORTATION_PROTOCOL_PROPERTY_ERROR;
        }
        if (encodeProperty.getEncodeType() == null) {
            return ErrorCode.TRANSPORTATION_PROTOCOL_ENCODE_ERROR;
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * 验证DidAuthentication有效性.
     *
     * @param weIdAuthentication 身份信息
     * @return Error Code and Message
     */
    protected ErrorCode checkDidAuthentication(DidAuthentication weIdAuthentication) {
        if (weIdAuthentication == null
                || weIdAuthentication.getDidPrivateKey() == null
                || weIdAuthentication.getDid() == null) {
            return ErrorCode.DID_AUTHORITY_INVALID;
        }
        if (!DidUtils.validatePrivateKeyDidMatches(
                weIdAuthentication.getDidPrivateKey(),
                weIdAuthentication.getDid())) {
            return ErrorCode.DID_PRIVATEKEY_DOES_NOT_MATCH;
        }
        ResponseData<Boolean> isExists = didService.isDidExist(weIdAuthentication.getDid());
        if (!isExists.getResult()) {
            return ErrorCode.DID_DOES_NOT_EXIST;
        }
        return ErrorCode.SUCCESS;
    }


    /**
     * 验证wrapper数据.
     *
     * @param obj wrapper数据,作为协议的rawData部分
     * @return Error Code and Message
     */
    protected ErrorCode checkProtocolData(Object obj) {
        if (obj == null) {
            return ErrorCode.TRANSPORTATION_PROTOCOL_DATA_INVALID;
        }
        return ErrorCode.SUCCESS;
    }

    protected List<String> getVerifiers() {
        return verifierDidList;
    }

    protected void setVerifier(List<String> verifierDidList) {
        if (CollectionUtils.isEmpty(verifierDidList)) {
            String errorMessage = ErrorCode.ILLEGAL_INPUT.getCode() + "-"
                    + ErrorCode.ILLEGAL_INPUT.getCodeDesc();
            logger.error("[specify] {}, the verifiers is null.", errorMessage);
            throw new DidBaseException(errorMessage);
        }
        for (String weid : verifierDidList) {
            ResponseData<Boolean> isExists = didService.isDidExist(weid);
            if (!isExists.getResult()) {
                String errorMessage = ErrorCode.DID_DOES_NOT_EXIST.getCode() + "-"
                        + ErrorCode.DID_DOES_NOT_EXIST.getCodeDesc();
                logger.error("[specify] {} , weid = {} .", errorMessage, weid);
                throw new DidBaseException(errorMessage);
            }
        }
        this.verifierDidList = verifierDidList;
    }

    protected Method getFromJsonMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        Method targetMethod = null;
        for (Method method : methods) {
            if (method.getName().equals("fromJson")
                    && method.getParameterTypes().length == 1
                    && method.getParameterTypes()[0] == String.class) {
                targetMethod = method;
            }
        }
        return targetMethod;
    }

    protected DidAuthentication getDidAuthentication() {
        return weIdAuthentication;
    }

    protected void setDidAuthentication(DidAuthentication weIdAuthentication) {
        this.weIdAuthentication = weIdAuthentication;
    }
}
