package org.icbc.actions.userRequestActions;

import org.icbc.bussinessService.IUserManageService;
import org.icbc.dataAccess.dto.UserDto;

public class RegisterNewUserAction {
	private String name;
	private String password;
	private String address;
	private IUserManageService userManageService;
	public String execute() {
		if(name==null || password==null) {
			return "fail";
		}else {
			UserDto user = new UserDto();
			user.setName(name);
			user.setPassword(password);
			user.setAdress(address);
			userManageService.addNewUser(user);
			return "suceess";
		}		
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
