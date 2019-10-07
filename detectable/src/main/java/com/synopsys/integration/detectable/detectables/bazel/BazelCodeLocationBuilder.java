/**
 * detectable
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.detectable.detectables.bazel;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synopsys.integration.bdio.graph.MutableDependencyGraph;
import com.synopsys.integration.bdio.graph.MutableMapDependencyGraph;
import com.synopsys.integration.bdio.model.dependency.Dependency;
import com.synopsys.integration.bdio.model.externalid.ExternalId;
import com.synopsys.integration.bdio.model.externalid.ExternalIdFactory;
import com.synopsys.integration.detectable.detectable.codelocation.CodeLocation;

public class BazelCodeLocationBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExternalIdFactory externalIdFactory;
    private final MutableDependencyGraph dependencyGraph;

    public BazelCodeLocationBuilder(final ExternalIdFactory externalIdFactory) {
        this.externalIdFactory = externalIdFactory;
        dependencyGraph = new MutableMapDependencyGraph();
    }

    public BazelCodeLocationBuilder addDependency(final String group, final String artifact, final String version) {
        try {
            logger.debug(String.format("Adding dependency from external id: %s:%s:%s", group, artifact, version));
            final ExternalId externalId = externalIdFactory.createMavenExternalId(group, artifact, version);
            final Dependency artifactDependency = new Dependency(artifact, version, externalId);
            dependencyGraph.addChildToRoot(artifactDependency);
        } catch (final Exception e) {
            logger.error(String.format("Unable to create dependency from %s:%s:%s", group, artifact, version));
        }
        return this;
    }

    public List<CodeLocation> build() {
        final CodeLocation codeLocation = new CodeLocation(dependencyGraph);
        final List<CodeLocation> codeLocations = new ArrayList<>(1);
        codeLocations.add(codeLocation);
        return codeLocations;
    }
}
