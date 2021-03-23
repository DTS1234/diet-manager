# diet-manager
Final project for Web appliction module at UEP.

1.	Domain

The main goal of this project is to provide simple but robust application for diet and meal management. Simple is the most important word here, as our goal is to provide basic meal tracking functionality with calories or macros for each dish.  Robust as we want to make it fast and accessible from many devices, UI should easy to use both from mobile and computer devices perspective. Making diet management fast and easy is our main purpose.
Main dashboard off the app will present our day with meals that are about to be eaten and those that we’ve already consumed. App will also provide data about how many calories or macros were consumed. User’s will have the possibility to add their own meals and plans. Access to historical data about diet and meals as well measurement of objectives constitutes for another important functionality.

2.	User roles

There will be three type of users in our app. 
•	Basic user, he can access the app without registration, and use basic functionalities as for example planning meals for whole day, but his data won’t be recorded in the database so he will not have ability to lookup the history or set goals.
•	Normal user, this is a registered one, he can set goals and record diet progress across long period of time. He will be identified by login and authorized by password. Process of registration will be provided by email service and authorization message sent upon the creation of account.
•	Administrator, his role is to provide support for other users he have access to every functionality of the app and can manipulate data of registered users from admin panel view. 
•	Manager, he will have access to aggregate data that can be used for further marketing purposes.

3.	Functionalities 

Main functionalities of our app will consist of:
•	Setting goal for your diet – user can select the limit of macros or calories in the diet. 
•	Goal verification – based on user action and activity app informs of progress done by user. Informs how much left till the end and support him with data. User can access historical data for meals.
•	Meal management – user can create/remove/update/delete meal for a given day, user can also plan his meals further. 
•	Calories counting – app will count calories consumed based on filled meal plan and inform whenever user is over or under the set limit. For each day there will be provided such measurement.
•	Macros counting – app will count macro ingredients from the diet. For each day there will be provided such measurement.
•	Shopping list – I case of planning further meals, app will give an option to generate shopping list
•	Streaks – app will also motivate user to stick to a diet with counting streaks days and will give user rank 
•	Desktop App – app will be also available as PWA
•	Auto-advice feature - app should be intelligent enough to analyze the food and based on goals that were set, calories limit at the current state and food preferences of the user advice the user what kind of meal is most suitable for him at the moment

