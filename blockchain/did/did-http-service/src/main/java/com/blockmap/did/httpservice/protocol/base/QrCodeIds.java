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
public final class QrCodeIds {

    private static final Map<String, Boolean> CACHE = new PassiveExpiringMap<>(20000, new ConcurrentHashMap<>());

    public static boolean isExist(String id) {
        return CACHE.containsKey(id);
    }

    /**
     * @param id
     * @param status if param @id exist in @CACHE being consumed, then status = true, else status = false
     */
    public static boolean set(String id, boolean status) {
        if (!isExist(id)){
            return false;
        }
        CACHE.put(id, status);
        if (isExist(id)){
            return true;
        }
        return false;
    }

    public static boolean add(String id){
        CACHE.put(id, false);
        if (isExist(id)){
            return true;
        }
        return false;
    }

    public static boolean getStatus(String id){
        return CACHE.get(id);
    }


}
