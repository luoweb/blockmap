package org.icbc.dataAccess.dao;

import java.util.List;

import org.icbc.dataAccess.dto.SubinstitutionDto;


public interface ISubinstitutionDao {
	//根据id值查询机构信息
	SubinstitutionDto getById(Long id);
	//删除机构信息
	void delete(SubinstitutionDto subinstitution);
	//保存机构信息
	void save(SubinstitutionDto subinstitution);
	//更新机构信息
	void update(SubinstitutionDto subinstitution);
	//新增或更新机构信息
	void saveOrUpdate(SubinstitutionDto subinstitution);
	//查找可复工的机构
	List<SubinstitutionDto> selectWorkSubinstitution(int offset, int limit);  
}
