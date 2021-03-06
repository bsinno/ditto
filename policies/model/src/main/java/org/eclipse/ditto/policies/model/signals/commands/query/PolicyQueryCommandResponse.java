/*
 * Copyright (c) 2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.policies.model.signals.commands.query;

import org.eclipse.ditto.json.JsonValue;
import org.eclipse.ditto.base.model.headers.DittoHeaders;
import org.eclipse.ditto.base.model.signals.commands.WithEntity;
import org.eclipse.ditto.policies.model.signals.commands.PolicyCommandResponse;


/**
 * Aggregates all PolicyCommandResponses which query state of a {@link org.eclipse.ditto.policies.model.Policy}.
 *
 * @param <T> the type of the implementing class.
 */
public interface PolicyQueryCommandResponse<T extends PolicyQueryCommandResponse<T>>
        extends PolicyCommandResponse<T>, WithEntity<T> {

    @Override
    T setEntity(JsonValue entity);

    @Override
    T setDittoHeaders(DittoHeaders dittoHeaders);

}
