Plingnote
=========
Note that minimum API level is 14 (Ice Cream Sandwich, ver 4.0).

Google Maps Android API Key
=========
To get the map view to display the map you need a valid Android Maps API Key.
A debug key is supplied in the strings.xml file. If you want to use this key
instead of generating a new computer specific key, you can just point
Eclipse to use the supplied debug.keystore file located at the root of this folder. 
This can be achieved by going to Window -> Preferences -> Android -> Build 
and selecting the debug.keystore file supplied from us from the "Custom debug keystore"
option. You will need to clean the project before it will work (Project -> Clean..). 
If it does not work, restart Eclipse and Clean again.

How to run the application tests
=========
To run the tests you need ant installed on your system.

Quickguide on getting ant up and running on Ubuntu.
For other systems please refer to ant website.
1. sudo apt-get install openjdk-7-jdk
2. sudo apt-get install ant (not the ant1.7)

Navigate to PlingnoteTest folder. Create a new file "local.properties" and
paste the following to the newly created file "sdk.dir=YOUR_ROOT_ANDROID_SDK_PATH".
Create a new folder inside PlingnoteTest called "libs" and put the Robotium test framework
jar inside it. Download the jar from "http://code.google.com/p/robotium/".
Now just run "ant debug install test" to run the tests. Note that the tests only
work on a real Android device and not in a emulator, so plug in your device before 
running the command.
To run EMMA and generate code coverage information run "ant emma debug install test"
A html document inside the PlingnoteTest/bin folder called coverage will be created 
with the statistics. This will only work on rooted Android devices with a insecure kernel
(ADBD Insecure).

How to run findbugs
=========
Install ant and findbugs. On Ubuntu do not forget to copy the findbugs-ant.jar to the 
ant libs folder where ant is installed. Navigate to the PlingnoteTest folder and edit the 
"local.properties" and add the following "findbugs.home="YOUR_ROOT_FINDBUGS_PATH"
To run findbugs run the command "ant findbugs". The output html will be in the
"PlingnoteTest/bin" folder afterwards.

How to build and install the application
=========
Navigate to the root folder (Plingnote) and run "ant debug install"

