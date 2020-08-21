# how-to-get-app

## Author
Hello! My name is Ruslan Gainutdinov, and this entire application was made by me.
I am very critical to my code and in constant chase for better coding skills. So any reviews and feedbacks are recommended.

[LinkedIn](https://www.linkedin.com/in/ruslan-gainutdinov-5329b71a9/)

## INFO
How To Get Application is service for booking travel options from one place to another. Provided by different companies. To use this service you must have an account in the app. If you don’t just come through simple registration procedure. When you have an account just log in and start to use  it.

**NOTE! This application is pet project! All the cities and companies that are used in project are not related to real ones, even if their names are the same. Cities that have been used are real ones.**

**List of things that already were implemented**
- Authentication via Spring Security using ORM.
- Authorization with 3 ROLES: USER, PROVIDER and ADMIN(NOT YET IMPLEMENTED)
- Login form was created(I really liked it)
- Registration with validation and confirmation code using that will be sent to email, that you will specify in registration page
- Seacrhing mechanism for suitable direction, date and transport type(ValuesGenerator values are used as results), with capability to book it, 1 order up to 8 peolpe.
- User can cancel its booking, if its needed, all seats, that were taken will become available
- Provider can manage its travel options, edit, add and remove
- If provider decided to cancel some route, any user that has booking on this flight/bus will be notified via email(aspects used)

WE ARE HERE

- Form to change user's personal information will be added (completely forgot about it :facepalm:)
- ADMIN ROLE is already in the system, but is not yet implemented
- ADMIN ROLE must have control of anything

If you think that I should add smth else, please let me know, I would appreciate any feedback as positive, as negative

**The technologies that have been used during development are:**

- Spring Boot
- Spring MVC
- Hibernate ORM
- Hibernate Validator
- Thymeleaf
- MySQL
Bootstrap templates were used for front-end part of the application.

-Documentation for the app almost done!

## Quick start
Below is shortened instruction what to do to run the project on your PC.
- Download project via IDE or any other way
- Run ValuesGenerator file in howtoget/mysql directory to produce unique values for the app
- Run sql scripts: how-to-get-ddl, commercial_accounts, buses-to-insert and flights-to- insert
**NOTE!For 1st and 2nd - order matters!**
- Run the project via running class HowToGetApplication.java in *com.ruslanproject.howtoget* package

Now when project in running.
To use this service you must have an account in the app. In case you don't, just come through simple registration procedure. 
When you have an account just log in and start to use it.
You can actually authenticate via one of the few already existing **Commercial Accounts**, that are already provided in the app, with mysql scripts.
Detailed instruction of setting up the environment, MySQL, database & project provided in additional files. 
Logins and passwords of Commercial Accounts are in the Manual_LANG files, in **Appendix** section.


