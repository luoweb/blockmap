/*
 *       Copyright© (2018) blockmap Co., Ltd.
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

package com.blockmap.did.core.suite.persistence.sql.driver;

import com.blockmap.did.core.constant.DataDriverConstant;
import com.blockmap.did.core.constant.ErrorCode;
import com.blockmap.did.core.exception.DidBaseException;
import com.blockmap.did.core.protocol.response.ResponseData;
import com.blockmap.did.core.suite.api.persistence.Persistence;
import com.blockmap.did.core.suite.persistence.sql.DefaultTable;
import com.blockmap.did.core.suite.persistence.sql.SqlDomain;
import com.blockmap.did.core.suite.persistence.sql.SqlExecutor;
import com.blockmap.did.core.util.DataToolUtils;
import com.blockmap.did.core.util.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * mysql operations.
 *
 * @author admin@xuzhijun.com.cn 2019年3月18日
 */
public class MysqlDriver implements Persistence {

    private static final Logger logger = LoggerFactory.getLogger(MysqlDriver.class);

    private static final String CHECK_TABLE_SQL =
            "SELECT table_name "
                    + DataDriverConstant.SQL_COLUMN_DATA
                    + " FROM information_schema.TABLES WHERE table_name ='$1'";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE `$1` ("
                    + "`id` varchar(128) NOT NULL COMMENT 'primary key',"
                    + "`data` blob DEFAULT NULL COMMENT 'the save data', "
                    + "`created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'created', "
                    + "`updated` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'updated', "
                    + "`protocol` varchar(32) DEFAULT NULL COMMENT 'protocol', "
                    + "`expire` datetime DEFAULT NULL COMMENT 'the expire time', "
                    + "`version` varchar(10) DEFAULT NULL COMMENT 'the data version', "
                    + "`ext1` int DEFAULT NULL COMMENT 'extend field1', "
                    + "`ext2` int DEFAULT NULL COMMENT 'extend field2', "
                    + "`ext3` varchar(500) DEFAULT NULL COMMENT 'extend field3', "
                    + "`ext4` varchar(500) DEFAULT NULL COMMENT 'extend field4', "
                    + "PRIMARY KEY (`id`) "
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='the data table'";

    private static final Integer FAILED_STATUS = DataDriverConstant.SQL_EXECUTE_FAILED_STATUS;

    private static final ErrorCode KEY_INVALID = ErrorCode.PRESISTENCE_DATA_KEY_INVALID;

    private static Boolean isinit = false;

    /**
     * the Constructor and init all domain.
     */
    public MysqlDriver() {
        if (!isinit) {
            synchronized (MysqlDriver.class) {
                if (!isinit) {
                    initDomain();
                    isinit = true;
                }
            }
        }
    }

    @Override
    public ResponseData<String> get(String domain, String id) {

        if (StringUtils.isEmpty(id)) {
            logger.error("[mysql->get] the id of the data is empty.");
            return new ResponseData<String>(StringUtils.EMPTY, KEY_INVALID);
        }
        String dataKey = DataToolUtils.getHash(id);
        try {
            ResponseData<String> result = new ResponseData<String>();
            result.setResult(StringUtils.EMPTY);
            SqlDomain sqlDomain = new SqlDomain(domain);
            ResponseData<Map<String, String>> response = new SqlExecutor(sqlDomain)
                    .executeQuery(SqlExecutor.SQL_QUERY, dataKey);
            if (response.getErrorCode().intValue() == ErrorCode.SUCCESS.getCode()
                    && response.getResult() != null) {
                DefaultTable tableData = DataToolUtils.deserialize(
                        DataToolUtils.serialize(response.getResult()), DefaultTable.class);
                if (tableData.getExpire() != null && tableData.getExpire().before(new Date())) {
                    logger.error("[mysql->get] the data is expire.");
                    return new ResponseData<String>(StringUtils.EMPTY, ErrorCode.SQL_DATA_EXPIRE);
                }
                if (StringUtils.isNotBlank(tableData.getData())) {
                    result.setResult(
                            new String(
                                    tableData.getData().getBytes(StandardCharsets.ISO_8859_1),
                                    StandardCharsets.UTF_8
                            )
                    );
                }
            }
            result.setErrorCode(ErrorCode.getTypeByErrorCode(response.getErrorCode()));
            return result;
        } catch (DidBaseException e) {
            logger.error("[mysql->get] get the data error.", e);
            return new ResponseData<String>(StringUtils.EMPTY, e.getErrorCode());
        }
    }

    @Override
    public ResponseData<Object> getAll(String domain, Object... ext) {
        try {
            ResponseData<Object> result = new ResponseData<Object>();
            result.setResult(StringUtils.EMPTY);
            SqlDomain sqlDomain = new SqlDomain(domain);
            ResponseData<List<Map<String, String>>> response = new SqlExecutor(sqlDomain)
                    .executeQuery2(SqlExecutor.SQL_QUERY_ALL, ext);
            if (response.getErrorCode().intValue() == ErrorCode.SUCCESS.getCode()
                    && response.getResult() != null) {
                List<String> dataList = new ArrayList<>();
                for (Map<String, String> dataMap : response.getResult()) {
                    DefaultTable tableData = DataToolUtils.deserialize(
                            DataToolUtils.serialize(dataMap), DefaultTable.class);
//                    if (tableData.getExpire() != null && tableData.getExpire().before(new Date())) {
//                        logger.error("[mysql->get] the data is expire.");
//                        return new ResponseData<Object>(StringUtils.EMPTY, ErrorCode.SQL_DATA_EXPIRE);
//                    }
                    if (StringUtils.isNotBlank(tableData.getData())) {
                        dataList.add(new String(
                                tableData.getData().getBytes(StandardCharsets.ISO_8859_1),
                                StandardCharsets.UTF_8
                        ));
                    }
                }
                result.setResult(dataList);
            }
            result.setErrorCode(ErrorCode.getTypeByErrorCode(response.getErrorCode()));
            return result;
        } catch (DidBaseException e) {
            logger.error("[mysql->get] get the data error.", e);
            return new ResponseData<Object>(StringUtils.EMPTY, e.getErrorCode());
        }
    }



    @Override
    public ResponseData<Integer> save(String domain, String id, String data, String ext3) {

        if (StringUtils.isEmpty(id)) {
            logger.error("[mysql->save] the id of the data is empty.");
            return new ResponseData<Integer>(FAILED_STATUS, KEY_INVALID);
        }
        String dataKey = DataToolUtils.getHash(id);
        try {
            SqlDomain sqlDomain = new SqlDomain(domain);
            Object[] datas = {dataKey, data, sqlDomain.getExpire(), ext3};
            return new SqlExecutor(sqlDomain).execute(SqlExecutor.SQL_SAVE, datas);
        } catch (DidBaseException e) {
            logger.error("[mysql->save] save the data error.", e);
            return new ResponseData<Integer>(FAILED_STATUS, e.getErrorCode());
        }
    }


    @Override
    public ResponseData<Integer> batchSave(String domain, List<String> ids, List<String> dataList) {
        try {
            List<Object> idHashList = new ArrayList<>();
            for (String id : ids) {
                if (StringUtils.isEmpty(id)) {
                    logger.error("[mysql->batchSave] the id of the data is empty.");
                    return new ResponseData<Integer>(FAILED_STATUS, KEY_INVALID);
                }
                idHashList.add(DataToolUtils.getHash(id));
            }
            SqlDomain sqlDomain = new SqlDomain(domain);
            Date[] dates = new Date[ids.size()];
            Arrays.fill(dates, sqlDomain.getExpire());
            List<Object> timeoutList = new ArrayList<>();
            timeoutList.addAll(Arrays.asList(dates));

            List<List<Object>> dataLists = new ArrayList<List<Object>>();
            dataLists.add(idHashList);
            dataLists.add(Arrays.asList(dataList.toArray()));
            dataLists.add(timeoutList);
            return new SqlExecutor(sqlDomain).batchSave(SqlExecutor.SQL_SAVE, dataLists);
        } catch (DidBaseException e) {
            logger.error("[mysql->batchSave] batchSave the data error.", e);
            return new ResponseData<Integer>(FAILED_STATUS, e.getErrorCode());
        }
    }


    @Override
    public ResponseData<Integer> delete(String domain, String id) {

        if (StringUtils.isEmpty(id)) {
            logger.error("[mysql->delete] the id of the data is empty.");
            return new ResponseData<Integer>(FAILED_STATUS, KEY_INVALID);
        }
        String dataKey = DataToolUtils.getHash(id);
        try {
            SqlDomain sqlDomain = new SqlDomain(domain);
            return new SqlExecutor(sqlDomain).execute(SqlExecutor.SQL_DELETE, dataKey);
        } catch (DidBaseException e) {
            logger.error("[mysql->delete] delete the data error.", e);
            return new ResponseData<Integer>(FAILED_STATUS, e.getErrorCode());
        }
    }


    @Override
    public ResponseData<Integer> update(String domain, String id, String data) {

        if (StringUtils.isEmpty(id)) {
            logger.error("[mysql->update] the id of the data is empty.");
            return new ResponseData<Integer>(FAILED_STATUS, KEY_INVALID);
        }
        String dataKey = DataToolUtils.getHash(id);
        Date date = new Date();
        try {
            SqlDomain sqlDomain = new SqlDomain(domain);
            Object[] datas = {date, data, sqlDomain.getExpire(), dataKey};
            return new SqlExecutor(sqlDomain).execute(SqlExecutor.SQL_UPDATE, datas);
        } catch (DidBaseException e) {
            logger.error("[mysql->update] update the data error.", e);
            return new ResponseData<Integer>(FAILED_STATUS, e.getErrorCode());
        }
    }

    /**
     * 初始化domain.
     */
    private void initDomain() {
        Set<String> domainKeySet = analyzeDomainValue();
        for (String domainKey : domainKeySet) {
            SqlExecutor sqlExecutor = new SqlExecutor(new SqlDomain(domainKey));
            sqlExecutor.resolveTableDomain(CHECK_TABLE_SQL, CREATE_TABLE_SQL);
        }
    }

    /**
     * 分析配置中的domain配置, 并且获取对应的配置项key.
     *
     * @return 返回配置值
     */
    private Set<String> analyzeDomainValue() {
        Set<Object> keySet = PropertyUtils.getAllPropertyKey();
        Set<String> domainKeySet = new HashSet<>();
        for (Object object : keySet) {
            String key = String.valueOf(object);
            if (key.indexOf(SqlDomain.KEY_SPLIT_CHAR) == key.lastIndexOf(SqlDomain.KEY_SPLIT_CHAR)
                    && key.startsWith(SqlDomain.PREFIX)) {
                domainKeySet.add(key);
            }
        }
        return domainKeySet;
    }

    @Override
    public ResponseData<Integer> saveOrUpdate(String domain, String id, String data) {
        ResponseData<String> getRes = this.get(domain, id);
        //如果查询数据存在，或者失效 则进行更新 否则进行新增
        if ((StringUtils.isNotBlank(getRes.getResult())
                && getRes.getErrorCode().intValue() == ErrorCode.SUCCESS.getCode())
                || getRes.getErrorCode().intValue() == ErrorCode.SQL_DATA_EXPIRE.getCode()) {
            return this.update(domain, id, data);
        }
        return this.save(domain, id, data, null);
    }
}
