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


package com.blockmap.did.core.constant;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: credential field disclosure status.
 * @File: CredentialFieldDisclosureValue
 * @Version: 1.0.0
 * @Date: 2019/12/16 19:22
 */


public enum CredentialFieldDisclosureValue {

    /**
     * the field is existed.
     */
    EXISTED(2),

    /**
     * the field is disclosed.
     */
    DISCLOSED(1),

    /**
     * the field is not disclosed.
     */
    NOT_DISCLOSED(0);

    /**
     * disclosure status.
     */
    private Integer status;

    CredentialFieldDisclosureValue(Integer status) {
        this.status = status;
    }

    /**
     * get field disclosure status.
     *
     * @return disclosure status of the field.
     */
    public Integer getStatus() {
        return status;
    }
}
