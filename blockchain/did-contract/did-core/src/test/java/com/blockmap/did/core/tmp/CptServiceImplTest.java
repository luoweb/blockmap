package com.blockmap.did.core.tmp;

import com.blockmap.did.core.constant.JsonSchemaConstant;
import com.blockmap.did.core.protocol.base.Cpt;
import com.blockmap.did.core.protocol.base.CptBaseInfo;
import com.blockmap.did.core.protocol.base.DidAuthentication;
import com.blockmap.did.core.protocol.base.DidPrivateKey;
import com.blockmap.did.core.protocol.request.CptMapArgs;
import com.blockmap.did.core.protocol.request.CptStringArgs;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.service.CptService;
import com.blockmap.did.core.service.impl.CptServiceImpl;
import org.junit.Test;

import java.util.HashMap;

public class CptServiceImplTest {

    @Test
    public void registerCpt() {
        CptService cptService = new CptServiceImpl();

        String jsonSchema = "{\"properties\" : {\"name\": {\"type\": \"string\",\"description\": \"the name of certificate owner\"},\"gender\": {\"enum\": [\"F\", \"M\"],\"type\": \"string\",\"description\": \"the gender of certificate owner\"}, \"age\": {\"type\": \"number\", \"description\": \"the age of certificate owner\"}},\"required\": [\"name\", \"age\"]}";

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:blockmapid:101:0x14ecbe3132e65e6203255595d1e4e48ad22a0b80");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        CptStringArgs cptStringArgs = new CptStringArgs();
        cptStringArgs.setCptJsonSchema(jsonSchema);
        cptStringArgs.setDidAuthentication(didAuthentication);

        ResponseData<CptBaseInfo> response = cptService.registerCpt(cptStringArgs);

        System.out.println(response);
    }

    @Test
    public void registerCpt1() {
        CptService cptService = new CptServiceImpl();
        String jsonSchema = "{\"properties\" : {\"name\": {\"type\": \"string\",\"description\": \"the name of certificate owner\"},\"gender\": {\"enum\": [\"F\", \"M\"],\"type\": \"string\",\"description\": \"the gender of certificate owner\"}, \"age\": {\"type\": \"number\", \"description\": \"the age of certificate owner\"}},\"required\": [\"name\", \"age\"]}";

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("57057203537529168669894331826659936672614685324072058636870339393635375446236");

        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:blockmapid:101:0x40d1a3b5ba4e3e5e5d07691f4c354eb5c241b544");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        CptStringArgs cptStringArgs = new CptStringArgs();
        cptStringArgs.setCptJsonSchema(jsonSchema);
        cptStringArgs.setDidAuthentication(didAuthentication);

        ResponseData<CptBaseInfo> response = cptService.registerCpt(cptStringArgs, 103);

        System.out.println(response);
    }

    @Test
    public void registerCpt2() {
        CptService cptService = new CptServiceImpl();

        HashMap<String, Object> cptJsonSchema = new HashMap<String, Object>(3);
        cptJsonSchema.put(JsonSchemaConstant.TITLE_KEY, "cpt template");
        cptJsonSchema.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is a cpt template");

        HashMap<String, Object> propertitesMap1 = new HashMap<String, Object>(2);
        propertitesMap1.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap1.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is name");

        String[] genderEnum = { "F", "M" };
        HashMap<String, Object> propertitesMap2 = new HashMap<String, Object>(2);
        propertitesMap2.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap2.put(JsonSchemaConstant.DATA_TYPE_ENUM, genderEnum);

        HashMap<String, Object> propertitesMap3 = new HashMap<String, Object>(2);
        propertitesMap3.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_NUMBER);
        propertitesMap3.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is age");

        HashMap<String, Object> cptJsonSchemaKeys = new HashMap<String, Object>(3);
        cptJsonSchemaKeys.put("name", propertitesMap1);
        cptJsonSchemaKeys.put("gender", propertitesMap2);
        cptJsonSchemaKeys.put("age", propertitesMap3);
        cptJsonSchema.put(JsonSchemaConstant.PROPERTIES_KEY, cptJsonSchemaKeys);

        String[] genderRequired = { "name", "gender" };
        cptJsonSchema.put(JsonSchemaConstant.REQUIRED_KEY, genderRequired);

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        CptMapArgs cptMapArgs = new CptMapArgs();
        cptMapArgs.setCptJsonSchema(cptJsonSchema);
        cptMapArgs.setDidAuthentication(didAuthentication);

        ResponseData<CptBaseInfo> response = cptService.registerCpt(cptMapArgs);
    }

    @Test
    public void registerCpt3() {
        CptService cptService = new CptServiceImpl();

        HashMap<String, Object> cptJsonSchema = new HashMap<String, Object>(3);
        cptJsonSchema.put(JsonSchemaConstant.TITLE_KEY, "cpt template");
        cptJsonSchema.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is a cpt template");

        HashMap<String, Object> propertitesMap1 = new HashMap<String, Object>(2);
        propertitesMap1.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap1.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is name");

        String[] genderEnum = { "F", "M" };
        HashMap<String, Object> propertitesMap2 = new HashMap<String, Object>(2);
        propertitesMap2.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap2.put(JsonSchemaConstant.DATA_TYPE_ENUM, genderEnum);

        HashMap<String, Object> propertitesMap3 = new HashMap<String, Object>(2);
        propertitesMap3.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_NUMBER);
        propertitesMap3.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is age");

        HashMap<String, Object> propertitesMap4 = new HashMap<String, Object>(2);
        propertitesMap4.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap4.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is id");

        HashMap<String, Object> cptJsonSchemaKeys = new HashMap<String, Object>(3);
        cptJsonSchemaKeys.put("name", propertitesMap1);
        cptJsonSchemaKeys.put("gender", propertitesMap2);
        cptJsonSchemaKeys.put("age", propertitesMap3);
        cptJsonSchemaKeys.put("id", propertitesMap4);
        cptJsonSchema.put(JsonSchemaConstant.PROPERTIES_KEY, cptJsonSchemaKeys);

        String[] genderRequired = { "id", "name", "gender" };
        cptJsonSchema.put(JsonSchemaConstant.REQUIRED_KEY, genderRequired);

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        CptMapArgs cptMapArgs = new CptMapArgs();
        cptMapArgs.setCptJsonSchema(cptJsonSchema);
        cptMapArgs.setDidAuthentication(didAuthentication);

        ResponseData<CptBaseInfo> response = cptService.registerCpt(cptMapArgs, 101);
    }

    @Test
    public void queryCpt() {
        CptService cptService = new CptServiceImpl();
        Integer cptId = Integer.valueOf(103);
        ResponseData<Cpt> response = cptService.queryCpt(cptId);
        System.out.println(response);
    }

    @Test
    public void updateCpt() {
        CptService cptService = new CptServiceImpl();

        HashMap<String, Object> cptJsonSchema = new HashMap<String, Object>(3);
        cptJsonSchema.put(JsonSchemaConstant.TITLE_KEY, "cpt template");
        cptJsonSchema.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is a cpt template");

        HashMap<String, Object> propertitesMap1 = new HashMap<String, Object>(2);
        propertitesMap1.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap1.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is name");

        String[] genderEnum = { "F", "M" };
        HashMap<String, Object> propertitesMap2 = new HashMap<String, Object>(2);
        propertitesMap2.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap2.put(JsonSchemaConstant.DATA_TYPE_ENUM, genderEnum);

        HashMap<String, Object> propertitesMap3 = new HashMap<String, Object>(2);
        propertitesMap3.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_NUMBER);
        propertitesMap3.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is age");

        HashMap<String, Object> cptJsonSchemaKeys = new HashMap<String, Object>(3);
        cptJsonSchemaKeys.put("name", propertitesMap1);
        cptJsonSchemaKeys.put("gender", propertitesMap2);
        cptJsonSchemaKeys.put("age", propertitesMap3);
        cptJsonSchema.put(JsonSchemaConstant.PROPERTIES_KEY, cptJsonSchemaKeys);

        String[] genderRequired = { "name", "gender" };
        cptJsonSchema.put(JsonSchemaConstant.REQUIRED_KEY, genderRequired);

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        CptMapArgs cptMapArgs = new CptMapArgs();
        cptMapArgs.setCptJsonSchema(cptJsonSchema);
        cptMapArgs.setDidAuthentication(didAuthentication);

        Integer cptId = Integer.valueOf(1017);

        ResponseData<CptBaseInfo> response = cptService.updateCpt(cptMapArgs, cptId);
    }

    @Test
    public void updateCpt1() {
        CptService cptService = new CptServiceImpl();

        String jsonSchema = "{\"properties\" : {\"name\": {\"type\": \"string\",\"description\": \"the name of certificate owner\"},\"gender\": {\"enum\": [\"F\", \"M\"],\"type\": \"string\",\"description\": \"the gender of certificate owner\"}, \"age\": {\"type\": \"number\", \"description\": \"the age of certificate owner\"}},\"required\": [\"name\", \"age\"]}";

        DidPrivateKey didPrivateKey = new DidPrivateKey();
        didPrivateKey.setPrivateKey("60866441986950167911324536025850958917764441489874006048340539971987791929772");

        DidAuthentication didAuthentication = new DidAuthentication();
        didAuthentication.setDid("did:weid:101:0x39e5e6f663ef77409144014ceb063713b65600e7");
        didAuthentication.setDidPrivateKey(didPrivateKey);

        CptStringArgs cptStringArgs = new CptStringArgs();
        cptStringArgs.setCptJsonSchema(jsonSchema);
        cptStringArgs.setDidAuthentication(didAuthentication);

        Integer cptId = Integer.valueOf(1017);

        ResponseData<CptBaseInfo> response = cptService.updateCpt(cptStringArgs, cptId);
    }

    @Test
    public void queryCredentialTemplate() {
    }
}