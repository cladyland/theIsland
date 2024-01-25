# The Island (2.0)

## Description

The Island is a console game which works in a multithreaded environment.
At the beginning of the game, the user determines the size of the island, after 
which the object of the island, as well as the animals and plants on it, will be 
created. A user can then start a new day, view statistics, or complete the game 
(with the option to save progress and load it the next time a user enter the game).

When a new day begins on the island, the animals change location, hunt or graze, 
and reproduce.  
In the statistics menu you can view the general statistics of the island, the 
number of all animals on the island by species and the difference in the number of 
objects between the beginning of the game and the current day.
***
### Main packages and classes
_start with src/main/java/vika/kovalenko/app/_
* ```core``` - contains logic that ensures the functionality of the game
  * ```core/manage/imp/``` - classes that contain the logic of animal life on the island, namely movement, nutrition and reproduction
  * ```core/utility/IslandItemFactory.java``` - implemented using the "Abstract factory" pattern; class methods define and create the corresponding island object
  * ```core/utility/GameSettings.java``` - implemented using the "Singleton" pattern; class methods define and set game parameters 
* ```primary``` - the upper level of gameplay management
  * ```primary/game/GameManagerImp.java``` - the main gameplay management class
  * ```primary/manage/Dialog.java``` - user interaction class
* ```statistic``` - island statistics management

## Launch

1. download **build/theIsland-2.0.jar**
2. open Windows Terminal
3. enter next commands:
* ```chcp 65001``` _(to enable ANSI supports)_
* ```java -jar $ theIsland-2.0.jar```

**(!) warning: the program is written for Windows 10, possible problems with work on other platforms**
***
#### Note:
> _I rewrote this project from scratch after completing JavaRash courses. Already 
> without the help of mentors and deadlines, but using AI ChatJPT and all 
> previously acquired knowledge and skills. The reason was that the corrections 
> in version 1.0 led to the inevitable appearance of bugs everywhere, and also 
> the project architecture did not allow to make changes flexibly._
