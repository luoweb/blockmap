package org.icbc.actions.confirmRequestActions;

import java.util.Date;

import org.icbc.bussinessService.IConfirmInformationService;
import org.icbc.dataAccess.dto.ConfirmInformationPostDto;

public class AddConfirmAction {
	private String username;
    private String gender;
    private int age;
    private String email;
    private String phone;   
    private String province;
    private String city;
    private String district; 
    private String street;  
    private String community;  
    private String unit;  
    private Date confirmDate;
    private String pathPost;
    private IConfirmInformationService confirmService;
    public String execute() {
    	ConfirmInformationPostDto confirm = new ConfirmInformationPostDto();
    	confirm.setUsername(username);
    	confirm.setGender(gender);
    	confirm.setAge(age);
    	confirm.setEmail(email);
    	confirm.setPhone(phone);
    	confirm.setProvince(province);
    	confirm.setCity(city);
    	confirm.setDistrict(district);
    	confirm.setStreet(street);
    	confirm.setCommunity(community);
    	confirm.setUnit(unit);
    	confirm.setConfirmDate(confirmDate);
    	confirm.setPathPost(pathPost);
    	confirmService.addNewConfirm(confirm);
    	return "success";
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getPathPost() {
		return pathPost;
	}
	public void setPathPost(String pathPost) {
		this.pathPost = pathPost;
	}
	public IConfirmInformationService getConfirmService() {
		return confirmService;
	}
	public void setConfirmService(IConfirmInformationService confirmService) {
		this.confirmService = confirmService;
	}
    
}
