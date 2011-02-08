/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.db;

import junit.framework.TestCase;
import org.solovyev.common.definitions.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: Apr 5, 2010
 * Time: 10:38:42 PM
 */
public class SQLBuilderTest extends TestCase {

	public void testScreen() throws Exception {
		List<Pair<String, String>> l = new ArrayList<Pair<String, String>>();
		l.add(new Pair<String, String>("te_st%", "te\\_st\\%"));
		l.add(new Pair<String, String>("te_st%%", "te\\_st\\%\\%"));
		l.add(new Pair<String, String>("%te_st%", "\\%te\\_st\\%"));
		l.add(new Pair<String, String>("%t%e%_%s%t%", "\\%t\\%e\\%\\_\\%s\\%t\\%"));
		l.add(new Pair<String, String>("_t__e_st%", "\\_t\\_\\_e\\_st\\%"));

		String result;
		for (Pair<String, String> el : l) {
			System.out.print("Given: ");
			System.out.print(el.getFirst() + "->" + el.getSecond());
			System.out.println();
			result = SQLBuilder.screen(el.getFirst());
			System.out.println("Result: " + result);
			assertTrue(result.equals(el.getSecond()));
		}
	}
}
