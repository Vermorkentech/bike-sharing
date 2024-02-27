#!/bin/sh
echo "Copying UI component to BikeSharing-API"
cp monolith/src/main/java/io/axoniq/bikesharing/api/*.java microservices/bikesharing-api/src/main/java/io.axoniq.bikesharing.api/

echo "Copying Command component to BikeSharing-Command"
cp monolith/src/main/java/io/axoniq/bikesharing/command/*.java microservices/bikesharing-command/src/main/java/io.axoniq.bikesharing.command/

echo "Copying Query component to BikeSharing-Query"
cp monolith/src/main/java/io/axoniq/bikesharing/query/*.java microservices/bikesharing-query/src/main/java/io.axoniq.bikesharing.query/

echo "Finished microservices creation process"