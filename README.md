# almubots-game

Game where tanks fight with each other. Game was made in a way to easily to create bots that can fight with each other. Every tank can see state of the game, like for example positions of tanks or their health.
Almubots-game is a server created using Kotlin with API for sending commands to tanks and for getting state of the game.

Avaliable actions:
- speed change in x and y axis
- canon rotation
- shooting

For every hit or kill tank is given points.

Example python client library to communicate with the server [almubots-client-python](https://github.com/Wojcik98/almubots-client-python)
