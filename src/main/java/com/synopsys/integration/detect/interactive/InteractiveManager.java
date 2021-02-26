/**
 * synopsys-detect
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.detect.interactive;

import com.synopsys.integration.configuration.source.MapPropertySource;

public class InteractiveManager {
    private final InteractivePropertySourceBuilder propertySourceBuilder;
    private final InteractiveWriter writer;
    private final InteractiveModeDecisionTree interactiveModeDecisionTree;

    public InteractiveManager(InteractivePropertySourceBuilder propertySourceBuilder, InteractiveWriter writer, InteractiveModeDecisionTree interactiveModeDecisionTree) {
        this.propertySourceBuilder = propertySourceBuilder;
        this.writer = writer;
        this.interactiveModeDecisionTree = interactiveModeDecisionTree;
    }

    public MapPropertySource executeInteractiveMode() {
        writer.println("");
        writer.println("Interactive flag found.");
        writer.println("Starting interactive mode.");
        writer.println("");

        return getPropertySourceFromInteractiveMode(interactiveModeDecisionTree);
    }

    public MapPropertySource getPropertySourceFromInteractiveMode(DecisionTree rootDecisionTree) {
        rootDecisionTree.traverse(propertySourceBuilder, writer);

        return propertySourceBuilder.build();
    }

}
