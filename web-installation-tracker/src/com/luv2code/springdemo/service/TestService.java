
package com.luv2code.springdemo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.TestDao;
import com.luv2code.springdemo.entity.Test;

@Service("testService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TestService {
    
    @Autowired
    private TestDao testDao;
    
    public List<Test> list() {
        return testDao.findAll();
    }
    
    public List<Test> range(int beginIndex, int endIndex) {
        return testDao.findRange(beginIndex, endIndex);
    }
    
    public List<Test> rangeOrderFirstAsc(int beginIndex, int endIndex) {
        return testDao.findRangeOrderFirst(beginIndex, endIndex, "name", true);
    }
    
    public List<Test> rangeOrderFirstDesc(int beginIndex, int endIndex) {
        return testDao.findRangeOrderFirst(beginIndex, endIndex, "name", false);
    }
    
    public List<Test> rangeOrderFirstAscUnarchivedOnly(int beginIndex, int endIndex) {
        return testDao.rangeOrderFirstUnarchivedOnly(beginIndex, endIndex, true);
    }
    
    public List<Test> pageByPageSize(int pageNumber, int pageSize) {
        return testDao.findPage(pageNumber, pageSize);
    }
    
    public List<Test> pageByPageSizeOrderAsc(int pageNumber, int pageSize) {
        return testDao.findPageOrderFirst(pageNumber, pageSize, "name", true);
    }
    
    public List<Test> pageByPageSizeOrderDesc(int pageNumber, int pageSize) {
        return testDao.findPageOrderFirst(pageNumber, pageSize, "name", false);
    }
    
    public List<Test> findPageArchivedOnlyOrderFirst(int pageNumber, int pageSize) {
        return testDao.findPageArchivedOnlyOrderFirst(pageNumber, pageSize);
    }
   
}
