# Build from base-tomcat
FROM base-tomcat:latest

# Copy war file to /tmp/
ADD target/saltedge-integration-service*.war /tmp/

# Create directory to extract war file inside webapps
RUN mkdir $CATALINA_HOME/webapps/ROOT \
    && unzip -q /tmp/saltedge-integration-service*.war -d $CATALINA_HOME/webapps/ROOT/  \
    && rm -rfv /tmp/*.war