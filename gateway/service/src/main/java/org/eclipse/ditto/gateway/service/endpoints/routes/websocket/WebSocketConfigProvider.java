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
package org.eclipse.ditto.gateway.service.endpoints.routes.websocket;

import java.util.function.BiFunction;

import org.eclipse.ditto.base.model.headers.DittoHeaders;
import org.eclipse.ditto.gateway.service.util.config.streaming.WebsocketConfig;

/**
 * Provides a method to customize a given {@link org.eclipse.ditto.gateway.service.util.config.streaming.WebsocketConfig}.
 */
public interface WebSocketConfigProvider extends BiFunction<DittoHeaders, WebsocketConfig, WebsocketConfig> {

}
