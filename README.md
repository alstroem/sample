# Sample

This app uses the device sensors to display and save weather/environment data.

Using the Android sensor manager, it collects temperature and humidity values, and persists the readings using a Room database on the device.
All the readings can then be seen in a list, and show a detailed view of the readings.

The app is built with a modularised approach, following the CLEAN architecture patterns. 
That means the app is separated into app, feature, domain, and data modules, each layer responsible for:
- App: All base configurations as DI and navigation setup.
- Feature: UI and all UI-related logic, primarily containing Composables and ViewModels.
- Domain: Potential business logic, using UseCases, and contains Repository interfaces.
- Data: Different data sources and mapping.

Following the CLEAN architecture pattern, first of all, it gives a clear separation of concerns, meaning each layer has its own responsibilities. This avoids huge classes that are error-prone and hard to test, makes the code easier to maintain and scale, and enforces simpler dependencies.
Having separate modules and layers also gives the option to reuse modules and easily change modules.

Noteworthy libraries used in the app:
- Koin
- Room
- Navigation component

Considerations for further development
- KMP
- Unit testing
- Centralized gradle setup, using convention plugin
- Improved Koin setup to avoid feature module including data module
- Split into more feature modules
- Tracking and logging
- Move Room database to a core module
- Better date handling
- UI for clear/delete readings
