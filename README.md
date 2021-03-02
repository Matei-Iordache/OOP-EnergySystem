OOP-EnergySystem
================================================================================
* Name: Iordache Matei Cezar
* Series: CA
* Group: 325CA
---
* Time spent on the project: ~1 week
---
![Language used](https://img.shields.io/badge/Language-Java-brightgreen)
![Technologies used](https://img.shields.io/badge/Technologies-Json-blue)
![Tests passed](https://img.shields.io/badge/tests-20%20passed%2C%200%20failed-brightgreen)

## Table of contents

  * [Project description](#project-description)
  * [How to run the program](#how-to-run-the-program)
  * [Project Structure](#project-structure)
  * [Simulation step by step guide](#simulation-step-by-step-guide)
  * [OOP DESIGN](#oop-design)
    + [Factory uses](#factory-uses)
    * [Singleton uses](#singleton-uses)
    + [Observer uses](#observer-uses)
    + [Strategy uses](#strategy-uses)
  * [Java 8 features](#java-8-features)
  * [Feedback](#feedback)
## Project description

- The project simulates an energetic system in which there are different
entities like consumers, distributors and producers
- All the entities will try to accomplish their tasks and not go bankrupt
- Consumers will buy power from the distributors
- Distributors will take power from the producers and distribute it to consumers
- Producers will manufacture power

## How to run the program
Step1. Open the project in IntelliJ

Step2. 
- Run the Test.java class for multiple tests
- Run the Main.java class for only one test
---
## Project Structure

1. SRC Folder - main folder of the project.
    * actions folder - contains the Commands and Updates classes.
        * Commands - contains all commands for the entities.
        * Updates - contains all update methods for the entities

    * checker folder - checker files

    * entities folder - contains an enum with all the energyTypes of a producer
        * EnergyType - enum with all the energyTypes of a producer

    * fileio folder - contains classes that will store all the data extracted
        from the JSON files, as well as classes with methods that extract data
        from a JSON file and write the data back as a JSON file

        * ConsumerData - stores all the data regarding a consumer

        * DistributorChanges - stores all the data from a distributor monthly
            change

        * DistributorData - stores all the data regarding a distributor.

        * Input - store all the data extracted from the json file

        * InputLoader - parses all the information from the Json file into an
            Input object

        * InputOutput - interface for a factory design pattern

        * InputOutputFactory - starting point for the factory design pattern

        * MonthlyStats - stores all the data regarding the MonthlyStats
            of a producer

        * MonthlyUpdatesInputData - stores all the data
                                   regarding the MonthlyUpdates

        * ProducerChanges - stores all the data from a producer monthly change

        * ProducerData - stores all the data regarding a producer

        * Writer - Writes an output JSON file containing the desired information

    * strategies folder - Contains strategy classes needed for the strategy pattern

        * Context - Context class for the strategy design pattern

        * GreenStrategy - Choose producers which have renewable energy

        * PriceStrategy - Choose producers which have the cheapest energy

        * QuantityStrategy - Choose producers which have the better energy output

        * Strategy - Interface for the Stragegy Design Pattern

    * utils folder - Constants

        * Constants - Different constants used throughout the program

2. Game Class - Class which contains the method that performs the simulation

3. Main Class - Starting point of the program

4. Test Class - Runs the tests from the checker.
---
## Simulation step by step guide
If it is the first month of the simulation, the flow is like this:
1. Determine which producers have renewable energy.
2. Distributors choose to which producers to subscribe
3. Distributors calculate their production cost, their profit,
        and set a price for their contracts
4. Consumers choose their distributors and calculate their new budget
5. Distributors calculate their new budget at the end of the month
6. Consumers update their contract time.

If it is not the first month of the simulation, the flow is like this:
1. Add new consumers and update distributor infrastructure cost,
    based on the monthly changes.
2. Distributors calculate their profit, the contract cost and
   will delete bankrupt consumers from their contract list.
3. Consumers choose their distributors and calculate their new budget
4. Distributors calculate their new budget at the end of the month
5. Consumers update their contract time.
6. Update the energyPerDistributor of a producer based on monthly changes
and notify the subscribed distributors.
7. Distributors choose their producers again if they are affected.
8. Distributors calculate their new production cost.
9. Set the monthly stats of producers at the end of the month.

---
## OOP DESIGN
Throughout the project I have used different oop concepts and design patterns
* Patterns used:
    - Factory
    - Singleton
    - Observer
    - Strategy

### Factory uses
I have used the Factory design pattern in the reading/writing of the entities.
(InputOutputFactory)

## Singleton uses
I have made the InputOutputFactory class a singleton.

### Observer uses
For the observer design pattern:
- the ProducerData class extends the Observable Class.
- the DistributorData class extends the Observer Class.

Everytime a producer undergoes a monthly change, every distributor 
that takes power from him will be announced to rechoose his producers

### Strategy uses
I have used the Strategy Design Pattern in creating different strategies based on
the preference of a distributor.

All the strategy classes will implement the StrategyInterface.

---
## JAVA 8 FEATURES
I used streams in the implementation of the different strategies

---
## FEEDBACK
Very hard to understand Task, could have been explained better.

But overall it was a very good exercise, forcing me to use the different
concepts I learned in this course.