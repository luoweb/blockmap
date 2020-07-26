package org.icbc.actions.userRequestActions;

import org.icbc.bussinessService.IUserManageService;
import org.icbc.dataAccess.dto.UserDto;

public class RegisterNewUserAction {
	private String username;
    private String password;
    private String email;
    private String phone;   
    private int institution;       
    private int type;    
    private String gender;  
    private int age;
    private int status;   
    private String province;
    private String city;
    private String district; 
    private String street;  
    private String community;  
    private String unit;  
    private float lon;   
    private float lat;
	private IUserManageService userManageService;
	public String execute() {
		if(username==null || password==null) {
			return "fail";
		}else {
			UserDto user = new UserDto();
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhone(phone);
			user.setInstitution(institution);
			user.setType(type);
			user.setGender(gender);
			user.setAge(age);
			user.setStatus(status);
			user.setProvince(province);
			user.setCity(city);
			user.setDistrict(district);
			user.setStreet(street);
			user.setCommunity(community);
			user.setUnit(unit);
			user.setLon(lon);
			user.setLat(lat);
			userManageService.addNewUser(user);
			return "suceess";
		}		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getInstitution() {
		return institution;
	}
	public void setInstitution(int institution) {
		this.institution = institution;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public IUserManageService getUserManageService() {
		return userManageService;
	}
	public void setUserManageService(IUserManageService userManageService) {
		this.userManageService = userManageService;
	}
	
}
