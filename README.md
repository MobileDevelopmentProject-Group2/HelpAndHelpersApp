# CareConnect App
The aim of this project was to create a modern Android application using Jetpack Compose.

## Overview
CareConnect is a support application that helps users find tutor or nanny assistance. Users can also register as a nanny or a tutor (both ways), and post photos and certifications.
The app allows users to post requests for help and respond to other users needs.

## Services
- **User Registration and Login**: All user will access the main page to seek helper or find clients, all need to register and log in. It secure user authentication and profile management.
- **Post and Respond to Help Requests**: Users can post details about the help they need and also respond to others' posts.
- **Helper Profiles**: User can register as a helper, and in this case user needs to create a profile showcasing skills and experiences, provide certifications, and upload personal image (options).
- **Helper List**: Users can view helper list and find a proper helper for their needs.
- **Help List**: User can view help requests posted in the app and see if there are any suitable job offers.
- **Emailing**: User can send email to helper or help requester via the app.
- **Interactive Maps**: User can show the approximate area of help needed request on Google map.
- **My data**: Users can view their own personal data (user data, helper data, help post) and update them if need.
- **Rating**: User can rate helper after the help is done.
- **Privacy and Terms**: This page handle legal and privacy terms.
- **About and Contact**: This page present our contact data and vision.

## Description of the technologies used in the project
- **Kotlin**: Primary programming language.
- **Jetpack Compose**: Toolkit for building native UI.
- **ViewModel**: For managing UI-related data in a lifecycle-conscious way.
- **Google Firebase Storage**: Backend for storing helper images and certificate files.
- **Google Cloud Firestore**: For storing user data and help requests in collections.
- **Google Firebase Authentication**: For user authentication; password sign-in and Google sign-in.
- **Google Maps API**: Showing approximate area of help needed request on Google map.
- **Secrets Gradle Plugin**: For storing API keys and other secrets.
- **Sheets-compose-dialogs**: Using the calendar dialog library for date picker.

## Project Structure
- **MainActivity.kt**: The navigation host, and creating and sharing viewModels.
- **Components**: For reusable UI components like pop-up diglogs, username rule, main top bar, second top bar, time formatter...etc.
- **Screens**: All UI Screens for the application eg: Login, register, main screen...etc.
- **Model**: Data classes for user, helper, and help posts data formats.
- **ViewModels**: Contain all logic and data handling functions for this application.
- **Theme**: For color, typography and shapes.
- **Asset**: Static file, the .csv file for postal code conversion.
- **Res**: This includes layout, strings for screens, and graphics.

## Set up and get started
- An Android device or emulator with API level 25 (Android 7.1) or higher.
- Clone this repository from github https://github.com/MobileDevelopmentProject-Group2/HelpAndHelpersApp.git
- Open the project in Android Studio then run at emulator in Android Studio or run at your Android phone.
- To use the Google map functionality, you need to create a project in Google Cloud Platform and enable the Google Maps SDK for Android. Then create an API key and add it to the `local.properties` file in the root directory of the project.

## Project Team Members
- [Cheng Shufen](https://github.com/ofiscarlett) : Full-stack development ( register, sign-in and user authentication, MyData - fetch and filter data, modify MyData, UI design).
- [Liisa Törmäkangas](https://github.com/liisatormakangas) : Full-stack development ( main screen, navigation, post new help requests, list all help requests, delete user and user data, date and time format, google map api, google sign in, emailing functionality, helper rating, password reset ).
- [Tomoko Takami](https://github.com/t2tato01) : Full-stack development ( add new helper screen, list all helpers by category, helper details, post and fetch images and certificates).
