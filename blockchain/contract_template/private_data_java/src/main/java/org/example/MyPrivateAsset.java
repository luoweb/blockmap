/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

@DataType()
public class MyPrivateAsset {

    @Property()
    public String privateValue;

    public MyPrivateAsset(){
    }

    public String getPrivateValue() {
        return privateValue;
    }

    public void setPrivateValue(String privateValue) {
        this.privateValue = privateValue;
    }

    public String toJSONString() {
        return new JSONObject(this).toString();
    }

    public static MyPrivateAsset fromJSONString(String json) {
        String privateValue = new JSONObject(json).getString("privateValue");
        MyPrivateAsset asset = new MyPrivateAsset();
        asset.setPrivateValue(privateValue);
        return asset;
    }
}
