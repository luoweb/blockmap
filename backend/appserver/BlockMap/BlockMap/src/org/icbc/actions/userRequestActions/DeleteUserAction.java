package org.icbc.actions.userRequestActions;

import org.icbc.bussinessService.IUserManageService;
import org.icbc.dataAccess.dto.UserDto;

public class DeleteUserAction {
	private long id;
	private IUserManageService userManageService;
	public String execute() {
		UserDto user = userManageService.getUser(id);
		userManageService.deleteUser(user);
		return "success";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public IUserManageService getUserManageService() {
		return userManageService;
	}
	public void setUserManageService(IUserManageService userManageService) {
		this.userManageService = userManageService;
	}
	
}
