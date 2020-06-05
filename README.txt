This version has undergone the following changes:

#1-Methods in FreeMarker class have been redesigned so that there is no code repetition or clutter in servlets.
#2-Reading files method has been relocated to Methods class and File Not Found exception has been dealt with Optional.
#3-To eliminate duplicate instances in Servlets, instances have been created in WebServerApp and
 passed as parameters to servlets.
#4-To reduce code repetition, getting user from cookies has been turned into a separate method.
#5-Lombok was implemented in Entities package to reduce coding(Lombok Plugin has been added to IDE to make it work without
warnings)
#6-ParamFilter was added to Registration and Login pages, which enables us to avoid empty forms.
#7-Logging was implemented in order to keep track of exceptions and errors.
#8-Filters have been improved with the help of interface implementation.
#9-All the incorrect mappings have been dealt with(They are directed to menu)
#10-Any incorrect user in /messages/ URL will be redirected to /like/*
#Other minor changes have been made to the project.


#################### HOW TO USE APPLICATION #####################

URL: http://mystepproject.herokuapp.com/
Repository: https://github.com/ELTAJ-R/StepProject

1.If you have an account please go to login page, if not please register.
Please note that you can not register unless you fill all the fields in the registration form.
Please be careful to put working url to the profile picture field while registering. Otherwise application won't work properly.
2.Without logging in no further functions can be used; you will automatically be redirected to login page.
3.Logged in user can not go to login/registration page without logging out first.
4.If you enter incorrect URL address (mapping), you will be directed to menu page.

Credentials for testing application:
Username: James
Password: 4567