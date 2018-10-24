/*
 * Copyright (c) 2017-2018 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.model.connectivity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.json.JsonCollectors;
import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.json.JsonField;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.json.JsonObjectBuilder;
import org.eclipse.ditto.json.JsonValue;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;

/**
 * Immutable implementation of {@link Enforcement}.
 */
@Immutable
final class ImmutableEnforcement implements Enforcement {

    private final String input;
    private final Set<String> filters;

    private ImmutableEnforcement(final String input, final Set<String> filters) {
        this.input = input;
        this.filters = Collections.unmodifiableSet(new HashSet<>(filters));
    }

    /**
     * Creates a new ImmutableEnforcement instance.
     *
     * @param input input the string to match against filters.
     * @param filters filters to match against the input .
     * @return new ImmutableEnforcement instance
     */
    static ImmutableEnforcement of(final String input, final Set<String> filters) {
        return new ImmutableEnforcement(input, filters);
    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public Set<String> getFilters() {
        return filters;
    }

    @Override
    public JsonObject toJson(final JsonSchemaVersion schemaVersion, final Predicate<JsonField> thePredicate) {
        final Predicate<JsonField> predicate = schemaVersion.and(thePredicate);
        final JsonObjectBuilder jsonObjectBuilder = JsonFactory.newObjectBuilder();

        jsonObjectBuilder.set(JsonFields.INPUT, input, predicate);
        jsonObjectBuilder.set(JsonFields.FILTERS, filters.stream()
                .map(JsonFactory::newValue)
                .collect(JsonCollectors.valuesToArray()), predicate.and(Objects::nonNull));

        return jsonObjectBuilder.build();
    }

    /**
     * Creates a new {@code Enforcement} object from the specified JSON object.
     *
     * @param jsonObject a JSON object which provides the data for the Enforcement to be created.
     * @return a new Enforcement which is initialised with the extracted data from {@code jsonObject}.
     * @throws NullPointerException if {@code jsonObject} is {@code null}.
     * @throws org.eclipse.ditto.json.JsonParseException if {@code jsonObject} is not an appropriate JSON object.
     */
    public static Enforcement fromJson(final JsonObject jsonObject) {
        final Set<String> readFilters = jsonObject.getValue(JsonFields.FILTERS)
                .map(array -> array.stream()
                        .map(JsonValue::asString)
                        .collect(Collectors.toSet())).orElse(Collections.emptySet());
        final String readInput =
                jsonObject.getValueOrThrow(JsonFields.INPUT);
        return new ImmutableEnforcement(readInput, readFilters);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImmutableEnforcement that = (ImmutableEnforcement) o;
        return Objects.equals(input, that.input) &&
                Objects.equals(filters, that.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, filters);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" +
                "input=" + input +
                ", filters=" + filters +
                "]";
    }
}
