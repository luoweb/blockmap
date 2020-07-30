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

import com.icbc.did.httpservice.constant.DidParamKeyConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: the com.icbc.did.com.icbc.did.core.util of the private key
 * @File: KeyUtil
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

public class KeyUtil {

    private static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);

    /**
     * SDK private key storage path.
     */
    public static final String SDK_PRIVKEY_PATH =
        PropertiesUtil.getProperty("admin.privKeyPath");

    /**
     * slash.
     */
    private static final String SLASH_CHARACTER = "/";

    /**
     * this method stores did private key information by file and stores private key information by
     * itself in actual scene.
     *
     * @param path save path
     * @param did the did
     * @param privateKey the private key
     * @return returns saved results
     */
    public static boolean savePrivateKey(String path, String did, String privateKey) {

        try {
            if (null == did) {
                logger.error("did is null");
                return false;
            }

            // get the third paragraph of did.
            String fileName = did.substring(did.lastIndexOf(":") + 1);

            // check whether the path exists or not, then create the path and return.
            String checkPath = checkDir(path);
            String filePath = checkPath + fileName;

            logger.info("save private key into file, did={}, filePath={}", did, filePath);

            // save the private key information as the file name for the third paragraph of did.
            saveFile(filePath, privateKey);
            return true;
        } catch (Exception e) {
            logger.error("savePrivateKey error", e);
        }
        return false;
    }

    /**
     * get the private key by did.
     *
     * @param path the path
     * @param did the did
     * @return returns the private key
     */
    public static String getPrivateKeyByDid(String path, String did) {

        if (null == did) {
            logger.error("did is null");
            return StringUtils.EMPTY;
        }

        // get the third paragraph of did.
        String fileName = did.substring(did.lastIndexOf(":") + 1);

        // check the default passphrase
        String passphrase = PropertiesUtil.getProperty("default.passphrase");
        if (fileName.equalsIgnoreCase(passphrase)) {
            fileName = DidParamKeyConstant.DEFAULT_PRIVATE_KEY_FILE_NAME;
        }

        // check whether the path exists or not, then create the path and return.
        String checkPath = checkDir(path);
        String filePath = checkPath + fileName;

        logger.info("get private key from file, did={}, filePath={}", did, filePath);

        // get private key information from a file according to the third paragraph of did.
        String privateKey = getDataByPath(filePath);
        return privateKey;
    }

    /**
     * check the path is exists, create and return the path if it does not exist.
     *
     * @param path the path
     * @return returns the path
     */
    public static String checkDir(String path) {

        String checkPath = path;

        // stitching the last slash.
        if (!checkPath.endsWith(SLASH_CHARACTER)) {
            checkPath = checkPath + SLASH_CHARACTER;
        }

        // check the path, create the path when it does not exist.
        File checkDir = new File(checkPath);
        if (!checkDir.exists()) {
            boolean success = checkDir.mkdirs();
            if (!success) {
                logger.error("checkDir.mkdirs");
            }
        }
        return checkPath;
    }

    /**
     * read data from the file.
     *
     * @param path the file path
     * @return returns the data
     */
    public static String getDataByPath(String path) {

        logger.info("get data form [{}]", path);
        FileInputStream fis = null;
        String str = null;
        try {
            fis = new FileInputStream(path);
            byte[] buff = new byte[fis.available()];
            int size = fis.read(buff);
            if (size > 0) {
                str = new String(buff, StandardCharsets.UTF_8);
            }
        } catch (FileNotFoundException e) {
            logger.error("the file path is not exists", e);
        } catch (IOException e) {
            logger.error("getDataByPath error", e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("fis close error", e);
                }
            }
        }
        return str;
    }

    /**
     * save data in a specified file.
     *
     * @param filePath save file path
     * @param dataStr save data
     * @return return the file path
     */
    public static String saveFile(String filePath, String dataStr) {

        logger.info("save data in to [{}]", filePath);
        OutputStreamWriter ow = null;
        try {
            File file = new File(filePath);
            ow = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            ow.write(dataStr);
            return file.getAbsolutePath();
        } catch (IOException e) {
            logger.error("writer file com.icbc.did.core.exception", e);
        } finally {
            if (null != ow) {
                try {
                    ow.close();
                } catch (IOException e) {
                    logger.error("OutputStreamWriter close com.icbc.did.core.exception", e);
                }
            }
        }
        return StringUtils.EMPTY;
    }
}
