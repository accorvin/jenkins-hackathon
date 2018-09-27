import hudson.security.csrf.DefaultCrumbIssuer
import javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration
import jenkins.model.GlobalConfiguration
import jenkins.model.Jenkins
import jenkins.security.s2m.AdminWhitelistRule

import java.util.logging.Logger

def logger = Logger.getLogger("")
def jenkins = Jenkins.getInstance()

logger.info("Disabling CLI over remoting")
jenkins.CLI.get().setEnabled(false);
logger.info("Enable Slave -> Master Access Control")
jenkins.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

// Set global read permission
def strategy = jenkins.getAuthorizationStrategy()
strategy.add(hudson.model.Hudson.READ, 'anonymous')
strategy.add(hudson.model.Item.READ, 'anonymous')
// users with URL will be presented with login screen
strategy.add(hudson.model.Item.DISCOVER, 'anonymous')
jenkins.setAuthorizationStrategy(strategy)

// Removing since this conflicts with triggering builds
// using curl with Openshift login Bearer token auth method.
//logger.info("Enabling CSRF Protection")
//instance.setCrumbIssuer(new DefaultCrumbIssuer(true))

logger.info("Disabling deprecated agent protocols (only JNLP4 is enabled)")
Set<String> agentProtocolsList = ['JNLP4-connect']
jenkins.setAgentProtocols(agentProtocolsList)

logger.info("Disabling script security for job DSL scripts")
GlobalConfiguration.all().get(GlobalJobDslSecurityConfiguration.class).useScriptSecurity = false

jenkins.save()
