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

package com.blockmap.did.core.protocol.cpt;

import com.github.reinert.jjschema.Attributes;
import lombok.Data;

import java.util.List;

/**
 * CPT for authorization.
 *
 * @author xuzj01@sdc.blockmap.com
 */
@Data
@Attributes(title = "Authorization token", description = "Basic Authorization Token Template")
public class Cpt101 {

    @Attributes(required = true, description = "The one granting authorization")
    private String id;
    @Attributes(required = true, description = "The one receiving authorization")
    private String receiver;
    @Attributes(required = true, description = "Subjects to be authorized", minItems = 1)
    private List<String> subjects;
}
