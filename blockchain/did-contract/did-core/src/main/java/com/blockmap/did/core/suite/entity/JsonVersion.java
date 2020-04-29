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

/**
 * JSON传输协议枚举.
 * @author admin@xuzhijun.com.cn
 *
 */
public enum JsonVersion {

    V1(1);
    
    private int code;

    JsonVersion(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
    /**
     * get JsonVersion By code.
     *
     * @param code the JsonVersion
     * @return JsonVersion
     */
    public static JsonVersion getJsonVersion(int code) {
        for (JsonVersion version : JsonVersion.values()) {
            if (version.getCode() == code) {
                return version;
            }
        }
        return null;
    }
}
