/*
 *       Copyright© (2019) blockmap Co., Ltd.
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

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * An EndpointInfo contains a requestName with an N:1 relationship - N in, 1 requestName. SDK
 * endpoint handler will handle Endpoint related services rerouting based this stub.
 *
 */
@Data
public class EndpointInfo {

    private String requestName;
    private List<String> inAddr = new ArrayList<>();
    private String description;
}
