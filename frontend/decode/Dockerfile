# production stage
FROM nginx:alpine

# Set the working directory in the container
WORKDIR /dist

# Copy the built app from the build stage
COPY /dist .

# Remove default nginx configuration
RUN rm /etc/nginx/conf.d/default.conf

# Copy custom nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose the port that the app will run on
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]