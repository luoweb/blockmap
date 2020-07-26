package org.icbc.dataAccess.dao;

import org.icbc.dataAccess.dto.UserDto;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements IUserDao{

	
	public boolean testHibernateTemplate(){
        if(getHibernateTemplate() == null)return false;
        return true;
    }
	@Override
	public UserDto getById(Long id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(UserDto.class, id);
	}

	@Override
	public void delete(UserDto user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(user);
	}

	@Override
	public void save(UserDto user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(user);
	}

	@Override
	public void update(UserDto user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(user);
	}

	@Override
	public void saveOrUpdate(UserDto user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(user);
	}

}
