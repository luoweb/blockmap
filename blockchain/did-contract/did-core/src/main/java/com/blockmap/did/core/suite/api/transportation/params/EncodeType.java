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

package com.blockmap.did.core.suite.api.transportation.params;

/**
 * enumeration of supported coding types.
 * 
 * @author admin@xuzhijun.com.cn
 *
 */
public enum EncodeType {

    /**
     * The original type.
     */
    ORIGINAL(0),
    
    /**
     * The cipher type.
     */
    CIPHER(1);
    
    /**
     * encode number.
     */
    private int code;

    
    EncodeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String toString() {
        return String.valueOf(this.code);
    }
    
    /**
     * get EncodeType by code.
     * @param value code value
     * @return codeType
     */
    public static EncodeType getObject(String value) {
        for (EncodeType codeType : EncodeType.values()) {
            if (String.valueOf(codeType.getCode()).equals(value)) {
                return codeType;
            }
        }
        return null;
    }
}
