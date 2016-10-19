package com.caliberhomeloans.user.store;

import org.apache.axiom.om.util.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.core.UserCoreConstants;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.jdbc.JDBCRealmConstants;
import org.wso2.carbon.user.core.jdbc.JDBCUserStoreManager;
import org.wso2.carbon.user.core.profile.ProfileConfigurationManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * JDBC user store for CHL.
 */
public class CHLJDBCUserstoreManager extends JDBCUserStoreManager {

    private static final Log log = LogFactory.getLog(CHLJDBCUserstoreManager.class);

    public CHLJDBCUserstoreManager() {
    }

    public CHLJDBCUserstoreManager(RealmConfiguration realmConfig, Map<String, Object> properties,
                                   ClaimManager claimManager, ProfileConfigurationManager profileManager,
                                   UserRealm realm, Integer tenantId) throws UserStoreException {
        super(realmConfig, properties, claimManager, profileManager, realm, tenantId);
    }

    @Override
    protected String preparePassword(String password, String saltValue) throws UserStoreException {

        try {
            String digestInput = password;
            if (saltValue != null) {
                digestInput = password + saltValue;
            }
            String digsestFunction = realmConfig.getUserStoreProperties().get(
                    JDBCRealmConstants.DIGEST_FUNCTION);
            if (digsestFunction != null) {

                if (digsestFunction
                        .equals(UserCoreConstants.RealmConfig.PASSWORD_HASH_METHOD_PLAIN_TEXT)) {
                    return password;
                }

                MessageDigest dgst = MessageDigest.getInstance(digsestFunction);
                byte[] byteValue = dgst.digest(digestInput.getBytes());
                password = Base64.encode(byteValue);
            }
            return password;
        } catch (NoSuchAlgorithmException e) {
            String msg = "Error occurred while preparing password.";
            if (log.isDebugEnabled()) {
                log.debug(msg, e);
            }
            throw new UserStoreException(msg, e);
        }
    }
}
