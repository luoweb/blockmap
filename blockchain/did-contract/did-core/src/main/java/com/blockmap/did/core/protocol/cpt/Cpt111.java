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

/**
 * User request issuer to sign credential.
 *
 * @author xuzj01@sdc.blockmap.com
 */
@Data
@Attributes(title = "User CPT", description = "Reserved CPT 111")
public class Cpt111 {

    @Attributes(required = true, description = "CPT ID")
    private String cptId;
    @Attributes(required = true, description = "credential Signature Request", minimum = 1)
    private String credentialSignatureRequest;
    @Attributes(required = true, description = "User Nonce")
    private String userNonce;
}
