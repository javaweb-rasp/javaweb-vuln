/*
 * Copyright sky 2018-12-19 Email:sky@03sec.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package org.javaweb.vuls.modules;

import org.javaweb.vuls.utils.RASPModulesTestUtils;
import org.junit.Test;

/**
 * @author sky
 */
public class RASPModuleTest {

	@Test
	public void enable() throws Exception {
		RASPModulesTestUtils.enableAllModule();
	}

	@Test
	public void disable() throws Exception {
		RASPModulesTestUtils.disableModule("spring");
	}

}
