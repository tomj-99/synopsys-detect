/**
 * detectable
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.detectable.detectables.git.parsing.model;

import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

public class GitConfigNode {
    private final String type;
    @Nullable
    private final String name;
    private final Map<String, String> properties;

    public GitConfigNode(final String type, final Map<String, String> properties) {
        this(type, null, properties);
    }

    public GitConfigNode(final String type, @Nullable final String name, final Map<String, String> properties) {
        this.type = type;
        this.name = name;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getProperty(final String propertyKey) {
        return Optional.ofNullable(properties.get(propertyKey));
    }
}
