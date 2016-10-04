OpenWOL
=========

OpenWOL is a Wake-on-Lan application, which can wake your device up from your phone/tablet.

CircleCI: [![CircleCI](https://circleci.com/gh/EvilOFSoul/OpenWOL.svg?style=shield)](https://circleci.com/gh/EvilOFSoul/OpenWOL)
Travis CI: [![Build Status](https://travis-ci.org/EvilOFSoul/OpenWOL.svg)](https://travis-ci.org/EvilOFSoul/OpenWOL)

Screenshots
---------
<img src="http://i.imgur.com/R1YFCZ3.png" width="230px" height="400px"/> <img src="http://i.imgur.com/SMRJBsl.png" width="230px" height="400px"/> <img src="http://i.imgur.com/gIecL1J.png" width="230px" height="400px"/>

Features
---------

* Operation with machine's data (adding, storing, removing).
* Turning on a machine via WI-FI.

Current Tasks
---------

* Releasing the first version of the app.
* Finishing implementation of material design.
* Localization (Ua, Ru).

Future Plans
---------

* Adding a search tool.
* Adding groups and group operation.
* Creating a widget.

Getting OpenWOL
---------

In this moment, compiling OpenWOL from source is the only way to get it. In the future links to apk files will be added.


Compiling OpenWOl From Source with Android Studio
---------

1. Download [Android Studio](https://developer.android.com/studio/index.html).
2. In Android Studio go to Tools>Android>SDK Manager. Make sure you're using the latest SDK tools and SDK Build tools.
3. Clone the OpenWOL repo.
4. To import the project, go to File>Import Project. Select the build.gradle file in the root of the OpenWOL folder.
5. Run the app. If you use the Genymotion, you will need to insert your network mask as an IP address in the machine settings it the app.

Usage
---------
You will need to make sure that your computer and network is set up for and supports Wake-On-Lan / WOL.

1. Install the app on your device.
2. Run the app.
3. Create new machine and configure it.
4. Tap at the machine in the list to turn it on.

Authors
---------

The project created by Yevhenii Bohdanets.

