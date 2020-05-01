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


package com.blockmap.did.core.exception;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: get Did document, resolve attribute Exception.
 * @File: ResolveAttributeException
 * @Version: 1.0.0
 * @Date: 2019/12/16 13:52
 */

@SuppressWarnings("serial")
public class ResolveAttributeException extends RuntimeException {

    private Integer errorCode;
    private String errorMessage;

    /**
     * constructor.
     *
     * @param errorCode com.blockmap.did.core.exception error code.
     * @param errorMessage com.blockmap.did.core.exception error messave.
     */
    public ResolveAttributeException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
