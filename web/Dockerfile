# Build stage
FROM node:10-slim

COPY ui /ui
COPY admin-ui /admin-ui
RUN (ls -lah /ui && cd /ui && yarn && yarn build)

# Run stage
FROM nginx

COPY conf/nginx.conf /etc/nginx/nginx.conf
COPY --from=0 ui/dist www/ui/
