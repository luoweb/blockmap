package org.icbc.actions.userRequestActions;

import org.icbc.bussinessService.IUserManageService;
import org.icbc.dataAccess.dto.UserDto;

public class QueryUserAction {
	private long id;
	private IUserManageService userManageService;
	public String execute() {
		UserDto user = userManageService.getUser(id);
		if(user !=null) {
			return "success";
		}else {
			return "fail";
		}		
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
