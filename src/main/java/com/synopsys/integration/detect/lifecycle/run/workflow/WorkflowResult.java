/**
 * synopsys-detect
 *
 * Copyright (c) 2021 Synopsys, Inc.
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
package com.synopsys.integration.detect.lifecycle.run.workflow;

import java.util.List;
import java.util.Optional;

import com.synopsys.integration.detect.lifecycle.run.EventRequest;

public class WorkflowResult {
    private final boolean success;
    private final Exception exception;
    private final List<EventRequest> eventRequests;

    public static WorkflowResult success(List<EventRequest> eventRequests) {
        return new WorkflowResult(true, null, eventRequests);
    }

    public static WorkflowResult fail(Exception ex, List<EventRequest> eventRequests) {
        return new WorkflowResult(false, ex, eventRequests);
    }

    private WorkflowResult(boolean success, Exception exception, List<EventRequest> eventRequests) {
        this.success = success;
        this.exception = exception;
        this.eventRequests = eventRequests;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean hasFailed() {
        return !success;
    }

    public Optional<Exception> getException() {
        return Optional.ofNullable(exception);
    }

    public List<EventRequest> getEventRequests() {
        return eventRequests;
    }
}
