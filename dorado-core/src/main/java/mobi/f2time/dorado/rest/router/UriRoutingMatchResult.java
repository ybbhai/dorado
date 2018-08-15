/*
 * Copyright 2017 The OpenDSP Project
 *
 * The OpenDSP Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package mobi.f2time.dorado.rest.router;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 * 
 * @author wangwp
 */
public class UriRoutingMatchResult {
	private final UriRoutingController controller;
	private final String[] pathVariables;
	private final String httpMethod;

	private UriRoutingMatchResult(UriRoutingController controller, MatchResult matchResult, String httpMethod) {
		this.httpMethod = httpMethod;
		this.controller = controller;
		pathVariables = new String[matchResult.groupCount()];

		for (int i = 0; i < pathVariables.length; i++) {
			pathVariables[i] = matchResult.group(i + 1);
		}
	}

	public static UriRoutingMatchResult create(UriRoutingController controller, Matcher matchResult,
			String httpMethod) {
		return new UriRoutingMatchResult(controller, matchResult, httpMethod);
	}

	public UriRoutingController controller() {
		return this.controller;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String pathVariable(int index) {
		if (index <= 0 || index > pathVariables.length) {
			throw new IllegalArgumentException("");
		}
		return pathVariables[index - 1];
	}

	public String[] pathVariables() {
		return this.pathVariables;
	}
}