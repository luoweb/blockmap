package org.icbc.dataAccess.dao;

import org.icbc.dataAccess.dto.EpidemicDto;
//对疫情信息的CRUD操作
public interface IEpidemicDao {
	//根据id值查询疫情信息
	EpidemicDto getById(Long id);
	//删除疫情信息
	void delete(EpidemicDto epid);
	//保存疫情信息
	void save(EpidemicDto epid);
	//更新疫情信息
	void update(EpidemicDto epid);
	//新增或更新疫情信息
	void saveOrUpdate(EpidemicDto epid);
}
