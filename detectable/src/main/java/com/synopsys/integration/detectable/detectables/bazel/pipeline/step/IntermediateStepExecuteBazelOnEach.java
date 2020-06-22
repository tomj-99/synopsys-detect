/**
 * detectable
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.detectable.detectables.bazel.pipeline.step;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synopsys.integration.exception.IntegrationException;

public class IntermediateStepExecuteBazelOnEach implements IntermediateStep {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BazelCommandExecutor bazelCommandExecutor;
    private final BazelVariableSubstitutor bazelVariableSubstitutor;
    private final List<String> bazelCommandArgs;
    private final boolean inputIsExpected;

    public IntermediateStepExecuteBazelOnEach(BazelCommandExecutor bazelCommandExecutor,
        BazelVariableSubstitutor bazelVariableSubstitutor, List<String> bazelCommandArgs, boolean inputIsExpected) {
        this.bazelCommandExecutor = bazelCommandExecutor;
        this.bazelVariableSubstitutor = bazelVariableSubstitutor;
        this.bazelCommandArgs = bazelCommandArgs;
        this.inputIsExpected = inputIsExpected;
    }

    @Override
    public List<String> process(List<String> input) throws IntegrationException {
        List<String> results = new ArrayList<>();
        if (inputIsExpected && input.isEmpty()) {
            return results;
        }
        List<String> adjustedInput;
        if (input.isEmpty()) {
            adjustedInput = new ArrayList<>(1);
            adjustedInput.add(null);
        } else {
            adjustedInput = input;
        }
        for (String inputItem : adjustedInput) {
            List<String> finalizedArgs = bazelVariableSubstitutor.substitute(bazelCommandArgs, inputItem);
            Optional<String> cmdOutput = bazelCommandExecutor.executeToString(finalizedArgs);
            if (cmdOutput.isPresent()) {
                results.add(cmdOutput.get());
            }
        }
        return results;
    }
}
