This version has undergone the following changes:

#1-Methods in FreeMarker class have been redesigned so that there is no code repetition or clutter in servlets.
#2-Reading files method has been relocated to Methods class and File Not Found exception has been dealt with Optional.
#3-To eliminate duplicate instances in Servlets, instances have been created in WebServerApp and
 passed as parameters to servlets.
#4-To reduce code repetition, getting user from cookies has been turned into a separate method.
#5-Lombok was implemented in Entities package to reduce coding(Lombok Plugin has been added to IDE to make it work without
warnings)