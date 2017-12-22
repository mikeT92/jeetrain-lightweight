FROM oracle/glassfish:5.0

ENV MYSQL_CONNECTOR_JAVA_MD5=2e3a933f8f7642345d5f34bf43374f8e
ENV MYSQL_CONNECTOR_JAVA_VERSION=5.1.45

# Install MySQL Connector
RUN yum -y install unzip \
    && cd /tmp \
    && curl -v -O https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION.zip \
    && echo "$MYSQL_CONNECTOR_JAVA_MD5 *mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION.zip" | md5sum -c - \
    && unzip -o mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION.zip \
    && cp -v mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION/mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION-bin.jar $GLASSFISH_HOME/glassfish/domains/domain1/lib/ \
    && rm -rf mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION \
    && rm -f mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION.zip \
    && yum -y remove unzip \
    && rm -rf /var/cache/yum

# Copy project-specific service configuration file
COPY domain.xml $GLASSFISH_HOME/glassfish/domains/domain1/config/


