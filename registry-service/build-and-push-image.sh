mvn clean install -DskipTests
docker build -t kelvn/ibanking-registry-service:latest -f Dockerfile .
docker push kelvn/ibanking-registry-service:latest