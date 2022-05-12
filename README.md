<img src="/media/header.png" />
<div class="flex">
     <a href="https://play.google.com/store/apps/details?id=com.thunderstorm.app.android">
          <img src="https://img.shields.io/static/v1?label=Get+it+on&message=Google+Play&color=blue&style=for-the-badge" />
     </a>
     <img src="https://img.shields.io/github/issues/galaxygoldfish/thunderstorm?style=for-the-badge" />
     <img src="https://img.shields.io/github/license/galaxygoldfish/thunderstorm?style=for-the-badge" />
     <img src="https://img.shields.io/static/v1?label=version&message=1.0-beta&color=ff69b4&style=for-the-badge" />
     <img src="https://img.shields.io/github/forks/galaxygoldfish/thunderstorm?label=forks&style=for-the-badge&color=blueviolet" />
</div>
<br>
<p>
⚡️ Weather app for Android + iOS built with Kotlin Multiplatform Mobile, Jetpack Compose and SwiftUI. Features include:
</p>


- 24 hour weather forecast
- Sunrise/sunset times
- Humidity, UV Index, Air quality and Visibility
- 3 day forecast
- Local weather advisories/warnings
- Save multiple cities
- Homescreen widget

## Screenshots
<div class="flex" align="center">
     <img src="/media/weather.png" width=24% />
     <img src="/media/cities.png" width=24% />
     <img src="/media/search.png" width=24% />
     <img src="/media/alerts.png" width=24% />
</div>

## Libraries used
- SwiftUI
- Jetpack Compose
- Kotlin Multiplatform Mobile
- Ktor
- SQLDelight
- Accompanist
- AndroidX Glance
- Compose Navigation
- SwiftUIPager

## Project structure
<img src="/media/architecture.png" width=100% />

## Build notes
Before building, you must make sure to:
- Create an object in the directory ```shared/src/commonMain/kotlin/com/thunderstorm/app/Keystore.kt``` with your API key from [WeatherAPI](https://weatherapi.com). 
- Have the Kotlin Multiplatform Mobile plugin in Android Studio installed.

```kotlin
package com.thunderstorm.app

object Keystore {
    const val WeatherAPIKey = "your key here"
}
```

## License
```
Copyright (C) 2021 Sebastian Hriscu

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see https://www.gnu.org/licenses.
```
