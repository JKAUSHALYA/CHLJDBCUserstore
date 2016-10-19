package com.caliberhomeloans.user.store.internal;

import com.caliberhomeloans.user.store.CHLJDBCUserstoreManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.user.api.UserStoreManager;

/**
 * @scr.component name="com.caliberhomeloans.user.store.internal.CHLUserstoreComponent" immediate="true"
 */
public class CHLUserstoreComponent {

    private static final Log log = LogFactory.getLog(CHLUserstoreComponent.class);

    protected void activate(ComponentContext context) {

        context.getBundleContext().registerService(UserStoreManager.class, new CHLJDBCUserstoreManager(), null);
        log.info("CHL JDBC User store activated successfully.");
    }

    protected void deactivate(ComponentContext context) {
    }
}
