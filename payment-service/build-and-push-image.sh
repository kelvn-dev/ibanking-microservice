mvn clean install -DskipTests
docker build -t kelvn/ibanking-payment-service:latest -f Dockerfile .
docker push kelvn/ibanking-payment-service:latest