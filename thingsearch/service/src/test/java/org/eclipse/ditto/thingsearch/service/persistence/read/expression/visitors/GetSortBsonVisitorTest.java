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
package org.eclipse.ditto.thingsearch.service.persistence.read.expression.visitors;

import java.util.Collections;

import org.bson.Document;
import org.eclipse.ditto.rql.query.SortDirection;
import org.eclipse.ditto.rql.query.SortOption;
import org.eclipse.ditto.rql.query.expression.AttributeExpression;
import org.eclipse.ditto.rql.query.expression.SortFieldExpression;
import org.junit.Test;

/**
 * Tests {@link GetSortBsonVisitor}.
 */
public final class GetSortBsonVisitorTest {

    @Test
    public void handlesNullValues() {
        final SortFieldExpression expression = AttributeExpression.of("a/b/c/d/e/f/g");
        final SortOption sortOption = new SortOption(expression, SortDirection.ASC);

        GetSortBsonVisitor.sortValuesAsArray(new Document(), Collections.singletonList(sortOption));
    }
}
