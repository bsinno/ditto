/*
 * Copyright (c) 2017 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 *
 * Contributors:
 *    Bosch Software Innovations GmbH - initial contribution
 */
package org.eclipse.ditto.signals.commands.amqpbridge.modify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Collections;

import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.model.amqpbridge.AmqpConnection;
import org.eclipse.ditto.model.base.common.HttpStatusCode;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.signals.commands.amqpbridge.TestConstants;
import org.eclipse.ditto.signals.commands.base.CommandResponse;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test for {@link CreateConnectionResponse}.
 */
public final class CreateConnectionResponseTest {

    private static final JsonObject KNOWN_JSON = JsonObject.newBuilder()
            .set(CommandResponse.JsonFields.TYPE, CreateConnectionResponse.TYPE)
            .set(CommandResponse.JsonFields.STATUS, HttpStatusCode.CREATED.toInt())
            .set(CreateConnectionResponse.JSON_CONNECTION, TestConstants.CONNECTION.toJson())
            .build();

    @Test
    public void testHashCodeAndEquals() {
        EqualsVerifier.forClass(CreateConnectionResponse.class)
                .usingGetClass()
                .verify();
    }

    @Test
    public void assertImmutability() {
        assertInstancesOf(CreateConnectionResponse.class, areImmutable(), provided(AmqpConnection.class).isAlsoImmutable());
    }

    @Test
    public void createInstanceWithNullConnection() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> CreateConnectionResponse.of(null, Collections.emptyList(), DittoHeaders.empty()))
                .withMessage("The %s must not be null!", "Connection")
                .withNoCause();
    }

    @Test
    public void fromJsonReturnsExpected() {
        final CreateConnectionResponse expected =
                CreateConnectionResponse.of(TestConstants.CONNECTION, Collections.emptyList(), DittoHeaders.empty());

        final CreateConnectionResponse actual =
                CreateConnectionResponse.fromJson(KNOWN_JSON, DittoHeaders.empty());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void toJsonReturnsExpected() {
        final JsonObject actual =
                CreateConnectionResponse.of(TestConstants.CONNECTION, Collections.emptyList(), DittoHeaders.empty()).toJson();

        assertThat(actual).isEqualTo(KNOWN_JSON);
    }

}
