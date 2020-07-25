package org.icbc.dataAccess.dao;

import org.icbc.dataAccess.dto.ConfirmInformationPostDto;

//对确诊信息表进行CRUD
public interface IConfirmInformationPostDao {
		//根据id值查询确诊信息
		ConfirmInformationPostDto getById(Long id);
		//删除确诊信息
		void delete(ConfirmInformationPostDto confirm);
		//保存确诊信息
		void save(ConfirmInformationPostDto confirm);
		//更新确诊信息
		void update(ConfirmInformationPostDto confirm);
		//新增或更新确诊信息
		void saveOrUpdate(ConfirmInformationPostDto confirm);
}
