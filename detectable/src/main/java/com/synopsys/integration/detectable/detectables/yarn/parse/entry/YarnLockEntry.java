/*
 * detectable
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.detectable.detectables.yarn.parse.entry;

import java.util.List;

import com.synopsys.integration.detectable.detectables.yarn.parse.YarnLockDependency;

public class YarnLockEntry {
    private final List<YarnLockEntryId> ids;
    private final String version;
    private final List<YarnLockDependency> dependencies;

    public YarnLockEntry(List<YarnLockEntryId> ids, String version, List<YarnLockDependency> dependencies) {
        this.ids = ids;
        this.version = version;
        this.dependencies = dependencies;
    }

    public List<YarnLockEntryId> getIds() {
        return ids;
    }

    public List<YarnLockDependency> getDependencies() {
        return dependencies;
    }

    public String getVersion() {
        return version;
    }
}