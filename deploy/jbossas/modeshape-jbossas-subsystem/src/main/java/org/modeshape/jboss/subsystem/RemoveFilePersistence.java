/*
 * ModeShape (http://www.modeshape.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.modeshape.jboss.subsystem;

import java.util.Collections;
import java.util.List;
import org.jboss.as.controller.OperationContext;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceName;

class RemoveFilePersistence extends AbstractModeShapeRemoveStepHandler {

    static final RemoveFilePersistence INSTANCE = new RemoveFilePersistence();

    private RemoveFilePersistence() {
    }

    @Override
    List<ServiceName> servicesToRemove( OperationContext context,
                                        ModelNode operation,
                                        ModelNode model ) {
        AddressContext addressContext = AddressContext.forOperation(operation);
        // Get the service addresses ...
        final String type = addressContext.lastPathElementValue();
        final String repositoryName = addressContext.repositoryName();

        return Collections.singletonList(ModeShapeServiceNames.persistenceFSServiceName(repositoryName, type));
    }
}
