package org.icbc.bussinessService;

import org.icbc.dataAccess.dto.UserDto;

public interface IUserManageService {
		//增加新用户
		public void addNewUser(UserDto user);
		//删除现有用户
		public void deleteUser(UserDto user);
		//更新现有用户
		public void updateUser(UserDto user);
		//根据id值查询用户
		public UserDto getUser(long id);  
}
