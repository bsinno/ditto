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
package org.eclipse.ditto.signals.events.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.UUID;

import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.base.json.FieldType;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;
import org.eclipse.ditto.signals.commands.base.CommandResponse;
import org.eclipse.ditto.signals.commands.base.GlobalCommandResponseRegistry;
import org.eclipse.ditto.signals.commands.things.modify.ModifyThingResponse;
import org.eclipse.ditto.signals.events.base.Event;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test for {@link BatchCommandExecuted}.
 */
public final class BatchCommandExecutedTest {

    private static final String KNOWN_BATCH_ID = UUID.randomUUID().toString();

    private static final CommandResponse KNOWN_RESPONSE =
            ModifyThingResponse.modified("org.eclipse.ditto.test:myThing3",
                    DittoHeaders.newBuilder().correlationId(KNOWN_BATCH_ID).build());

    private static final JsonObject KNOWN_JSON = JsonFactory.newObjectBuilder()
            .set(Event.JsonFields.TYPE, BatchCommandExecuted.TYPE)
            .set(Event.JsonFields.TIMESTAMP, null)
            .set(BatchCommandExecuted.JsonFields.RESPONSE,
                    KNOWN_RESPONSE.toJson(JsonSchemaVersion.V_2, FieldType.regularOrSpecial()))
            .set(BatchEvent.JsonFields.BATCH_ID, KNOWN_BATCH_ID)
            .set(BatchCommandExecuted.JsonFields.DITTO_HEADERS, KNOWN_RESPONSE.getDittoHeaders().toJson())
            .build();

    @Test
    public void assertImmutability() {
        assertInstancesOf(BatchCommandExecuted.class, areImmutable(),
                provided(CommandResponse.class, DittoHeaders.class).areAlsoImmutable());
    }

    @Test
    public void testHashCodeAndEquals() {
        EqualsVerifier.forClass(BatchCommandExecuted.class)
                .withRedefinedSuperclass()
                .verify();
    }

    @Test(expected = NullPointerException.class)
    public void tryToCreateInstanceWithNullBatchId() {
        BatchCommandExecuted.of(null, KNOWN_RESPONSE);
    }

    @Test(expected = NullPointerException.class)
    public void tryToCreateInstanceWithNullResponse() {
        BatchCommandExecuted.of(KNOWN_BATCH_ID, null);
    }

    @Test
    public void toJsonReturnsExpected() {
        final BatchCommandExecuted underTest =
                BatchCommandExecuted.of(KNOWN_BATCH_ID, KNOWN_RESPONSE);
        final JsonObject actualJson = underTest.toJson(FieldType.regularOrSpecial());

        assertThat(actualJson).isEqualTo(KNOWN_JSON);
    }

    @Test
    public void createInstanceFromValidJson() {
        final BatchCommandExecuted underTest =
                BatchCommandExecuted.fromJson(KNOWN_JSON.toString(), DittoHeaders.empty(),
                        GlobalCommandResponseRegistry.getInstance());

        assertThat(underTest).isNotNull();
        assertThat(underTest.getResponse()).isEqualTo(KNOWN_RESPONSE);
    }

    @Test
    public void retrieveEventName() {
        final String name = BatchCommandExecuted.fromJson(KNOWN_JSON.toString(), DittoHeaders.empty(),
                GlobalCommandResponseRegistry.getInstance()).getName();

        assertThat(name).isEqualTo(BatchCommandExecuted.NAME);
    }

}
