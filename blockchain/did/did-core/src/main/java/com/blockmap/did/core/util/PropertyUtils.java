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


package com.blockmap.did.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: tools for properties.
 * @File: PropertyUtils
 * @Version: 1.0.0
 * @Date: 2019/12/16 9:55
 */


public final class PropertyUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    private static final String FABRIC_PROP_NAME = "fabric.properties";
    private static final String DID_PROP_NAME = "did.properties";
    private static Properties prop = new Properties();

    static {
        try {
            loadProperties(FABRIC_PROP_NAME);
            loadProperties(DID_PROP_NAME);
        } catch (IOException e) {
            logger.error("[PropertyUtils] Load fabric.properties/did.properties file failed.", e);
        }
    }

    /**
     * load properties from specific com.blockmap.did.core.config file.
     *
     * @param filePath properties com.blockmap.did.core.config file.
     */
    private static void loadProperties(String filePath) throws IOException {

        InputStream in;
        BufferedReader br = null;
        try {
            in = PropertyUtils.class.getClassLoader().getResourceAsStream(filePath);
            br = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            in = PropertyUtils.class.getClassLoader().getResourceAsStream("/resource/" + filePath);
            br = new BufferedReader(new InputStreamReader(in));
        }
        prop.load(br);
        br.close();
        in.close();
    }

    /**
     * get property value by specific key.
     *
     * @param key property key
     * @return value of the key
     */
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    /**
     * get property value by specific key.
     *
     * @param key          property keys
     * @param defaultValue default value
     * @return value of the key
     */
    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    /**
     * get the all key from Properties.
     *
     * @return value of the key Set
     */
    public static Set<Object> getAllPropertyKey() {
        return prop.keySet();
    }
}
