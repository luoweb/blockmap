/*
 *       Copyright© (2020) ICBC Co., Ltd.
 *
 *       This file is part of did-http-com.icbc.did.core.service.
 *
 *       did-http-com.icbc.did.core.service is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       did-http-com.icbc.did.core.service is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with did-http-com.icbc.did.core.service.  If not, see <https://www.gnu.org/licenses/>.
 */


package com.icbc.did.httpservice.service.impl;


import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.icbc.did.core.constant.ErrorCode;
import com.icbc.did.core.util.PropertyUtils;
import com.icbc.did.httpservice.constant.HttpReturnCode;
import com.icbc.did.httpservice.protocol.base.Cache4QrCode;
import com.icbc.did.httpservice.protocol.base.QrCode;
import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import com.icbc.did.httpservice.service.BaseService;
import com.icbc.did.httpservice.service.InvokerQrCodeService;
import com.icbc.did.httpservice.util.JsonUtil;
import com.icbc.did.core.suite.api.persistence.Persistence;
import com.icbc.did.core.suite.persistence.sql.driver.MysqlDriver;
import com.icbc.did.core.util.DataToolUtils;
import com.icbc.did.httpservice.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description:
 * @File: InvokerQrCodeServiceImpl
 * @Version: 1.0.0
 * @Date: 2019/12/16 20:02
 */

@Component
public class InvokerQrCodeServiceImpl extends BaseService implements InvokerQrCodeService {

    private Logger logger = LoggerFactory.getLogger(InvokerQrCodeServiceImpl.class);

    private static final String WAN_SERVER_IP = PropertiesUtil.getProperty("WAN.server.ip");
    private static final String WAN_SERVER_PORT = PropertiesUtil.getProperty("WAN.server.port");


    private Persistence dataDriver;

    private Persistence getDataDriver() {
        if (dataDriver == null) {
            dataDriver = new MysqlDriver();
        }
        return dataDriver;
    }


    @Override
    public HttpResponseData<Object> activeQrCodeInvoke(InputArg activeQrCodeFuncArgs) {
        QrCode qrCode = null;
        try {
            qrCode = (QrCode) JsonUtil
                    .jsonStrToObj(new QrCode(), activeQrCodeFuncArgs.getFunctionArg());
        } catch (Exception e) {
            // The input credential is a json, so keep moving down.
            logger.info("Detected Portable-Json format qrCode, continuing..");
        }

        if (qrCode == null) {
            try {
                Map<String, Object> credMap = (Map<String, Object>) JsonUtil
                        .jsonStrToObj(new HashMap<String, Object>(),
                                activeQrCodeFuncArgs.getFunctionArg());
                credMap = JsonUtil.reformatCredentialPojoToJson(credMap);
                qrCode = (QrCode) JsonUtil
                        .jsonStrToObj(new QrCode(), JsonUtil.mapToCompactJson(credMap));
            } catch (Exception e) {
                logger.error("Input qrCode format illegal: {}", activeQrCodeFuncArgs);
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_ILLEGAL.getCode(),
                        HttpReturnCode.INPUT_ILLEGAL.getCodeDesc().concat(e.getMessage()));
            }
        }
        if (!Cache4QrCode.isExist(qrCode)){
            return new HttpResponseData<>(false, -1, "QR code not found, please create qr code first!");
        }

        try {
            Cache4QrCode.active(qrCode);
            long start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            long cost = end - start;
            while (cost < 20000) {
                end = System.currentTimeMillis();
                cost = end - start;
                if (Cache4QrCode.isScanned(qrCode)) {
                    break;
                }
            }
            if (Cache4QrCode.isScanned(qrCode)) {
                return new HttpResponseData<>(true, 0, "success");
            } else {
                return new HttpResponseData<>(false, -1, "QR code is timeout");
            }

        } catch (Exception e) {
            logger.error("[invokerQrCodeServiceImpl]: SDK error. reqQrCodeArgs:{}",
                    activeQrCodeFuncArgs,
                    e);
            return new HttpResponseData<>(null, HttpReturnCode.DID_SDK_ERROR.getCode(),
                    HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }

    @Override
    public HttpResponseData<Object> createQrCodeInvoke(InputArg createQrCodeFuncArgs) {
        QrCode qrCode = null;
        try {
            qrCode = (QrCode) JsonUtil
                    .jsonStrToObj(new QrCode(), createQrCodeFuncArgs.getFunctionArg());
        } catch (Exception e) {
            // The input credential is a json, so keep moving down.
            logger.info("Detected Portable-Json format qrCode, continuing..");
        }

        if (qrCode == null) {
            try {
                Map<String, Object> credMap = (Map<String, Object>) JsonUtil
                        .jsonStrToObj(new HashMap<String, Object>(),
                                createQrCodeFuncArgs.getFunctionArg());
                credMap = JsonUtil.reformatCredentialPojoToJson(credMap);
                qrCode = (QrCode) JsonUtil
                        .jsonStrToObj(new QrCode(), JsonUtil.mapToCompactJson(credMap));
            } catch (Exception e) {
                logger.error("Input qrCode format illegal: {}", createQrCodeFuncArgs);
                return new HttpResponseData<>(null, HttpReturnCode.INPUT_ILLEGAL.getCode(),
                        HttpReturnCode.INPUT_ILLEGAL.getCodeDesc().concat(e.getMessage()));
            }
        }

        try {
            Cache4QrCode.add(qrCode);
            String url = "http://"+WAN_SERVER_IP+":"+WAN_SERVER_PORT+"/did/api/verify?args=";
            String body = "%7B%22functionArg%22:%7B%22id%22:%22"+qrCode.getId()+"%22%7D,%22transactionArg%22:%7B%7D,%22v%22:%221.0.0%22,%22functionName%22:%22getCredentialByQrCodeId%22%7D";
            String content = url+body;
            logger.info("Code Content: {}", content);
            OutputStream outputStream = new ByteArrayOutputStream();
            int errorCode = DataToolUtils.generateQrCode(content, ErrorCorrectionLevel.M, outputStream);
            if (errorCode == 0) {
                Map result = new HashMap();
                result.put("img", outputStream);
                return new HttpResponseData<>(
                        JsonUtil.convertJsonToSortedMap(JsonUtil.mapToCompactJson(result)),
                        HttpReturnCode.SUCCESS);
            } else {
                return new HttpResponseData<>(false, errorCode, ErrorCode.getTypeByErrorCode(errorCode).getCodeDesc());
            }
        } catch (Exception e) {
            logger.error("[invokerQrCodeServiceImpl]: SDK error. reqQrCodeArgs:{}",
                    createQrCodeFuncArgs,
                    e);
            return new HttpResponseData<>(null, HttpReturnCode.DID_SDK_ERROR.getCode(),
                    HttpReturnCode.DID_SDK_ERROR.getCodeDesc().concat(e.getMessage()));
        }
    }

}
