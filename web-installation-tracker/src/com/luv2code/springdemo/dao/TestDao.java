
package com.luv2code.springdemo.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Test;

@Repository("testDao")
public class TestDao extends Dao<Test> {

    public TestDao() {
        super(Test.class);
    }
    
    
    public List<Test> rangeOrderFirstUnarchivedOnly(int begin, int end, boolean asc) {
        Criteria c = getCurrentSession().createCriteria(Test.class).add(Restrictions.eq("archived","n"));
        return findRangeOrderFirst(c, begin, end, "name", asc);
    }
    
    public List<Test> findPageArchivedOnlyOrderFirst(int pageNumber, int pageSize) {
        Criteria c = getCurrentSession().createCriteria(Test.class).add(Restrictions.eq("archived","y"));
        return findPageOrderFirst(c, pageNumber, pageSize, "name", true);
    }
}
