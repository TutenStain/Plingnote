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