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
package org.eclipse.services.thingsearch.querymodel.criteria;

import org.eclipse.services.thingsearch.querymodel.criteria.visitors.PredicateVisitor;

/**
 * Equals predicate.
 */
public class EqPredicateImpl extends AbstractSinglePredicate {

    /**
     * Constructor.
     *
     * @param value the value, may be {@code null}
     */
    public EqPredicateImpl(final Object value) {
        super(value);
    }

    @Override
    public <T> T accept(final PredicateVisitor<T> visitor) {
        return visitor.visitEq(getValue());
    }
}
