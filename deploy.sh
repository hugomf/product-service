docker build -t product-service:05 .
docker push registry-url/repository/image-name:tag

# Run a container using the image
#docker run -p 8080:8080 product-service