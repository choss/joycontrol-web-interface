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

- dynamic creation of buttons for quick access to scripts
- every action is a script!

# Requirements
- joycontrol
- joycontrol_rest_api
- java 11

# Configuration/Execution
- edit the application.properties with the location of your rest api endpoint
- java -jar \<filename\>
