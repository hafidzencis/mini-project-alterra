FROM nginx:1.21.0-alpine

COPY ./target/site/jacoco /usr/share/nginx/html