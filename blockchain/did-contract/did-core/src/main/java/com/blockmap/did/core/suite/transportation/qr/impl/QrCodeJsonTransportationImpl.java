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

package com.blockmap.did.core.suite.transportation.qr.impl;

import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.exception.DidBaseException;
import com.blockmap.did.core.protocol.base.DidAuthentication;
import com.blockmap.did.core.protocol.inf.JsonSerializer;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.suite.api.transportation.inf.QrCodeTransportation;
import com.blockmap.did.core.suite.transportation.AbstractJsonTransportation;
import com.blockmap.did.core.suite.transportation.qr.protocol.QrCodeBaseData;
import com.blockmap.did.core.suite.transportation.qr.protocol.QrCodeVersion1;
import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.suite.api.transportation.params.ProtocolProperty;
import com.blockmap.did.core.suite.encode.EncodeProcessorFactory;
import com.blockmap.did.core.suite.entity.EncodeData;
import com.blockmap.did.core.suite.entity.QrCodeVersion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码传输协议业务处理类.
 * @author admin@xuzhijun.com.cn
 *
 */
public class QrCodeJsonTransportationImpl
    extends AbstractJsonTransportation
    implements QrCodeTransportation {

    private static final Logger logger = 
        LoggerFactory.getLogger(QrCodeJsonTransportationImpl.class);
    
    private static final QrCodeVersion version = QrCodeVersion.V1;
    
    /**
     * 支持的协议版本配置.
     */
    private static final Map<String, Class<?>> protocol_version_map;
    
    static {
        protocol_version_map = new HashMap<String, Class<?>>();
        protocol_version_map.put(QrCodeVersion.V1.name(), QrCodeVersion1.class);
    }
    
    @Override
    public <T extends JsonSerializer> ResponseData<String> serialize(
        T object, 
        ProtocolProperty property) {
        return null;
        
//        logger.info(
//            "[serialize] begin to execute QrCodeTransportation serialization, property:{}",
//            property
//        );
//        // 验证协议配置
//        ErrorCode errorCode = checkEncodeProperty(property);
//        if (errorCode != ErrorCode.SUCCESS) {
//            logger.error("[serialize] checkEncodeProperty fail, errorCode:{}.", errorCode);
//            return new ResponseData<String>(StringUtils.EMPTY, errorCode);
//        }
//        // 验证presentation数据
//        errorCode = checkProtocolData(object);
//        if (errorCode != ErrorCode.SUCCESS) {
//            logger.error("[serialize] checkProtocolData fail, errorCode:{}.", errorCode);
//            return new ResponseData<String>(StringUtils.EMPTY, errorCode);
//        }
//        try {
//            // 根据协议版本生成协议实体对象
//            QrCodeBaseData qrCodeData =
//                QrCodeBaseData.newInstance(
//                    protocol_version_map.get(version.name())
//                );
//            // 构建协议header
//            qrCodeData.buildQrCodeData(
//                property,
//                fiscoConfig.getCurrentOrgId()
//            );
//            // 创建编解码实体对象，对此实体中的data编码操作
//            EncodeData encodeData =
//                new EncodeData(
//                    qrCodeData.getId(),
//                    qrCodeData.getOrgId(),
//                    object.toJson(),
//                    super.getVerifiers()
//                );
//            logger.info("[serialize] encode by {}.", property.getEncodeType().name());
//            // 进行编码处理
//            String data =
//                EncodeProcessorFactory
//                    .getEncodeProcessor(qrCodeData.getEncodeType())
//                    .encode(encodeData);
//            qrCodeData.setData(data);
//            // 将协议实体转换成协议字符串数据
//            String transString = qrCodeData.buildBuffer().getTransString();
//            logger.info("[serialize] QrCodeTransportation serialization finished.");
//            return new ResponseData<String>(transString, ErrorCode.SUCCESS);
//        } catch (DidBaseException e) {
//            logger.error("[serialize] QrCodeTransportation serialization due to base error.", e);
//            return new ResponseData<String>(StringUtils.EMPTY, e.getErrorCode());
//        } catch (Exception e) {
//            logger.error("[serialize] QrCodeTransportation serialization due to unknown error.", e);
//            return new ResponseData<String>(StringUtils.EMPTY, ErrorCode.UNKNOW_ERROR);
//        }
    }  

    @Override
    public <T extends JsonSerializer> ResponseData<T> deserialize(
        String transString,
        Class<T> clazz) {
        
        logger.info("[deserialize] begin to execute QrCodeTransportation deserialize.");
        try {
            //解析协议版本
            QrCodeVersion version = QrCodeBaseData.getQrCodeVersion(transString);
            //根据协议版本生成协议实体对象
            QrCodeBaseData qrCodeData = 
                QrCodeBaseData.newInstance(protocol_version_map.get(version.name()));
            //将协议字符串构建成协议对象
            qrCodeData.buildData(transString);
            EncodeData encodeData = 
                new EncodeData(
                    qrCodeData.getId(),
                    qrCodeData.getOrgId(),
                    qrCodeData.getData(),
                    super.getDidAuthentication()
                );
            logger.info("[deserialize] encode by {}.", qrCodeData.getEncodeType().name());
            //进行解码处理
            String data = 
                EncodeProcessorFactory
                    .getEncodeProcessor(qrCodeData.getEncodeType())
                    .decode(encodeData);
            //将解压出来的数据进行反序列化成原数据对象
            //T presentation = JsonUtil.jsonStrToObj(clazz, data);
            String presentationEStr = DataToolUtils.convertUtcToTimestamp(data);
            String presentationEStrNew = presentationEStr;
            if (DataToolUtils.isValidFromToJson(presentationEStr)) {
                presentationEStrNew = DataToolUtils.removeTagFromToJson(presentationEStr);
            }
            T object = null;
            Method method = getFromJsonMethod(clazz);
            if (method == null) {
                //调用工具的反序列化 
                object = (T) DataToolUtils.deserialize(presentationEStrNew, clazz);
            } else  {
                object = (T) method.invoke(null, presentationEStrNew);
            }
            logger.info("[deserialize] QrCodeTransportation deserialize finished.");
            return new ResponseData<T>(object, ErrorCode.SUCCESS);
        } catch (DidBaseException e) {
            logger.error("[deserialize] QrCodeTransportation deserialize due to base error.", e);
            return new ResponseData<T>(null, e.getErrorCode());
        } catch (Exception e) {
            logger.error("[deserialize] QrCodeTransportation deserialize due to unknown error.", e);
            return new ResponseData<T>(null, ErrorCode.UNKNOW_ERROR);
        }
    }
    
    @Override
    public <T extends JsonSerializer> ResponseData<T> deserialize(
        DidAuthentication weIdAuthentication,
        String transString, 
        Class<T> clazz
    ) {
        //检查DidAuthentication合法性
        ErrorCode errorCode = checkDidAuthentication(weIdAuthentication);
        if (errorCode != ErrorCode.SUCCESS) {
            logger.error("[deserialize] checkDidAuthentication fail, errorCode:{}.", errorCode);
            return new ResponseData<T>(null, errorCode);
        }
        super.setDidAuthentication(weIdAuthentication);
        return deserialize(transString, clazz);
    }
}
