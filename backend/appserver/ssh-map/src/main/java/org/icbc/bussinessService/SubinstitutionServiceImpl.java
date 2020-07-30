package org.icbc.bussinessService;

import java.util.List;

import org.icbc.dataAccess.dao.ISubinstitutionDao;
import org.icbc.dataAccess.dto.SubinstitutionDto;

public class SubinstitutionServiceImpl implements ISubinstitutionService{
	private ISubinstitutionDao subinstitutionDao;
	@Override
	public void addNewSubinstitution(SubinstitutionDto subinstitution) {
		// TODO Auto-generated method stub
		subinstitutionDao.save(subinstitution);
	}

	@Override
	public void deleteSubinstitution(SubinstitutionDto subinstitution) {
		// TODO Auto-generated method stub
		subinstitutionDao.delete(subinstitution);
	}

	@Override
	public void updateSubinstitution(SubinstitutionDto subinstitution) {
		// TODO Auto-generated method stub
		subinstitutionDao.update(subinstitution);
	}

	@Override
	public SubinstitutionDto getSubinstitution(long id) {
		// TODO Auto-generated method stub
		return subinstitutionDao.getById(id);
	}

	@Override
	public List<SubinstitutionDto> selectWorkSubinstitution(int page, int limit) {
		// TODO Auto-generated method stub
		int offset=(page-1)*limit;
		return subinstitutionDao.selectWorkSubinstitution(offset, limit);
	}

	public ISubinstitutionDao getSubinstitutionDao() {
		return subinstitutionDao;
	}

	public void setSubinstitutionDao(ISubinstitutionDao subinstitutionDao) {
		this.subinstitutionDao = subinstitutionDao;
	}
	
}
