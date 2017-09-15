# Tomcat environment configuration example
# For externalized application properties

export JAVA_OPTS="$JAVA_OPTS -Xmx512m -Dlogback.configurationFile=$CATALINA_HOME/conf/logback.xml -DAPP_PROPS=file:///home/user/location/application.properties "
export CATALINA_PID="$CATALINA_BASE/bin/catalina.pid"
