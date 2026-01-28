# Use Eclipse Temurin JDK 17 as the base image
FROM eclipse-temurin:17-jdk

# Set environment variables
ENV PAYARA_HOME=/opt/payara6
ENV ADMIN_USER=admin
ENV ADMIN_PASSWORD=admin123

# Database configuration
ENV DB_HOST=host.docker.internal
ENV DB_PORT=3306
ENV DB_NAME=temco_db
ENV DB_USER=root
ENV DB_PASSWORD=1234

# Install Payara Server
RUN apt-get update && apt-get install -y curl unzip \
    && curl -L -o payara-6.2024.10.zip https://nexus.payara.fish/repository/payara-community/fish/payara/distributions/payara/6.2024.10/payara-6.2024.10.zip \
    && unzip payara-6.2024.10.zip -d /opt \
    && rm payara-6.2024.10.zip \
    && apt-get clean

# Copy MySQL connector library
COPY ./mysql-connector-java-8.1.0.jar $PAYARA_HOME/glassfish/domains/domain1/lib/

# Set working directory
WORKDIR $PAYARA_HOME/bin

# Configure admin password, enable secure admin, and create database connection pool
RUN echo "AS_ADMIN_PASSWORD=" > /tmp/pwdfile \
    && echo "AS_ADMIN_NEWPASSWORD=$ADMIN_PASSWORD" >> /tmp/pwdfile \
    && ./asadmin start-domain \
    && ./asadmin --user $ADMIN_USER --passwordfile /tmp/pwdfile change-admin-password --domain_name domain1 \
    && ./asadmin stop-domain \
    && echo "AS_ADMIN_PASSWORD=$ADMIN_PASSWORD" > /tmp/pwdfile \
    && ./asadmin start-domain \
    && ./asadmin --user $ADMIN_USER --passwordfile /tmp/pwdfile enable-secure-admin \
    && ./asadmin restart-domain \
    && ./asadmin --user $ADMIN_USER --passwordfile /tmp/pwdfile create-jdbc-connection-pool \
        --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource \
        --restype javax.sql.DataSource \
        --property "User=${DB_USER}:Password=${DB_PASSWORD}:portNumber=${DB_PORT}:serverName=${DB_HOST}:databaseName=${DB_NAME}:driverClass=com.mysql.cj.jdbc.Driver:URL=jdbc\\:mysql\\://${DB_HOST}\\:${DB_PORT}/${DB_NAME}\\?allowPublicKeyRetrieval\\=true\\&useSSL\\=false\\&zeroDateTimeBehavior\\=convertToNull" \
        temco_system_db_pool \
    && ./asadmin --user $ADMIN_USER --passwordfile /tmp/pwdfile create-jdbc-resource \
        --connectionpoolid temco_system_db_pool \
        jdbc/temco_loan_system_JNDI \
    && rm /tmp/pwdfile \
    && ./asadmin stop-domain

# Expose admin (4848), HTTP (8080), and HTTPS (8181) ports
EXPOSE 8080 4848 8181

# Start domain when container runs
CMD ["sh", "-c", "./asadmin start-domain --verbose"]
