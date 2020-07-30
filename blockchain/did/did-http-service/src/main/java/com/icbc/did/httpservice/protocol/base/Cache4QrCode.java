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

package com.icbc.did.httpservice.protocol.base;

import org.apache.commons.collections4.map.PassiveExpiringMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: The common input argument for all Service API.
 * @File: InputArg
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

public final class Cache4QrCode {

    /**
     * 1 存在未激活
     * 2 存在已激活
     * 3 已经被扫码
     */
    private static final Map<QrCode, Integer> CACHE = new PassiveExpiringMap<>(600000, new ConcurrentHashMap<>());

    public static boolean isExist(QrCode qrCode) {
        return CACHE.containsKey(qrCode);
    }

    public static boolean isActive(QrCode qrCode){
        return isExist(qrCode) && CACHE.get(qrCode) == 2;
    }

    public static boolean isScanned(QrCode qrCode){
        return isExist(qrCode) && CACHE.get(qrCode) == 3;
    }

    public static QrCode getQrCodeById(String id){
        for (QrCode qrCode: CACHE.keySet()) {
            if (qrCode.getId().equalsIgnoreCase(id)){
                return qrCode;
            }
        }
        return null;
    }

    /**
     * @param qrCode
     */
    public static boolean active(QrCode qrCode) {
        if (!isExist(qrCode)){
            return false;
        }
        CACHE.put(qrCode, 2);
        if (isExist(qrCode)){
            return true;
        }
        return false;
    }
    public static boolean inActive(QrCode qrCode) {
        if (!isExist(qrCode)){
            return false;
        }
        CACHE.put(qrCode, 3);
        if (isExist(qrCode)){
            return true;
        }
        return false;
    }

    public static boolean add(QrCode qrCode){
        CACHE.put(qrCode, 1);
        if (isExist(qrCode)){
            return true;
        }
        return false;
    }
}
