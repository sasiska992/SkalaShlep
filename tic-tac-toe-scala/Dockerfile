FROM eclipse-temurin:11-jdk

# Install necessary packages
RUN apt-get update && \
    apt-get install -y swi-prolog wget curl gnupg && \
    rm -rf /var/lib/apt/lists/*

# Install SBT
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | gpg --dearmor | tee /etc/apt/trusted.gpg.d/sbt.gpg > /dev/null && \
    apt-get update && \
    apt-get install -y sbt && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy the application files
COPY . /app

# Make startup script executable
RUN chmod +x /app/startup.sh

# Expose port 8080
EXPOSE 8080

# Command to run the application
CMD ["/app/startup.sh"]