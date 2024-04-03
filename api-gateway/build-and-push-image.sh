mvn clean install -DskipTests
docker build -t kelvn/ibanking-api-gateway:latest -f Dockerfile .
docker push kelvn/ibanking-api-gateway:latest