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

package com.blockmap.did.core.suite.entity;

import com.blockmap.did.core.protocol.base.DidAuthentication;
import lombok.Getter;

import java.util.List;

/**
 * 编辑码的实体类，封装了需要编解码的数据.
 * @author admin@xuzhijun.com.cn
 *
 */
@Getter
public class EncodeData {

    /**
     * 机构名称.
     */
    private String orgId;
   
    /**
     * 待编解码字符串.
     */
    private String data;
   
    /**
     * 待编解码字符串数据编号.
     */
    private String id;
    
    /**
     * 协议数据指定用户.
     */
    private List<String> verifiers;
    
    /**
     * 解码者身份信息.
     */
    private DidAuthentication weIdAuthentication;
   
    private EncodeData(String id, String orgId, String data) {
        this.id = id;
        this.orgId = orgId;
        this.data = data;
    }
    
    /**
     * 构建编解码对象.
     * @param orgId 协议所属机构
     * @param id 数据编号
     * @param data 需要编解码数据
     * @param verifiers 协议数据指定用户
     */
    public EncodeData(
        String id,
        String orgId,
        String data,
        List<String> verifiers
    ) {
        this(id, orgId, data);
        this.verifiers = verifiers;
    }
    
    /**
     * 构建编解码对象.
     * @param orgId 协议所属机构
     * @param id 数据编号
     * @param data 需要编解码数据
     * @param weIdAuthentication 解码身份信息
     */
    public EncodeData(String id, String orgId, String data, DidAuthentication weIdAuthentication) {
        this(id, orgId, data);
        this.weIdAuthentication = weIdAuthentication;
    }
}
