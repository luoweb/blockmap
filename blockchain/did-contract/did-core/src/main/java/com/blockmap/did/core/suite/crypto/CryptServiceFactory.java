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

package com.blockmap.did.core.suite.crypto;

import com.blockmap.did.core.exception.EncodeSuiteException;
import com.blockmap.did.core.suite.entity.CryptType;

import java.util.HashMap;
import java.util.Map;

/**
 * 秘钥对象工厂, 根据不同类型秘钥得到相应的秘钥处理对象.
 * @author admin@xuzhijun.com.cn
 *
 */
public class CryptServiceFactory {
    
    /**
     * 支持加密类型的配置Map，目前支持仅支持AES.
     */
    private static final Map<String, CryptService> cryptServiceMap =
        new HashMap<String, CryptService>();
    
    static {
        cryptServiceMap.put(CryptType.AES.name(), new AesCryptService());
    }

    /**
     * 通过秘钥枚举类型获取秘钥对象.
     * @param cryptType 秘钥枚举类型
     * @return 秘钥加解密处理对象
     */
    public static CryptService getCryptService(CryptType cryptType) {
        CryptService service = cryptServiceMap.get(cryptType.name());
        if (service == null) {
            throw new EncodeSuiteException();
        }
        return service;
    }
}
