/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.server.lifecycle;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.server.lifecycle.ILifecycleComponent;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.server.lifecycle.ILifecycleStep;

/**
 * Implementaton of {@link ILifecycleStep} that starts a single component as a
 * nested component of an owning lifecycle component.
 * 
 * @author Derek
 */
public class StartComponentLifecycleStep extends ComponentOperationLifecycleStep {

    /** Error message if failed */
    private String errorMessage;

    /** Indicates of required for parent component to function */
    private boolean require;

    public StartComponentLifecycleStep(ILifecycleComponent owner, ILifecycleComponent component, String name,
	    String errorMessage, boolean require) {
	super(owner, component, name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sitewhere.spi.server.lifecycle.ILifecycleStep#execute(com.sitewhere.
     * spi.server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void execute(ILifecycleProgressMonitor monitor) throws SiteWhereException {
	if (getComponent() != null) {
	    try {
		getOwner().startNestedComponent(getComponent(), monitor, getErrorMessage(), isRequire());
	    } catch (SiteWhereException t) {
		throw t;
	    } catch (Throwable t) {
		throw new SiteWhereException(getErrorMessage(), t);
	    }
	} else {
	    throw new SiteWhereException("Attempting to start component '" + getName() + "' but component is null.");
	}
    }

    public String getErrorMessage() {
	return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }

    public boolean isRequire() {
	return require;
    }

    public void setRequire(boolean require) {
	this.require = require;
    }
}