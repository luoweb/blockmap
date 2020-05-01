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

package com.blockmap.did.core.suite.transportation.qr.protocol;

import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.suite.api.transportation.params.ProtocolProperty;
import com.blockmap.did.core.suite.entity.QrCodeVersion;

/**
 * 协议版本V1.
 * @author admin@xuzhijun.com.cn
 *
 */
public class QrCodeVersion1 extends QrCodeBaseData {

    private static final String protocol = 
        PROTOCOL_VERSION + "encodeType|orgId|id|data|extendData";
    
    private static final String[] protocols;
    
    static {
        //得到协议模板配置的协议字段
        protocols = protocol.split(PROTOCOL_PARTITION_DIVISION);
    }

    public QrCodeBaseData buildBuffer() {
        super.buildBuffer(protocols);
        return this;
    }

    public QrCodeBaseData buildData(String transString) {
        super.buildData(protocols, transString);
        return this;
    }

    @Override
    public void buildQrCodeData(
        ProtocolProperty protocol,
        String orgId
    ) {
        super.buildHead(
            protocol.getEncodeType(),
            QrCodeVersion.V1
        );
        super.setOrgId(orgId);
        super.setId(DataToolUtils.getUuId32());
    }
}
