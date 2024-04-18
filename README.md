# Evolutionary Microservices enabled by AxonIQ

This repo is the demo application which is presented in our introduction webinar where we present the concept of building
services which start as well-structured monoliths that will allow you to evolve to microservices when the time is right.
This is done through the use messaging (Commands, Events, and Queries) to communicate between the components of the monolith
to achieve location transparency.  This allows our application to evolve towards a microservices architecture
where components can be moved from our monolith to their own microservice. This allows the component to independently scale
and evolve to meet its own needs and requirements. 

The three products that Axoniq offers to help in this approach are

- Axon Framework - JVM based framework which
- Axon Server - distributed message bus and event store
- AxonIQ Console - monitoring and management of axon based applications

## Problem overview
To demonstrate evolving an application from a single monolith to microservices we have build a bike sharing application.
This application allows a bike to be registered to create an inventory, to be checked out, and to be returned.  


## Getting Started
You will need to following installed to run this repo
- Java 21
- Docker Compose

## Running the application
Start Axon Server using docker by running the following from the root of the project

`docker compose up -d`

Start the project by running the `BikeSharingApplication` application.


### Exercising the funcationality