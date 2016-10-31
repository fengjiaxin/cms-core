package org.feng.cms.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.feng.basic.test.util.EntitiesHelper;
import org.feng.basic.util.EnumUtils;
import org.feng.cms.model.RoleType;

public class TestEnum {

	@Test
	public void testEnumList() {
		List<String> actuals = Arrays.asList("ROLE_ADMIN","ROLE_PUBLISH","ROLE_AUDIT");
		List<String> expectes = EnumUtils.enum2Name(RoleType.class);
		EntitiesHelper.assertObjects(expectes, actuals);
	}
	
}
