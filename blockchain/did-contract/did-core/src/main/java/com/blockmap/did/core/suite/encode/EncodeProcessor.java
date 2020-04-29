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

package com.blockmap.did.core.suite.encode;

import com.blockmap.did.core.exception.EncodeSuiteException;
import com.blockmap.did.core.suite.entity.EncodeData;

/**
 * 编解码处理器接口.
 * @author admin@xuzhijun.com.cn
 *
 */
public interface EncodeProcessor {

    /**
     * 编码处理方法定义.
     * @param encodeData 需要编码的实体数据
     * @return 返回编码后的数据
     * @throws EncodeSuiteException Exception
     */
    public String encode(EncodeData encodeData) throws EncodeSuiteException;
    
    /**
     * 解码处理方法定义.
     * @param encodeData 需要解码的实体数据
     * @return 返回解密后的数据
     * @throws EncodeSuiteException Exception
     */
    public String decode(EncodeData encodeData) throws EncodeSuiteException;
}
