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

import com.blockmap.did.core.exception.DataTypeCastException;
import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.protocol.inf.JsonSerializer;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @Author: admin@xuzhijun.com.cn
 * @Description: The base data structure to handle  DID Document info.
 * @File: ErrorCode
 * @Version: 1.0.0
 * @Date: 2019/12/15 20:03
 */
@Data
public class DidDocument implements JsonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(DidDocument.class);

    /**
     *  the serialVersionUID.
     */
    private static final long serialVersionUID = 411522771907189878L;

    /**
     * Required: The id.
     */
    private String id;

    /**
     * Required: The created.
     */
    private Long created;

    /**
     * Required: The updated.
     */
    private Long updated;

    /**
     * Required: The public key list.
     */
    private List<PublicKeyProperty> publicKey = new ArrayList<>();

    /**
     * Required: The authentication list.
     */
    private List<AuthenticationProperty> authentication = new ArrayList<>();

    /**
     * Required: The com.blockmap.did.core.service list.
     */
    private List<ServiceProperty> service = new ArrayList<>();
    
    @Override
    public String toJson() {
        return DataToolUtils.addTagFromToJson(DataToolUtils.serialize(this));
    }
   
    /**
     * create DidDocument with JSON String.
     * @param didDocumentJson the didDocument JSON String
     * @return DidDocument
     */
    public static DidDocument fromJson(String didDocumentJson) {
        if (StringUtils.isBlank(didDocumentJson)) {
            logger.error("create DidDocument with JSON String failed, "
                + "the DidDocument JSON String is null");
            throw new DataTypeCastException("the DidDocument JSON String is null.");
        }
        String didDocumentString = didDocumentJson;
        if (DataToolUtils.isValidFromToJson(didDocumentJson)) {
            didDocumentString = DataToolUtils.removeTagFromToJson(didDocumentJson);
        }
        return DataToolUtils.deserialize(didDocumentString, DidDocument.class);
    }
}
