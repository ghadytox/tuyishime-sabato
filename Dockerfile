FROM openjdk:21-jdk
WORKDIR /app
COPY . .
CMD ["/bin/bash"]
