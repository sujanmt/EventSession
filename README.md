# Event Analytics Session
Event Analytics Session is an extension Android library that makes start the recording user session 
and track of any event.

## Quick Setup

Edit your build.gradle file and add below dependency.

```groovy
repositories {
  google()
  mavenCentral()
}

dependencies {
implementation 'com.sujan.eventanalytics:eventanalytics:xxx'
}
```

That's all. Now you are ready to go.

## Basic Usage

```kotlin
        // Initialize SDK with Room/Shared Preference storage implementation
        var storage = RoomAnalyticsStorage(applicationContext, Gson())
        AnalyticsSDK.initialize(storage)

        //Start Session
        findViewById<Button>(R.id.btnStartSession).setOnClickListener {
            if (!isSessionActive) {
                try {
                    AnalyticsSDK.startSession("test_session")
                    isSessionActive = true
                    showToast("Session started")
                    updateButtonStates()
                } catch (e: Exception) {
                    showToast("Error: ${e.message}")
                }
            }
        }


        //End Session
        findViewById<Button>(R.id.btnEndSession).setOnClickListener {
            if (isSessionActive) {
                try {
                    AnalyticsSDK.endSession()
                    isSessionActive = false
                    showToast("Session ended")
                    updateButtonStates()
                } catch (e: Exception) {
                    showToast("Error: ${e.message}")
                }
            }
        }

        //Track Event with Session
        findViewById<Button>(R.id.btnTrackEvent).setOnClickListener {
            if (isSessionActive) {
                try {
                    AnalyticsSDK.trackEvent(
                        "button_clicked",
                        mapOf(
                            "timestamp" to System.currentTimeMillis(), 
                            "button_name" to "track_event",
                            "screen" to "main"
                         )
                    )
                    showToast("Event tracked")
                } catch (e: Exception) {
                    showToast("Error: ${e.message}") 
                }
            } else { 
                showToast("No active session")
            }
        }

```
## License

```
Copyright (C) SMTXbot!c 2024, Event Analytics Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```