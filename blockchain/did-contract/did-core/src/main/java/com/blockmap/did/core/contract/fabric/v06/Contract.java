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


package com.blockmap.did.core.contract.fabric.v06;

import com.blockmap.bctp.blockchain.access.BlockchainConnector;
import com.blockmap.bctp.blockchain.service.BlockchainService;
import com.blockmap.did.core.config.fabric.v06.FabricConfig06;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description:
 * @File: Contract
 * @Version: 1.0.0
 * @Date: 2019/12/18 19:50
 */

public abstract class Contract {

    private static final Logger logger = LoggerFactory.getLogger(Contract.class);

    protected static final String TPYE_QUERY = "query";
    protected static final String TPYE_INVOKE = "invoke";
    protected static final String TPYE_ASYNCINVOKE = "asyncInvoke";

    protected static FabricConfig06 fabricConfig06;

    static {
        fabricConfig06 = new FabricConfig06();
        if (!fabricConfig06.load()) {
            logger.error("[Contract] Failed to load fabric v0.6 blockchain node information.");
        }
    }

    protected static String DID_CONTRACT_ADDRESS = fabricConfig06.getDidAddress();
    protected static String CTP_CONTRACT_ADDRESS = fabricConfig06.getCptAddress();
    protected static String ISSUER_CONTRACT_ADDRESS = fabricConfig06.getIssuerAddress();
    protected static String EVIDENCE_CONTRACT_ADDRESS = fabricConfig06.getEvidenceAddress();
    protected static String SPECIFIC_ISSUER_CONTRACT_ADDRESS = fabricConfig06.getSpecificIssuerAddress();

    protected Map httpInvoke(String contractAddress, String type, String func, String[] param) {
        BlockchainConnector blockchainConnector = new BlockchainConnector(
                fabricConfig06.getAddress(),
                fabricConfig06.getPort(),
                Integer.valueOf(fabricConfig06.getSyncInvokeTimeout()),
                fabricConfig06.getUser());
        BlockchainService blockchainService = new BlockchainService(blockchainConnector);
        return blockchainService.doRequest(contractAddress, type, func, param);
    }

    protected Map httpsInvoke(String contractAddress, String type, String func, String[] param) {
        BlockchainConnector bc = new BlockchainConnector(
                fabricConfig06.getAddress(),
                fabricConfig06.getPort(),
                Integer.valueOf(fabricConfig06.getSyncInvokeTimeout()),
                fabricConfig06.getUser());
        bc.useHttpsConnect(fabricConfig06.getKeyStorePath(), fabricConfig06.getKeyStorePwd());
        BlockchainService bs = new BlockchainService(bc);
        return bs.doRequest(contractAddress, type, func, param);
    }

}
