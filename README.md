# Thunderstorm
⚡️Weather app for Android + iOS built with Kotlin Multiplatform Mobile. (Under heavy development)

## Build notes
Before building, you must create an object in the directory ```shared/src/commonMain/kotlin/com/thunderstorm/app/Keystore.kt``` with your API key from [WeatherAPI](https://weatherapi.com).

```
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
