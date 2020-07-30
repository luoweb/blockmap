package org.icbc.bussinessService;

import org.icbc.dataAccess.dao.IUserDao;
import org.icbc.dataAccess.dto.UserDto;

public class UserManageServiceImpl implements IUserManageService{
	private IUserDao userDao;
	@Override
	public void addNewUser(UserDto user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public void deleteUser(UserDto user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	@Override
	public void updateUser(UserDto user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	@Override
	public UserDto getUser(long id) {
		// TODO Auto-generated method stub
		return userDao.getById(id);
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	
}
