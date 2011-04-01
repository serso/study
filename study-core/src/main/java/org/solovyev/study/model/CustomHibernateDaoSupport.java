package org.solovyev.study.model;

import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * User: serso
 * Date: 3/17/11
 * Time: 10:39 AM
 */
public class CustomHibernateDaoSupport extends HibernateDaoSupport {

    @Autowired
    public void setSessionFactoryHelper(@NotNull SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}