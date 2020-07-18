package org.icbc.actions.userRequestActions;

import org.icbc.bussinessService.IUserManageService;
import org.icbc.dataAccess.dto.UserDto;

public class UpdateUserAction {
	private long id;
	private String name;
	private String password;
	private String address;
	private IUserManageService userManageService;
	public String execute() {
		UserDto user = userManageService.getUser(id);
		if(user==null) {
			return "fail";
		}else {
			user.setName(name);
			user.setPassword(password);
			user.setAdress(address);
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public IUserManageService getUserManageService() {
		return userManageService;
	}
	public void setUserManageService(IUserManageService userManageService) {
		this.userManageService = userManageService;
	}
	
}
