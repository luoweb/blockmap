package org.icbc.dataAccess.dao;

import java.util.List;

import org.icbc.dataAccess.dto.SubinstitutionDto;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SubinstitutionDaoImpl extends HibernateDaoSupport implements ISubinstitutionDao{
	
	public boolean testHibernateTemplate(){
        if(getHibernateTemplate() == null)return false;
        return true;
    }
	@Override
	public SubinstitutionDto getById(Long id) {
		// TODO Auto-generated method stub
		return (SubinstitutionDto) getHibernateTemplate().get(SubinstitutionDto.class, id);
	}

	@Override
	public void delete(SubinstitutionDto subinstitution) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(subinstitution);
	}

	@Override
	public void save(SubinstitutionDto subinstitution) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(subinstitution);
	}

	@Override
	public void update(SubinstitutionDto subinstitution) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(subinstitution);
	}

	@Override
	public void saveOrUpdate(SubinstitutionDto subinstitution) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(subinstitution);
	}
	@Override
	public List<SubinstitutionDto> selectWorkSubinstitution(int page, int limit) {
		// TODO Auto-generated method stub
		return (List<SubinstitutionDto>)getHibernateTemplate().find("from SubinstitutionDto as p where p.riskLevel <= 1");
	}

}
