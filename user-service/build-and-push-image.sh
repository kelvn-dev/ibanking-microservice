mvn clean install -DskipTests
docker build -t kelvn/ibanking-user-service:latest -f Dockerfile .
docker push kelvn/ibanking-user-service:latest