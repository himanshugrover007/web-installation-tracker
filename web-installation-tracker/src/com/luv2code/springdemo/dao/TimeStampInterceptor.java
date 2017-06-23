package com.luv2code.springdemo.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class TimeStampInterceptor extends EmptyInterceptor {

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, 
            Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof TimeStamped) {
            int indexOf = com.mchange.v1.util.ArrayUtils.indexOf(propertyNames, "lastUpdatedDate");
            currentState[indexOf] = new Date();
            return true;
        }
        return false;
    }

    public boolean onSave(Object entity, Serializable id, Object[] state, 
            String[] propertyNames, Type[] types) {
            if (entity instanceof TimeStamped) {
                int indexOf =  com.mchange.v1.util.ArrayUtils.indexOf(propertyNames, "createdDate");
                state[indexOf] = new Date();
                indexOf =  com.mchange.v1.util.ArrayUtils.indexOf(propertyNames, "lastUpdatedDate");
                state[indexOf] = new Date();
                return true;
            }
            return false;
    }
}