import com.redhat.jenkins.plugins.ci.GlobalCIConfiguration
import com.redhat.jenkins.plugins.ci.messaging.ActiveMqMessagingProvider

import java.util.logging.Logger

def logger = Logger.getLogger("")

// Get Red Hat UMB Provider to set up staging one
def prodProvider = GlobalCIConfiguration.get().getProvider("Red Hat UMB")
def prodTopicProvider = prodProvider.getTopicProvider()
def prodAuthenticationMethod = prodProvider.getAuthenticationMethod()

logger.info("Setup UMB Stage Messaging Provider")
ActiveMqMessagingProvider umbStage = new ActiveMqMessagingProvider("Local UMB", "failover:(ssl://umb-test-123-umb:61613?socket.enabledProtocols=TLSv1.2)?startupMaxReconnectAttempts=1&maxReconnectAttempts=1", true, "", prodTopicProvider, prodAuthenticationMethod)
try {
    GlobalCIConfiguration.get().addMessageProvider(umbStage)
}
catch (Exception e) {
    logger.warning(e.getMessage())
}
