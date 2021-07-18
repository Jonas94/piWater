# piWater

Backend API for raspberry pi to control an automated watering system via a relay.

Any kind of frontend app could be implemented to call this API. Currently there is a WIP iOS app, 
[WaterButler](https://github.com/Jonas94/WaterButler).

### prerequisites
* Firebase/firestore needs to be set up


### Other implementations
Also see [watering_system.ino](https://github.com/Jonas94/arduino/blob/master/watering_system.ino)
for a more hardware-near solution to the same problem/application, using arduino to control the relay based on input from physical switches.
