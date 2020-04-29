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

package com.blockmap.did.core.suite.api.transportation.inf;

import com.blockmap.did.core.protocol.base.DidAuthentication;
import com.blockmap.did.core.protocol.inf.JsonSerializer;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.suite.api.transportation.params.ProtocolProperty;

import java.util.List;

/**
 * 协议的传输接口.
 * @author admin@xuzhijun.com.cn
 *
 */
public interface JsonTransportation {
    
    JsonTransportation specify(List<String> verifierDidList);

    /**
     * 协议传输序列化接口.
     * @param object 协议存储的实体数据对象
     * @param <T> the type of the element
     * @param property 协议的配置对象
     * @return 返回协议字符串数据
     */
    <T extends JsonSerializer> ResponseData<String> serialize(
            T object,
            ProtocolProperty property
    );

    /**
     * 协议反序列化接口.
     * @param transString JSON格式的协议数据字符串
     * @param clazz 需要转换成的Class类型
     * @param <T> the type of the element
     * @return 返回PresentationE对象数据
     */
    @Deprecated
    <T extends JsonSerializer> ResponseData<T> deserialize(
            String transString,
            Class<T> clazz
    );
    
    /**
     * 协议反序列化接口，支持权限控制.
     * @param weIdAuthentication 验证方当前authentication信息 
     * @param transString JSON格式的协议数据字符串
     * @param clazz 需要转换成的Class类型
     * @param <T> the type of the element
     * @return 返回PresentationE对象数据
     */
    <T extends JsonSerializer> ResponseData<T> deserialize(
            DidAuthentication weIdAuthentication,
            String transString,
            Class<T> clazz
    );
    
}
