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

package com.blockmap.did.core.protocol.response.wesign;

import lombok.Data;

import java.util.Date;

/**
 * Verify timestamp response.
 *
 * @author xuzj01@sdc.blockmap.com
 **/
@Data
public class VerifyTimestampResponse {

    private int code;
    private String msg;
    private String transactionTime;
    private String bizSeqNo;
    private Result result;

    @Data
    public class Result {

        private String bizSeqNo;
        private String transactionTime;
        private TimestampData data;

        @Data
        public class TimestampData {

            private Date signTime;
            private Boolean verifyResult;
        }
    }

}
