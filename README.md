# TicketSync 🎬

A modern Java-based movie ticket booking system with an intuitive GUI interface and MongoDB integration.

## 📋 Overview

TicketSync is a comprehensive movie theater management application that allows users to:
- Browse available movies with poster images
- Book movie tickets with quantity selection
- Manage movie inventory (add/remove movies)
- View booking orders and transaction history
- Secure user authentication system

## ✨ Features

### 🎭 Movie Management
- **Visual Movie Catalog**: Browse movies with poster images stored in MongoDB GridFS
- **Add Movies**: Upload new movies with posters, descriptions, and pricing
- **Remove Movies**: Delete movies from the catalog
- **Search & Filter**: Find specific movies quickly

### 🎫 Ticket Booking
- **Interactive Booking**: Select movies and specify ticket quantities
- **Real-time Pricing**: Dynamic cost calculation based on ticket quantity
- **Order Tracking**: View complete booking history with timestamps

### 🔐 User Authentication
- **Secure Login**: MongoDB-based user credential verification
- **Modern UI**: Rounded, draggable login interface

## 🛠️ Technology Stack

- **Language**: Java (Swing GUI)
- **Database**: MongoDB with GridFS for image storage
- **UI Framework**: FlatLaf (Modern Look and Feel)
- **Build Tool**: Maven
- **Graphics**: Custom image handling and SVG support with Apache Batik

## 📁 Project Structure

```
src/main/java/
├── com/mycompany/oop/system/
│   ├── LoginFrame.java          # Authentication interface
│   ├── MainPage.java            # Main application window
│   ├── OOPSystem.java           # Entry point
│   └── Tester.java              # Development testing
├── Modules/
│   ├── FontLoader.java          # Custom font management
│   ├── StringManager.java       # Localization support
│   ├── GridFSCardData.java      # Movie data model
│   ├── OrderData.java           # Booking data model
│   ├── InputValidation.java     # Form validation utilities
│   └── ValidationDataObject.java
└── ExtraComponents/             # Custom UI components
    ├── CardComponent.java       # Movie display cards
    ├── BookMovieComponent.java  # Booking interface
    ├── AddMovieComponent.java   # Movie management
    └── SideBar.java             # Navigation sidebar
```

## 🐛 Known Issues

- Ensure MongoDB is running before starting the application
- Font files must be present in resources for proper UI rendering
- Image files are required for complete visual experience
