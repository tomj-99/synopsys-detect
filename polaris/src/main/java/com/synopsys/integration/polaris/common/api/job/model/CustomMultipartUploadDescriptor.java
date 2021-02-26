/**
 * polaris
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.polaris.common.api.job.model;

import com.google.gson.annotations.SerializedName;

// this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created

public class CustomMultipartUploadDescriptor extends S3UploadDescriptor {
    @SerializedName("multiPartSignerUrl")
    private String multiPartSignerUrl;

    /**
     * Get multiPartSignerUrl
     * @return multiPartSignerUrl
     */
    public String getMultiPartSignerUrl() {
        return multiPartSignerUrl;
    }

}
