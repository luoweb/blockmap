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

import com.blockmap.did.core.constant.ErrorCode;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: DidBase Exception. Base Exception for Did Project.
 * @File: DidBaseException
 * @Version: 1.0.0
 * @Date: 2019/12/16 13:51
 */

@SuppressWarnings("serial")
public class DidBaseException extends RuntimeException {

    private ErrorCode errorCode = ErrorCode.BASE_ERROR;

    /**
     * constructor.
     *
     * @param msg com.blockmap.did.core.exception message
     * @param cause com.blockmap.did.core.exception object
     */
    public DidBaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * constructor.
     *
     * @param msg com.blockmap.did.core.exception message
     */
    public DidBaseException(String msg) {
        super(msg);
    }

    /**
     * constructor.
     *
     * @param errorCode the errorCode
     */
    public DidBaseException(ErrorCode errorCode) {
        this(errorCode.getCode() + " - " + errorCode.getCodeDesc());
        this.errorCode = errorCode;
    }

    /**
     * get associated error code.
     *
     * @return ErrorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        StringBuilder builder = new StringBuilder();
        builder
                .append(s)
                .append(". Error code =")
                .append(getErrorCode().getCode())
                .append(", Error message : ")
                .append(getMessage());
        return builder.toString();
    }
}
