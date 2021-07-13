# joycontrol-web-interface

This repository contains a work-in-progress java web-interface for the [joycontrol rest API](https://github.com/choss/joycontrol_rest_api).

![screenshot](doc/webclient.png?raw=true)

# Introduction

The application emulates (with joycontrol) a pro controller connected via bluetooth to the nintendo switch. It can also execute scripting actions against the switch.

The code is in MVP (minimal viable product) state. There are no proper validations, error handling or documentation available. Help in improving this is highly appreciated.

# Features:
- scripting of pro controller actions to the switch
  - all buttons
  - all sticks
  - nfc read functionality (e.g. ACNH, BOTW)
  - works on a raspberry pi zero W with the azul java 11
- dynamic creation of buttons for quick access to scripts
- every action is a script!

# Requirements
- joycontrol
- joycontrol_rest_api
- java 11
- Working Apache Maven installation

# Configuration/Execution
- edit the application.properties with the location of your rest api endpoint
- compile the application with maven ( mvn clean package -Dmaven.test.skip=true )
- java -jar \<filename\>  (e.g. java -jar web\joycontrol-web\target\joycontrol-web-0.0.1-SNAPSHOT.jar )
- open in browser: http://\<ip\>:8080
  
