# CareConnect App
Our group project is about creating a Android application for users finding their neededs.

## Overview
CareConnect is a support application that helps users find tutor or nanny assistance. Users also can register as a nanny or tutor (both ways), and post photos and cetifications.
The app allows users to post requests for help and respond to others' needs.
In this app, there are some pages for useage.

## Services
- **User Registration and Login**: All user will access the main page to seek helper or find clients, all need to regist and log in. It secure user authentication and profile management.
- **Post and Respond to Help Requests**: Users can post details about the help they need and also respond to others' posts.
- **Helper Profiles**: User reigster as a helper, then user need to create profiles showcasing their skills and experiences, also provide cetifications, and upload personal image (options).
- **Interactive Maps**:
- **Helper List**: Users can view helper list and find helper for their needs.
- **Help List**: User can view user post and to see if there is any job offer and take contact with. 
- **My data**: Users can view their personaldata (user data, helper data, help post) and update them if need.
- **Privacy and Terms**: This page handle legal and privacy terms.
- **About and Concact**: This page present our contact data and vision.
## Description of the technologies used in the project
- **Kotlin**: Primary programming language.
- **Jetpack Compose**: Toolkit for building native UI.
- **ViewModel**: For managing UI-related data in a lifecycle-conscious way.
- **Firebase Firestore**: Backend for users authentication, database, and storage.
- **Google Maps API**: For handling location-based.
## Project Structure
- **Components**: For reuseable UI componentslike diglog, username rule, main top bar, second topbar...etc.
- **Screens**: All UI Screens for the application eg: Login, regist, main screen...etc.
- **ViewModel**: Contain all logic and data handling for this application.
- **Theme**: For color, typography and shapes.
- **Asset**: Static file, the CSV for postal code conversion.
- **Res**: This inculdes layout, strings for screens, and graphics.
## Setup and Get start
- An Android device or emulator with API level 21 (Lollipop) or higher. Clone the the repository to your machine then run at your phone.
- Colne this repository from github https://github.com/MobileDevelopmentProject-Group2/HelpAndHelpersApp.git
-  Open the project in Android Studio then run at emulator in Android Studio or run at your Android phone.

## Project Team Members
- [Cheng Shufen](https://github.com/ofiscarlett) : Full-stack development ( register, sign-in and user authentication, fetch and filter data, UI design).
- [Liisa Törmäkangas](https://github.com/liisatormakangas) : Full-stack development ( Help, post, date format, google map api ).
- [Tomoko Takami](https://github.com/t2tato01) : Full-stack development ( Helper, upload image, post ).
