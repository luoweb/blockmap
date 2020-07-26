package org.icbc.dataAccess.dao;

import org.icbc.dataAccess.dto.UserDto;
//对User数据表的CRUD
public interface IUserDao {
	//根据id值查询用户信息
	UserDto getById(Long id);
	//删除用户信息
	void delete(UserDto user);
	//保存用户信息
	void save(UserDto user);
	//更新用户信息
	void update(UserDto user);
	//新增或更新用户信息
	void saveOrUpdate(UserDto user);
}
