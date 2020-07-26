package org.icbc.bussinessService;

import org.icbc.dataAccess.dto.ConfirmInformationPostDto;

public interface IConfirmInformationService {
	//增加确诊信息
	public void addNewConfirm(ConfirmInformationPostDto confirm);
	//删除确诊信息
	public void deleteConfirm(ConfirmInformationPostDto confirm);
	//更新确诊信息
	public void updateConfirm(ConfirmInformationPostDto confirm);
	//根据id值查询确诊信息
	public ConfirmInformationPostDto getConfirm(long id);
}
