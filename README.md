umbcmap
=======
Authors: Nicholas Tokash, William Thompson, Simon Guo, Anthony Sasadeusz, Morgan Miller

Project Overview

  The program is an Android application that organizes information about UMBC’s campus and locations on campus in a searchable form. The information will be displayed on a map of the campus. The information about the campus is organized and searchable. A user is able to select which categories of information they want to see displayed on the map or search each specific category for a particular location. For example, a student looking for which building has their lecture hall should be able to search only lecture halls where as a student looking for food on campus should be able to search specifically for food locations. 

Implementation Details

OSMDroid API (Open Street Map for Android) - 

OSMDroid is Map API for Android devices. It includes features for downloading map tiles, interacting with the map with zoom and pan functions, adding markers to the map view and bounding the map to a specific area. The API is packaged as the jar library osmdroid-android-4.1.jar.

We chose to use this API over Google Maps API v2 because implementation issues. Google Maps does not work with the current Android Development Kit. The device emulator would not render the map tiles. This problem needed to be resolved before we could proceed development. We chose to use OSMDroid because it was easier to implement and still had all the needed function for use to develop our app. A consequence of using OSMDroid is any device using our app requires an SD card to run. 

Android and the Android SDK -

Android is a free platform for smart phones and tablet devices. It features open source app development and a development kit that includes a development environment and an Android device emulator.

We chose to use Android because it is a popular mobile device platform and developing software for devices using Android is free. The Android SDK includes examples and free code that we used and studied in the development of our app. The powerful tools included in the SDK such as the eclipse IDE modified for Android app development and the Android device emulator were important to developing our app in a controlled and safe environment. 

SQLite -

SQLite is a lightweight database interface. It utilizes standard SQL syntax to interact with the database stored locally on a device. 

We decided to use SQLite to store our campus locations because it requires no additional software to be installed with the Android SDK. Since SQLite is cross-platform, we could copy the database generated from the Android device to our computers to check if we were properly interacting with the information on our device and to alter information for testing.  


slf4j-android -

slf4j-android.jar is a logging framework that forward all SLF4J log requests to the logger provided on the Google Android platform. This library was needed by the OSMDroid API.
