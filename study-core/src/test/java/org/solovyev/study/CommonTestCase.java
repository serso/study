package org.solovyev.study;

import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;


/**
 * User: serso
 * Date: 4/3/11
 * Time: 12:58 PM
 */
public abstract class CommonTestCase extends TestCase {

	@NotNull
	protected SessionFactory sessionFactory;

	@Override
	protected void setUp() throws Exception {
/*		final ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/home/serso/projects/java/study/study/study-web/web/WEB-INF/dispatcher-servlet.xml"});
//		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		sessionFactory = (SessionFactory)context.getBean("sessionFactory");*/
	}
}
