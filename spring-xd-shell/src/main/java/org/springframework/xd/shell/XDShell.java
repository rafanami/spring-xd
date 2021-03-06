/*
 * Copyright 2013 the original author or authors.
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
 */

package org.springframework.xd.shell;

import java.net.URI;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import org.springframework.xd.rest.client.SpringXDClient;
import org.springframework.xd.rest.client.SpringXDOperations;

@Component
public class XDShell implements CommandMarker {

	private String target;

	private SpringXDOperations springXDOperations;

	public String getTarget() {
		return target;
	}

	public XDShell() {
		target("http://localhost:8080");
	}

	@CliCommand(value = { "target" }, help = "Select the XD admin server to use")
	public String target(@CliOption(mandatory = true, key = "") String target) {
		try {
			springXDOperations = new SpringXDClient(URI.create(target));
			this.target = target;
			return String.format("Successfully targeted %s", target);
		} catch (Exception e) {
			this.target = "unknown";
			springXDOperations = null;
			return String.format("Unable to contact XD Admin at %s", target);
		}
	}

	public SpringXDOperations getSpringXDOperations() {
		return springXDOperations;
	}

}
