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
package org.eclipse.ditto.signals.commands.policies.modify;

import static org.eclipse.ditto.model.base.common.ConditionChecker.checkNotNull;

import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.json.JsonField;
import org.eclipse.ditto.json.JsonFieldDefinition;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.json.JsonObjectBuilder;
import org.eclipse.ditto.json.JsonPointer;
import org.eclipse.ditto.model.base.common.HttpStatusCode;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.base.json.FieldType;
import org.eclipse.ditto.model.base.json.JsonParsableCommandResponse;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;
import org.eclipse.ditto.model.policies.Label;
import org.eclipse.ditto.model.policies.PoliciesModelFactory;
import org.eclipse.ditto.signals.commands.base.AbstractCommandResponse;
import org.eclipse.ditto.signals.commands.base.CommandResponseJsonDeserializer;

/**
 * Response to a {@link ModifySubjects} command.
 */
@Immutable
@JsonParsableCommandResponse(type = ModifySubjectsResponse.TYPE)
public final class ModifySubjectsResponse extends AbstractCommandResponse<ModifySubjectsResponse> implements
        PolicyModifyCommandResponse<ModifySubjectsResponse> {

    /**
     * Type of this response.
     */
    public static final String TYPE = TYPE_PREFIX + ModifySubjects.NAME;

    static final JsonFieldDefinition<String> JSON_LABEL =
            JsonFactory.newStringFieldDefinition("label", FieldType.REGULAR, JsonSchemaVersion.V_2);

    private final String policyId;
    private final Label label;

    private ModifySubjectsResponse(final String policyId,
            final Label label,
            final HttpStatusCode statusCode,
            final DittoHeaders dittoHeaders) {

        super(TYPE, statusCode, dittoHeaders);
        this.policyId = checkNotNull(policyId, "Policy ID");
        this.label = checkNotNull(label, "Label");
    }

    /**
     * Creates a response to a {@code ModifySubjects} command.
     *
     * @param policyId the Policy ID of the modified subjects.
     * @param label the Label of the PolicyEntry.
     * @param dittoHeaders the headers of the preceding command.
     * @return the response.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static ModifySubjectsResponse of(final String policyId, final Label label,
            final DittoHeaders dittoHeaders) {

        return new ModifySubjectsResponse(policyId, label, HttpStatusCode.NO_CONTENT, dittoHeaders);
    }

    /**
     * Creates a response to a {@code ModifySubjects} command from a JSON string.
     *
     * @param jsonString the JSON string of which the response is to be created.
     * @param dittoHeaders the headers of the preceding command.
     * @return the response.
     * @throws NullPointerException if {@code jsonString} is {@code null}.
     * @throws IllegalArgumentException if {@code jsonString} is empty.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonString} was not in the expected
     * format.
     */
    public static ModifySubjectsResponse fromJson(final String jsonString, final DittoHeaders dittoHeaders) {
        return fromJson(JsonFactory.newObject(jsonString), dittoHeaders);
    }

    /**
     * Creates a response to a {@code ModifySubjects} command from a JSON object.
     *
     * @param jsonObject the JSON object of which the response is to be created.
     * @param dittoHeaders the headers of the preceding command.
     * @return the response.
     * @throws NullPointerException if {@code jsonObject} is {@code null}.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonObject} was not in the expected
     * format.
     */
    public static ModifySubjectsResponse fromJson(final JsonObject jsonObject, final DittoHeaders dittoHeaders) {
        return new CommandResponseJsonDeserializer<ModifySubjectsResponse>(TYPE, jsonObject)
                .deserialize((statusCode) -> {
                    final String policyId =
                            jsonObject.getValueOrThrow(PolicyModifyCommandResponse.JsonFields.JSON_POLICY_ID);
                    final Label label = PoliciesModelFactory.newLabel(jsonObject.getValueOrThrow(JSON_LABEL));

                    return new ModifySubjectsResponse(policyId, label, statusCode, dittoHeaders);
                });
    }

    @Override
    public String getId() {
        return policyId;
    }

    /**
     * Returns the {@code Label} of the {@code PolicyEntry} whose {@code Subjects} were modified.
     *
     * @return the label.
     */
    public Label getLabel() {
        return label;
    }

    @Override
    public JsonPointer getResourcePath() {
        final String path = "/entries/" + label + "/subjects";
        return JsonPointer.of(path);
    }

    @Override
    protected void appendPayload(final JsonObjectBuilder jsonObjectBuilder, final JsonSchemaVersion schemaVersion,
            final Predicate<JsonField> thePredicate) {

        final Predicate<JsonField> predicate = schemaVersion.and(thePredicate);
        jsonObjectBuilder.set(PolicyModifyCommandResponse.JsonFields.JSON_POLICY_ID, policyId, predicate);
        jsonObjectBuilder.set(JSON_LABEL, label.toString(), predicate);
    }

    @Override
    public ModifySubjectsResponse setDittoHeaders(final DittoHeaders dittoHeaders) {
        return of(policyId, label, dittoHeaders);
    }

    @Override
    protected boolean canEqual(@Nullable final Object other) {
        return other instanceof ModifySubjectsResponse;
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final ModifySubjectsResponse that = (ModifySubjectsResponse) o;
        return that.canEqual(this) && Objects.equals(policyId, that.policyId) && Objects.equals(label, that.label)
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), policyId, label);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + super.toString() + ", policyId=" + policyId + ", label=" + label +
                "]";
    }

}
