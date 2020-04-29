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


package com.blockmap.did.core.protocol.response;

import com.blockmap.did.core.constant.ErrorCode;
import lombok.Data;

/**
 * @Author: The internal base response result class.
 * @Description:
 * @File: ResponseData
 * @Version: 1.0.0
 * @Date: 2019/12/15 20:00
 */

@Data
public class ResponseData<T> {

    /**
     * The generic type result object.
     */
    private T result;

    /**
     * The error code.
     */
    private Integer errorCode;

    /**
     * The error message.
     */
    private String errorMessage;

    /**
     * The blockchain transaction info. Note that this transaction only becomes valid (not null nor
     * blank) when a successful transaction is sent to chain with a block generated.
     */
    private TransactionInfo transactionInfo = null;

    /**
     * Instantiates a new response data.
     */
    public ResponseData() {
        this.setErrorCode(ErrorCode.SUCCESS);
    }

    /**
     * Instantiates a new response data. Transaction info is left null to avoid unnecessary boxing.
     *
     * @param result the result
     * @param errorCode the return code
     */
    public ResponseData(T result, ErrorCode errorCode) {
        this.result = result;
        if (errorCode != null) {
            this.errorCode = errorCode.getCode();
            this.errorMessage = errorCode.getCodeDesc();
        }
    }

    /**
     * Instantiates a new response data with transaction info.
     *
     * @param result the result
     * @param errorCode the return code
     * @param transactionInfo transactionInfo
     */
    public ResponseData(T result, ErrorCode errorCode, TransactionInfo transactionInfo) {
        this.result = result;
        if (errorCode != null) {
            this.errorCode = errorCode.getCode();
            this.errorMessage = errorCode.getCodeDesc();
        }
        if (transactionInfo != null) {
            this.transactionInfo = transactionInfo;
        }
    }

    /**
     * set a ErrorCode type errorCode.
     *
     * @param errorCode the errorCode
     */
    public void setErrorCode(ErrorCode errorCode) {
        if (errorCode != null) {
            this.errorCode = errorCode.getCode();
            this.errorMessage = errorCode.getCodeDesc();
        }
    }

    /**
     * Instantiates a new Response data based on the error code and error message.
     *
     * @param result the result
     * @param errorCode code number
     * @param errorMessage errorMessage
     */
    public ResponseData(T result, Integer errorCode, String errorMessage) {
        this.result = result;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

