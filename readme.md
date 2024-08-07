# Hotel Management Android App

## Overview

This Android application, developed using Kotlin, XML, and Firebase, offers a comprehensive hotel
management system with separate interfaces for customers and hotel owners. The app streamlines the
process of hotel booking for customers while providing hotel managers with tools to list and manage
their properties efficiently.

---
## Features

### Customer Side

- **Authentication**: Secure login using Firebase authentication.
- **Hotel Browsing**: View a list of hotels with detailed information:
    - Hotel name
    - Location on Google Maps
    - Price (including GST)
    - Address
    - Number of available rooms
    - Capacity per room
    - Contact details (phone and email)
- **Search Functionality**: Find hotels by city name.
- **Booking System**:
    - Book rooms with integrated Razorpay payment gateway.
    - View booking history.
- **Real-time Updates**: Automatic reduction of available rooms after booking.
- **User Profile**: View and edit account details.
- **Logout**: Secure sign-out option.

### Hotel Owner Side

- **Authentication**: Separate signup and signin options for hotel managers.
- **Hotel Management**:
    - List new hotels with comprehensive details.
    - Edit and update existing hotel information.
    - Manage room availability.

## Screenshots

[Insert screenshots of key app screens here]

## Technology Stack

- **Language**: Kotlin
- **UI**: XML
- **Backend**: Firebase
- **Maps Integration**: Google Maps API
- **Payment Gateway**: Razorpay

## Installation

1. Clone the repository:

```bash
git https://github.com/itssinghankit/ShelterSpot.git
```

2. Install necessary dependencies.
3. Add `Google Map API key` from _Google Maps SDK_ to `secrets.properties`

```bash
MAPS_API_KEY= <Your API key>
```

4. Configure Firebase credentials.
5. Run the app on an Android device or emulator.

## Contributing

We welcome contributions to improve ShelterSpot. Fork the repository, create a branch for your
changes, and submit a pull request.

## Contact

Feel free to reach out with any questions or feedback at
[singhankit.kr@gmail.com](singhankit.kr@gmail.com)