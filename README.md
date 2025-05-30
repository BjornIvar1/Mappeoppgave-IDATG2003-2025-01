# 3Spill

STUDENT NAME = "ARPIT SAHOO"  
STUDENT ID = "131809"

STUDENT NAME = "Bjørn Ivar Thorsen Høie"  
STUDENT ID = "134131"

## About
3Spill is an application that allows the user to play a variety of games. 
The application is built using Java and JavaFX, and it is designed to be 
modular and extensible. As user, you will be introduced to three different Snakes and
Ladders games and one Monopoly game. The maximum number of players is 4, and the minimum is 1.
The application is designed to be easy to use and
intuitive, with a simple and clean user interface.


## Project tree
```
└───src
    ├───main
    │   ├───java
    │   │   └───edu
    │   │       └───ntnu
    │   │           └───idi
    │   │               └───bidata
    │   │                   ├───filehandler
    │   │                   │   ├───board
    │   │                   │   └───player
    │   │                   ├───model
    │   │                   │   ├───engine
    │   │                   │   ├───entity
    │   │                   │   └───tileactions
    │   │                   ├───observer
    │   │                   ├───ui
    │   │                   │   ├───controller
    │   │                   │   ├───factory
    │   │                   │   └───gui
    │   │                   │       ├───base
    │   │                   │       ├───game
    │   │                   │       └───menu
    │   │                   └───utils
    │   │                       └───exception
    │   └───resources
    │       ├───board
    │       │   ├───monopoly
    │       │   └───SnakesAndLadders
    │       ├───image
    │       │   ├───die
    │       │   └───skippedturn
    │       └───players
    └───test
        └───java
            └───edu
                └───ntnu
                    └───idi
                        └───bidata
                            ├───filehandler
                            └───model
                                ├───engine
                                ├───entity
                                └───tileactions

```


## How to launch
```bash
mvn clean javafx:run
```

## How to run the tests
```bash
mvn clean test
```
## How to run the code coverage
```bash
mvn clean test jacoco:report
```

## Authors
| Name                | Tag       |
|---------------------|-----------|
| Bjørn Ivar Thorsen Høie | bjornivar |
| Arpit Sahoo         | arpit     |