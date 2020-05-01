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

package com.blockmap.did.core.protocol.inf;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * proof接口.
 *
 *
 */
public interface IProof {
    
    /**
     * 从proof中获取key对应value.
     * @param proof proofMap数据
     * @param key 要获取的数据的key
     * @return 返回key的数据
     */
    public default Object getValueFromProof(Map<String, Object> proof, String key) {
        if (proof != null) {
            return proof.get(key);
        }
        return null;
    }
    
    /**
     * 将Object转换成String.
     * @param obj 从proof中取出的object
     * @return 返回key的字符串数据
     */
    public default String toString(Object obj) {
        if (obj != null) {
            return String.valueOf(obj);
        }
        return StringUtils.EMPTY;
    }
}
