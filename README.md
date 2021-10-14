## Java developer home assignment
### Task
Assume you are part of a team developing the Vehicle rental application. You are only tasked to
design/develop a Class which calculates the total expense for a given trip.

Do not use any property file or constants to store the values mentioned in the problem statement.
Instead, think in terms of injectable interfaces to your class assuming members of your team will be
responsible to develop the same. You develop a default implementation for those interfaces in which
you can hard code so that you can complete your Class implementation. Actual implementation of those
interfaces may pull data from a database, file or http service as per requirement.

Ensure to write unit tests for your main class using framework for your choice. No need to right for the
other default implementations.

Just use plain language of your choice and no framework. Main goal is to see OOPs concept, good
naming conventions, good design and granularity of class/methods.

### Feature problem statement
A vehicle can be rented for a trip. Vehicle can be a SUV, a car, a van, a bus, etc.

The standard rate for a petrol for a standard trip is 1.5 EUR/Km.

Additional 0.2 EUR/Km charge is applicable for vehicles with AC.

Diesel vehicles rate is 0.1 EUR less than standard rate.

2% discount is applicable for bus on standard rate.

Additional charges of 0.1 EUR/Km/Person are charged if number of passengers exceeds certain limit for
a vehicle.

Example distance:
* Prague - Brno: 200 KM
* Prague - Budapest: 550 KM
* Brno - Viena: 150 KM
* Brno - Budapest: 350 KM etc.

Example trip: 
> VW Golf, Diesel, NON AC, Prague-Brno-Budapest-Prague, 3 Passengers