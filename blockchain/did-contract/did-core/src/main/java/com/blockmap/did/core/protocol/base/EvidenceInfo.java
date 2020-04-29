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

import lombok.Data;

import java.util.List;

/**
 * The base data structure to handle Credential EvidenceInfo info.
 *
 */
@Data
public class EvidenceInfo {

    /**
     * Required: The full Credential hash.
     */
    private String credentialHash;

    /**
     * Required: The signers of this Credential.
     */
    private List<String> signers;

    /**
     * Required: The signatures of each signers with the same order. In JavaBean object, the
     * signatures will be encoded in Base64. On the blockchain, the signatures will be stored in its
     * r, s, v.
     */
    private List<String> signatures;
}
