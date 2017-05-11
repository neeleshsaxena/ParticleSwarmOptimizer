# ParticleSwarmOptimizer
Amazon 1 Day Delivery Problem solved using PSO by  optimizing the route keeping in mind real time distance and time


Problem Statement

Amazon is struggling to provide delivery service to its customers.
- The problem arises from the fact that there are multiple orders for products in the city, keeping in mind that there are fixed number of 	transport carriers used to deliver, each carrier can carry some number of deliveries. 
- What should be the minimal total cost of the number of routes, the minimal/optimal travel length, and the minimal/optimal travel time?

Proposed Solution	

- The objective here is to minimize the total cost for Amazon, which constitutes the number of routes that each delivery guy takes
- Optimizing their delivery route to deliver products keeping in mind each customer is visited only once. 
- Optimizing the route keeping in mind the time and distance factor 
- The proposed PSO algorithm has been applied to provide these solutions


Vehicle Routing Problem		

- Have modeled the Amazon problem into a Vehicle Routing Problem which classify problem related to distribution of goods between depots and users.
- The users are geographically scattered across the map and the users can be categorized into customers, retailers, etc.
- The distribution of goods is done by one or multiple depots through one or multiple vehicles 
- My model takes distribution to multiple user through vehicles from a single depot.

Particle Swarm Optimization	

- An iterative function that searches over a search space with embedded particles.
- Particles calculate their fitness value over each iteration.
- Each particle determines its movement by combining their current fitness value, previous fitness values and the global fitness values.
- In short the particles update their position with the help of other particles keeping a swarm like movement.
- The next iteration starts after the positions of all particles have been updated




