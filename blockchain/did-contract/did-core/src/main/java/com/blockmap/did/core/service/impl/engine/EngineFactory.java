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


package com.blockmap.did.core.service.impl.engine;

import com.blockmap.did.core.constant.DidConstant;
import com.blockmap.did.core.util.PropertyUtils;
import com.blockmap.did.core.service.impl.engine.fabric.v06.AuthorityIssuerEngineV06;
import com.blockmap.did.core.service.impl.engine.fabric.v06.CptServiceEngineV06;
import com.blockmap.did.core.service.impl.engine.fabric.v06.DidServiceEngineV06;
import com.blockmap.did.core.service.impl.engine.fabric.v2.AuthorityIssuerEngineV2;
import com.blockmap.did.core.service.impl.engine.fabric.v2.CptServiceEngineV2;
import com.blockmap.did.core.service.impl.engine.fabric.v2.DidServiceEngineV2;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description:
 * @File: EngineFactory
 * @Version: 1.0.0
 * @Date: 2019/12/18 10:40
 */

public class EngineFactory {

    /**
     * FABRIC version, default 1.3.x
     */
    private static String fabricVersion = PropertyUtils.getProperty("fabric.version", "0.6");

    /**
     * create DidServiceEngine.
     * @return DidServiceEngine object
     */
    public static DidServiceEngine createDidServiceEngine() {
        if (fabricVersion.startsWith(DidConstant.FABRIC_0_X_VERSION_PREFIX)) {
            return new DidServiceEngineV06();
        }
        return new DidServiceEngineV2();
    }

    /**
     * create CptServiceEngine.
     * @return CptServiceEngine object
     */
    public static CptServiceEngine createCptServiceEngine() {
        if (fabricVersion.startsWith(DidConstant.FABRIC_0_X_VERSION_PREFIX)) {
            return new CptServiceEngineV06();
        }
        return new CptServiceEngineV2();
    }
    /**
     * create CptServiceEngine.
     * @return CptServiceEngine object
     */
    public static AuthorityIssuerServiceEngine createAuthorityIssuerServiceEngine() {
        if (fabricVersion.startsWith(DidConstant.FABRIC_0_X_VERSION_PREFIX)) {
            return new AuthorityIssuerEngineV06();
        }
        return new AuthorityIssuerEngineV2();
    }

}
