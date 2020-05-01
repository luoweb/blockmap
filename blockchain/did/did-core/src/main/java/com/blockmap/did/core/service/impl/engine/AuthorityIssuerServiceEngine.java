/*
 *       Copyright© (2018-2019) blockmap Co., Ltd.
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

import com.blockmap.did.core.protocol.base.AuthorityIssuer;
import com.blockmap.did.core.protocol.request.RegisterAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.request.RemoveAuthorityIssuerArgs;
import com.blockmap.did.core.protocol.response.ResponseData;

import java.util.List;

/**
 * 作为调用支持不同FABRIC平台版本的AuthorityIssuer合约的service engine， 根据FABRIC版本不同，
 * 调用不同版本的智能合约方法，并处理合约返回的数据.
 *
 * @author admin@xuzhijun.com.cn 2019年12月25日
 */
public interface AuthorityIssuerServiceEngine {

    /**
     * call authority issuer contract method to add an authority issuer.
     *
     * @param args parameters
     * @return result
     */
    public ResponseData<Boolean> addAuthorityIssuer(RegisterAuthorityIssuerArgs args);

    /**
     * call authority issuer contract method to remove an authority issuer.
     *
     * @param args parameters
     * @return result
     */
    public ResponseData<Boolean> removeAuthorityIssuer(RemoveAuthorityIssuerArgs args);

    /**
     * check if the authority issuer is registered on the blockchain.
     *
     * @param address identity address
     * @return result
     */
    public ResponseData<Boolean> isAuthorityIssuer(String address);

    /**
     * call authority issuer contract method to query information of an authority issuer.
     *
     * @param did the entity's weidentity did
     * @return result
     */
    public ResponseData<AuthorityIssuer> getAuthorityIssuerInfoNonAccValue(String did);

    /**
     * call authority issuer contract method to query all authority issuer address.
     *
     * @param index the index
     * @param num the num
     * @return authority issuer address list
     */
    public List<String> getAuthorityIssuerAddressList(Integer index, Integer num);

    /**
     * call specific issuer contract method to remove a issuer.
     *
     * @param issuerType issuerType value
     * @param issuerAddress issuer address
     * @param privateKey the caller's private key
     * @return result
     */
    public ResponseData<Boolean> removeIssuer(
            String issuerType,
            String issuerAddress,
            String privateKey
    );

    /**
     * call specific issuer contract to check the address is a specific issuer .
     *
     * @param issuerType issuerType value
     * @param address issuer's address
     * @return result
     */
    public ResponseData<Boolean> isSpecificTypeIssuer(String issuerType, String address);

    /**
     * call specific issuer contract to query specific issuer address.
     *
     * @param issuerType issuerType value
     * @param index index
     * @param num num
     * @return result
     */
    public ResponseData<List<String>> getSpecificTypeIssuerList(
            String issuerType,
            Integer index,
            Integer num
    );

    /**
     * call specific issuer contract to register issuer type.
     *
     * @param issuerType issuerType value
     * @param privateKey the caller's private key
     * @return result
     */
    public ResponseData<Boolean> registerIssuerType(String issuerType, String privateKey);

    /**
     * call specific issuer contract to add issuer.
     *
     * @param issuerType issuerType value
     * @param issuerAddress issuer's address
     * @param privateKey the caller's private key
     * @return result
     */
    public ResponseData<Boolean> addIssuer(
            String issuerType,
            String issuerAddress,
            String privateKey
    );
}
