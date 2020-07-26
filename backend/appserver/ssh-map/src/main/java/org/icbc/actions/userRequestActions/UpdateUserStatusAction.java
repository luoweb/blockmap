package org.icbc.actions.userRequestActions;

import org.icbc.bussinessService.IUserManageService;
import org.icbc.dataAccess.dto.UserDto;

public class UpdateUserStatusAction {
	private long id;
	private int status;
	private IUserManageService userManageService;
	public String execute() {
		UserDto user=userManageService.getUser(id);
		if(user==null) {
			return "fail";
		}else {
			user.setStatus(status);
			userManageService.updateUser(user);
			return "success";
		}			
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public IUserManageService getUserManageService() {
		return userManageService;
	}
	public void setUserManageService(IUserManageService userManageService) {
		this.userManageService = userManageService;
	}
	
}
