/*
 * Copyright (c) 2019 Contributors to the Eclipse Foundation
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
package org.eclipse.ditto.thingsearch.service.starter;

import org.eclipse.ditto.base.api.persistence.cleanup.CleanupPersistenceResponse;
import org.eclipse.ditto.base.model.signals.acks.Acknowledgement;
import org.eclipse.ditto.policies.api.commands.sudo.SudoRetrievePolicyResponse;
import org.eclipse.ditto.things.api.commands.sudo.SudoRetrieveThingResponse;
import org.eclipse.ditto.thingsearch.api.commands.sudo.SudoRetrieveNamespaceReportResponse;
import org.eclipse.ditto.services.utils.health.RetrieveHealthResponse;
import org.eclipse.ditto.services.utils.test.GlobalCommandResponseRegistryTestCases;
import org.eclipse.ditto.base.api.common.RetrieveConfigResponse;
import org.eclipse.ditto.base.api.common.purge.PurgeEntitiesResponse;
import org.eclipse.ditto.base.api.devops.signals.commands.RetrieveLoggerConfigResponse;
import org.eclipse.ditto.messages.model.signals.commands.SendClaimMessageResponse;
import org.eclipse.ditto.base.model.namespaces.signals.commands.PurgeNamespaceResponse;
import org.eclipse.ditto.policies.model.signals.commands.PolicyErrorResponse;
import org.eclipse.ditto.policies.model.signals.commands.actions.ActivateTokenIntegrationResponse;
import org.eclipse.ditto.policies.model.signals.commands.modify.DeleteSubjectResponse;
import org.eclipse.ditto.policies.model.signals.commands.query.RetrieveResourceResponse;
import org.eclipse.ditto.things.model.signals.commands.ThingErrorResponse;
import org.eclipse.ditto.things.model.signals.commands.modify.ModifyFeaturePropertyResponse;
import org.eclipse.ditto.things.model.signals.commands.query.RetrieveFeatureResponse;
import org.eclipse.ditto.thingsearch.model.signals.commands.SearchErrorResponse;
import org.eclipse.ditto.thingsearch.model.signals.commands.query.QueryThingsResponse;

public final class ThingsSearchServiceGlobalCommandResponseRegistryTest extends GlobalCommandResponseRegistryTestCases {

    public ThingsSearchServiceGlobalCommandResponseRegistryTest() {
        super(
                SudoRetrieveThingResponse.class,
                SudoRetrievePolicyResponse.class,
                QueryThingsResponse.class,
                RetrieveFeatureResponse.class,
                ModifyFeaturePropertyResponse.class,
                SendClaimMessageResponse.class,
                PurgeNamespaceResponse.class,
                RetrieveResourceResponse.class,
                DeleteSubjectResponse.class,
                ActivateTokenIntegrationResponse.class,
                SearchErrorResponse.class,
                ThingErrorResponse.class,
                PolicyErrorResponse.class,
                RetrieveLoggerConfigResponse.class,
                SudoRetrieveNamespaceReportResponse.class,
                RetrieveConfigResponse.class,
                RetrieveHealthResponse.class,
                PurgeEntitiesResponse.class,
                SudoRetrieveNamespaceReportResponse.class,
                Acknowledgement.class,
                CleanupPersistenceResponse.class
        );
    }

}
