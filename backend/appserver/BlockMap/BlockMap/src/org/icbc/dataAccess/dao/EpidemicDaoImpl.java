package org.icbc.dataAccess.dao;

import org.icbc.dataAccess.dto.EpidemicDto;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class EpidemicDaoImpl extends HibernateDaoSupport implements IEpidemicDao{
	
	public boolean testHibernateTemplate(){
        if(getHibernateTemplate() == null)return false;
        return true;
    }
	@Override
	public EpidemicDto getById(Long id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(EpidemicDto.class, id);
	}

	@Override
	public void delete(EpidemicDto epid) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(epid);
	}

	@Override
	public void save(EpidemicDto epid) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(epid);
	}

	@Override
	public void update(EpidemicDto epid) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(epid);
	}

	@Override
	public void saveOrUpdate(EpidemicDto epid) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(epid);
	}

}
