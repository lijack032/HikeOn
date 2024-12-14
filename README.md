# HikeOn

## Members

    Vaibhav Gupta - Vab-170
    Jack Li - lijack032
    Arshiya Mahmoodinezad - X-arshiya-X 

## Introduction

HikeOn is a powerful tool for organizing outdoor activities with ease and confidence. Perfect for planning hikes, and other group outings, this software combines convenience and safety.Users simply enter the location they are located, and the software provides curated nearby location options, real-time weather data, and AI-driven tips specific to their chosen activity.

Utilizing OpenWeather for accurate forecasts, Google Maps for nearby locations, and the OpenAI API for intelligent real-time advice, HikeOne ensures that users have everything they need for a successful outing. Whether you’re a seasoned hiker or planning a casual day out, HikeOn is your all-in-one solution for safe and memorable outdoor experiences.

## Background

Outdoor activities are a great way to be healthy both mentally and physically but sometimes we are discouraged from going to them because of many reasons such as not knowing nearby trails, getting surprised by the weather or dont have much knowledge about how to be prepared properly.

## Table of Contents:
1) [User Story](#user-story-division)
2) [Contribution of Developing HikeOn](#contribution)
3) [Feature](#feature)
4) [Insallation](#installation)
5) [Usage Guide](#usage-guide)

## User Story division
1) Emma wants to go on a hike. She enters a location and presses "Find nearby hiking spot". The app shows her nearby locations suitable for hiking, with a real-time rating of the locations. Emma can click any one of the locations and the app jumps to the browser with Google map. (Jack Li - User Story)
   
2) Emma plans a picnic but wants to check the weather first. She enters a location into the app, and it gives the real-time weather data around that location as well as the next 24-hour forecast. (Vaibhav Gupta - User Story)
   
3) Emma needs safety advice for her hiking trip. The app suggests bringing plenty of water and wearing sunscreen, as the weather is hot. (Arshiya Mahmoodinezad - User Story)

4) Emma has a free day and wants to do something outdoors, but she isn’t sure what activity would be suitable. She opens the app and asks for suggestions for activities from the HikeON chatbot based on the current weather, such as recommending a shaded trail on a hot day or a sheltered area if rain is expected. (Team - User Story)

## Contribution:

Frontend Setup: Vaibhav Gupta

Use Case Setup:

1) OpenWeather API: Vaibhav Gupta

2) OpenAI API: Arshiya Mahmoodinezad

3) Google Maps API: Jack Li

## Feature:
1) Given an input of a location, HikeOn can retrieve the latest, and real-time weather data of that location and display it to the user.
2) Based on the location input, HikeOn can generate a list of nearby hiking locations with real-time rating information, and display it to the user.
3) HikeOn also enables chatting with AI. With a single click, users can communicate with AI in many ways such as getting safety advice based on weather, professional technique of hiking, or anything in general.

## Installation:
HikeOn is a maven Java swing GUI application. HikeOn relies on several major external dependencies to function effectively:
1) The [org.json:json library](https://mvnrepository.com/artifact/org.json/json) is used for parsing and generating JSON data, which is essential for handling data interchange returned as API requests are made.
2) The [com.squareup.okhttp3:okhttp library](https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp) is an HTTP client for making network requests, providing a reliable way to communicate with web services.
3) The [io.github.cdimascio:dotenv-java library](https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-java) is used for loading API keys from a .env file, which helps manage data security.
4) The [org.springframework:spring-web library](https://mvnrepository.com/artifact/org.springframework/spring-web) is part of the Spring Framework, providing comprehensive support for web-based applications, and in particular, significant support to HikeOn.
   

## Usage Guide:
HikeOn is a maven Java swing GUI application. Thus, once the user forks this repository and clones it to their local repository, HikeOn can be run simply by running MainFrame.java located in this path: src/main/java/view/MainFrame.java.

