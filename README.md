# Sample

This app uses the device sensors to display and save weather/environment data.

Using the Android sensor manager, it can collect temperature and humidity values, and persist the readings using a Room database on the device.
All the readings can then be seen in a list, and show a detailed view of the readings.

The app is build with a modulised approach, following the CLEAN architecture patterns. 
That means the app is separated in app, feature, domain and data modules, each layer responsiple for:
- App: Setting up DI and include features, and setting up navigation, and any app specific configurations
- Feature: Handling UI and all related logic, primarily containing Composables and ViewModels
- Domain: Handling potential business logic, using UseCases, and contains Repository interfaces
- Data: Handling different data sources

Noteworthy libraries used in the app:
- Koin
- Room
- Navigation component

Considerations for further development
- Unit testing
- Centralized gradle setup, using convention plugin
- Improved Koin setup to avoid feature module using data module
- Split into more feature modules
- Better date handling
- UI for clear/delete readings
- Move Room database to a core module
- KMP
