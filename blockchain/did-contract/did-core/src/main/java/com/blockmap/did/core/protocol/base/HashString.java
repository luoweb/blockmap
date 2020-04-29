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

package com.blockmap.did.core.protocol.base;

import com.blockmap.did.core.constant.DidConstant;
import com.blockmap.did.core.protocol.inf.Hashable;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**i
 * The base data structure to represent a Hash value.
 *
 */
public class HashString implements Hashable {

    private String hash = StringUtils.EMPTY;

    public HashString(String hash) {
        setHash(hash);
    }

    @Override
    public String getHash() {
        return hash;
    }

    /**
     * Set hash value with a validity check.
     *
     * @param hash hash value
     */
    public void setHash(String hash) {
        if (StringUtils.isEmpty(hash)
            || !Pattern.compile(DidConstant.HASH_VALUE_PATTERN).matcher(hash).matches()) {
            return;
        }
        this.hash = hash;
    }
}
