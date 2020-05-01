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

import com.blockmap.did.core.constant.DidConstant;
import com.blockmap.did.core.exception.DidBaseException;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.web3j.crypto.ECKeyPair;
import com.blockmap.did.core.web3j.crypto.Keys;
import com.blockmap.did.core.web3j.utils.Numeric;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;


/**
 * @Author: admin@xuzhijun.com.cn
 * @Description: The DID Utils.
 * @File: ErrorCode
 * @Version: 1.0.0
 * @Date: 2019/12/15 20:03
 */

public final class DidUtils {

    /**
     * log4j object, for recording log.
     */
    private static final Logger logger = LoggerFactory.getLogger(DidUtils.class);
    
    /**
     * read the chainId from properties.
     */
    private static final String CHAIN_ID = PropertyUtils.getProperty("chain.id");

    /**
     * Convert a DID to a fisco/fabric account address.
     *
     * @param did the DID
     * @return did related address, empty if input DID is illegal
     */
    public static String convertDidToAddress(String did) {
        if (StringUtils.isEmpty(did) || !StringUtils.contains(did, DidConstant.DID_PREFIX)) {
            return StringUtils.EMPTY;
        }
        String[] didFields = StringUtils.splitByWholeSeparator(did, DidConstant.DID_SEPARATOR);
        return didFields[didFields.length - 1];
    }

    /**
     * Convert an account address to  DID.
     * 
     * @param address the address
     * @return a related  DID, or empty string if the input is illegal.
     */
    public static String convertAddressToDid(String address) {
        if (StringUtils.isEmpty(address)) {
            return StringUtils.EMPTY;
        }
        return buildDidByAddress(address);
    }

    /**
     * Check if the input DID is legal or not.
     *
     * @param did the DID
     * @return true if the DID is legal, false otherwise.
     */
    public static boolean isDidValid(String did) {
        return StringUtils.isNotEmpty(did)
            && StringUtils.startsWith(did, DidConstant.DID_PREFIX)
            && isMatchTheChainId(did);
    }

    /**
     * Convert a public key to a  DID.
     *
     * @param publicKey the public key
     * @return  DID
     */
    public static String convertPublicKeyToDid(String publicKey) {
        try {
            String address = Keys.getAddress(new BigInteger(publicKey));
            return buildDidByAddress(address);
        } catch (Exception e) {
            logger.error("convert publicKey to did error.", e);
            return StringUtils.EMPTY;
        }
    }
    
    private static String buildDidByAddress(String address) {
        if (StringUtils.isEmpty(CHAIN_ID)) {
            throw new DidBaseException("the chain Id is illegal.");
        }
        StringBuffer did = new StringBuffer();
        did.append(DidConstant.DID_PREFIX)
            .append(CHAIN_ID)
            .append(DidConstant.DID_SEPARATOR);
        if (!StringUtils.contains(address, DidConstant.HEX_PREFIX)) {
            did.append(DidConstant.HEX_PREFIX);
        }
        did.append(address);
        return did.toString();
    }

    /**
     * check if private key is empty.
     *
     * @param didPrivateKey the DID private key
     * @return true if the private key is not empty, false otherwise.
     */
    public static boolean isPrivateKeyValid(DidPrivateKey didPrivateKey) {
        return (null != didPrivateKey && StringUtils.isNotEmpty(didPrivateKey.getPrivateKey())
            && NumberUtils.isDigits(didPrivateKey.getPrivateKey())
            && new BigInteger(didPrivateKey.getPrivateKey()).compareTo(BigInteger.ZERO) > 0);
    }

    /**
     * check if the public key matchs the private key.
     *
     * @param privateKey the  DID private key
     * @param publicKey the  DID publicKey key
     * @return true if the private and publicKey key is match, false otherwise.
     */
    public static boolean isKeypairMatch(String privateKey, String publicKey) {
        try {
            ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privateKey));
            return StringUtils.equals(String.valueOf(keyPair.getPublicKey()), publicKey);
        } catch (Exception e) {
            return false;
        }
    }


    
    /**
     * check if the chainId.
     *
     * @param did given did
     * @return true if yes, false otherwise.
     */
    public static boolean isMatchTheChainId(String did) {
        String[] didFields = StringUtils.splitByWholeSeparator(did, DidConstant.DID_SEPARATOR);
        if (didFields.length == 4) {
            return didFields[2].equals(CHAIN_ID);
        }
        return true;  
    }



    /**
     * check if the given Address is empty.
     *
     * @param addr given Address
     * @return true if yes, false otherwise.
     */
    public static boolean isEmptyStringAddress(String addr) {
        return Numeric.toBigInt(addr).equals(BigInteger.ZERO);
    }
    
    /**
     * check the did is match the private key.
     *
     * @param privateKey the private key
     * @param did the did
     * @return true if match, false mismatch
     */
    public static boolean validatePrivateKeyDidMatches(DidPrivateKey privateKey, String did) {
        boolean isMatch = false;

        try {
            BigInteger publicKey = DataToolUtils
                .publicKeyFromPrivate(new BigInteger(privateKey.getPrivateKey()));
            String address1 = "0x" + Keys.getAddress(publicKey);
            String address2 = DidUtils.convertDidToAddress(did);
            if (address1.equals(address2)) {
                isMatch = true;
            }
        } catch (Exception e) {
            logger.error("Validate private key We Id matches failed. Error message :{}", e);
            return isMatch;
        }

        return isMatch;
    }
}
