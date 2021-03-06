/*
 * Copyright (c) 2020 Contributors to the Eclipse Foundation
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
package org.eclipse.ditto.protocol.adapter.provider;

import org.eclipse.ditto.protocol.adapter.Adapter;
import org.eclipse.ditto.base.model.signals.Signal;
import org.eclipse.ditto.base.model.signals.commands.CommandResponse;

/**
 * Interface providing the query command adapter and the query command response adapter.
 *
 * @param <Q> the type of query commands
 * @param <R> the type of query command responses
 */
interface QueryCommandAdapterProvider<Q extends Signal<?>, R extends CommandResponse<?>> {

    /**
     * @return the query command adapter
     */
    Adapter<Q> getQueryCommandAdapter();

    /**
     * @return the query command response adapter
     */
    Adapter<R> getQueryCommandResponseAdapter();

}
