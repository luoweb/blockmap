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
 * @Description: Load Contract Exception.
 * @File: LoadContractException
 * @Version: 1.0.0
 * @Date: 2019/12/16 13:54
 */


@SuppressWarnings("serial")
public class LoadContractException extends DidBaseException {

    public LoadContractException(Throwable cause) {
        super(ErrorCode.LOAD_CONTRACT_FAILED.getCodeDesc(), cause);
    }

    public LoadContractException() {
        super(ErrorCode.LOAD_CONTRACT_FAILED.getCodeDesc());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.LOAD_CONTRACT_FAILED;
    }
}
