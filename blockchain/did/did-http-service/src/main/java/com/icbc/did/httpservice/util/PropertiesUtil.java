/*
 *       Copyright© (2020) ICBC Co., Ltd.
 *
 *       This file is part of did-http-service.
 *
 *       did-http-service is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       did-http-service is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with did-http-service.  If not, see <https://www.gnu.org/licenses/>.
 */


package com.icbc.did.httpservice.util;

import com.icbc.did.core.config.fabric.v06.ContractConfig;
import com.icbc.did.core.config.fabric.v06.FabricConfig06;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description:
 * @File: PropertiesUtil
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * Properties object.
     */
    private static Properties props;

    /**
     * configuration file.
     */
    private static final String APPLICATION_FILE = "application.properties";

    static {
        loadProps();
    }

    /**
     * load configuration file.
     */
    private static synchronized void loadProps() {
        props = new Properties();
        InputStream resourceAsStream = PropertiesUtil.class.getClassLoader()
            .getResourceAsStream(APPLICATION_FILE);
        try {
            props.load(resourceAsStream);
            logger.info("loadProps finish...");
        } catch (IOException e) {
            logger.error("loadProps error", e);
        }
    }

    /**
     * read the value in the configuration file according to key.
     *
     * @param key configured key
     * @return returns the value of key
     */
    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    /**
     * read the value in the configuration file according to key,
     * returns the default value when it is not available.
     *
     * @param key configured key
     * @param defaultValue  default value
     * @return returns the value of key
     */
    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

    /**
     * On-demand build the com.icbc.did.core.contract com.icbc.did.core.config info.
     *
     * @return the contractConfig instance
     */
    public static ContractConfig buildContractConfig(FabricConfig06 fabricConfig06) {
        ContractConfig contractConfig = new ContractConfig();
        contractConfig.setDidAddress(fabricConfig06.getDidAddress());
        contractConfig.setCptAddress(fabricConfig06.getCptAddress());
        contractConfig.setIssuerAddress(fabricConfig06.getIssuerAddress());
        contractConfig.setEvidenceAddress(fabricConfig06.getEvidenceAddress());
        contractConfig.setSpecificIssuerAddress(fabricConfig06.getSpecificIssuerAddress());
        return contractConfig;
    }
}
