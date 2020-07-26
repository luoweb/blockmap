package org.icbc.bussinessService;

import java.util.List;

import org.icbc.dataAccess.dto.SubinstitutionDto;

public interface ISubinstitutionService {
		//增加机构信息
		public void addNewSubinstitution(SubinstitutionDto subinstitution);
		//删除机构信息
		public void deleteSubinstitution(SubinstitutionDto subinstitution);
		//更新机构信息
		public void updateSubinstitution(SubinstitutionDto subinstitution);
		//根据id值查询机构信息
		public SubinstitutionDto getSubinstitution(long id);
		//查询机构复工情况
		public List<SubinstitutionDto> selectWorkSubinstitution(int page, int limit);
}
