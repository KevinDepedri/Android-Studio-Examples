# <p align="center">Android Studio Examples</p> 

Collection of Android apps developed following the modules of the 
[Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course) guide provided by Google.

All the applications in this repository are developed in Kotlin using the latest best practises and exploiting the Jetpack Compose framework.
Here follows the list of all the applications with a short description:
1. Happy Birthday, simple app like an 'hello world'. Introduction to general composables, composable previews and toast.
2. Dice Roller, app that allows to roll a dice how many times you want by pressing a button. Introduction to resiliency to recomposition using 'remember'.
3. Tip Calculator, app to compute the tip and to round it given the amount of the bill. Introduction to lambdas, state hoisting, focus manager, keyboard 
actions and types.
4. Affirmations, app that allows to scroll in a list of images with short captions. Introduction to LazyColumn composable and dataclasses.
5. Woof, app that allows to scroll dog profiles and to open them to see more informations. Introduction to scaffold composable, themes, animations and light/dark mode.
6. Dessert Clicker, game-app that allows to click many times on desserts to increase the bill. Introduction to activity lifecicle and resiliency to configuration changes 
using 'rememberSaveable'.
7. Unscramble, game-app where the player needs to unscramble the words correctly to increase its score. Introduction to resiliency to recomposition and configuration 
changes using viewModels and UiState class, which allows to separate the UI layer from the data layer.
8. Cupcake, app where the user can oreder a set of cupcakes, specifying number, flavour and pick up date. Finally the total is computed and it is sent to another 
application. Introduction to navController, navHost and intents.
9. Reply, app similar to an email manager, with different sections and supported on different screen sizes. Introduction to layouts and different navigation bars (bottom bar, navigation rail or permanent drawer) which are automatically enabled depending on the device screen size.
10. Race Tracker, app in which two bar increments with different pace, simulating a run between two people. Introduction to coroutines, coroutineScope and linearProgressIndicator composable.
11. Mars Photos, app which connects to a server and makes a request to download some Mars images. Introduction to HTTP, Retrofit and REST. [Development in progress]
12. Juice tracker, app where the user can save its juices by adding a name, a description, a color and a rating. Here the information are saved in memory for the 
next start of the application. Introduction to the system based on views XML files which was used previously to Jetpack Compose.
