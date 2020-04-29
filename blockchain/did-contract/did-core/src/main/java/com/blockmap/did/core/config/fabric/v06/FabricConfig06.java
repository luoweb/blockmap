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


package com.blockmap.did.core.config.fabric.v06;

import com.blockmap.did.core.util.PropertyUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: Fabric Config that loaded by java process, not Spring applicationContext anymore.
 * @File: FabricConfig06
 * @Version: 1.0.0
 * @Date: 2019/12/18 16:57
 */

@Data
public class FabricConfig06 {

    private static final Logger logger = LoggerFactory.getLogger(FabricConfig06.class);

    private String address;
    private String port;
    private String syncInvokeTimeout;
    private String user;
    private String keyStorePath;
    private String keyStorePwd;


    private String didAddress;
    private String cptAddress;
    private String issuerAddress;
    private String evidenceAddress;
    private String specificIssuerAddress;

    public boolean load() {
        try {

            address = PropertyUtils.getProperty("http.address");
            port = PropertyUtils.getProperty("http.port");
            syncInvokeTimeout = PropertyUtils.getProperty("http.syncInvokeTimeout");
            user = PropertyUtils.getProperty("http.user");
            keyStorePath = PropertyUtils.getProperty("https.keyStorePath");
            keyStorePwd = PropertyUtils.getProperty("https.keyStorePwd");

            didAddress = PropertyUtils.getProperty("did.contractaddress");
            cptAddress = PropertyUtils.getProperty("cpt.contractaddress");
            issuerAddress = PropertyUtils.getProperty("issuer.contractaddress");
            evidenceAddress = PropertyUtils.getProperty("evidence.contractaddress");
            specificIssuerAddress = PropertyUtils.getProperty("specificissuer.contractaddress");

            return true;
        } catch (Exception e) {
            logger.error("Error occurred during loading Fabric properties: " + e.getMessage());
            return false;
        }
    }

}
