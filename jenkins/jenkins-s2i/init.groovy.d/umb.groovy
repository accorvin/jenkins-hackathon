import com.redhat.jenkins.plugins.ci.GlobalCIConfiguration
import com.redhat.jenkins.plugins.ci.messaging.ActiveMqMessagingProvider

import java.util.logging.Logger

def logger = Logger.getLogger("")

// Get Red Hat UMB Provider to set up staging one
def prodProvider = GlobalCIConfiguration.get().getProvider("Red Hat UMB")
def prodTopicProvider = prodProvider.getTopicProvider()
def prodAuthenticationMethod = prodProvider.getAuthenticationMethod()

logger.info("Setup UMB Stage Messaging Provider")
ActiveMqMessagingProvider umbStage = new ActiveMqMessagingProvider("Red Hat UMB Stage", "failover:(ssl://messaging-devops-broker01.web.stage.ext.phx2.redhat.com:61616?socket.enabledProtocols=TLSv1.2,ssl://messaging-devops-broker02.web.stage.ext.phx2.redhat.com:61616?socket.enabledProtocols=TLSv1.2)?startupMaxReconnectAttempts=1&maxReconnectAttempts=1", true, "", prodTopicProvider, prodAuthenticationMethod)
try {
    GlobalCIConfiguration.get().addMessageProvider(umbStage)
}
catch (Exception e) {
    logger.warning(e.getMessage())
}
