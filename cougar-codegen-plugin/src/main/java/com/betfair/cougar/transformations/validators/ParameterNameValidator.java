/*
 * Copyright 2013, The Sporting Exchange Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.betfair.cougar.transformations.validators;

import org.w3c.dom.Node;

import com.betfair.cougar.codegen.ValidationException;

public class ParameterNameValidator extends AbstractValidator{
	@Override
	public boolean nodeMustExist() {
		return false;
	}

	@Override
	public String getName() {
		return "Parameter Name Validator";
	}

	@Override
	public String getXPath() {
		return "//parameter";
	}

	@Override
	public void validate(Node node) throws ValidationException {
	    if (node.getParentNode().getLocalName().equals("interface")) {
    		// Check that the data type name starts with a upper case letter
             String name = getAttribute(getName(), node, "name");
             if (name == null || name.length() < 1) {
            	 throw new ValidationException("Parameters must have a name", node);
             } else if (Character.isUpperCase(name.charAt(0))) {
            	 throw new ValidationException("Parameters must start with a lower case letter", node);
             }
	    }
	}
}
