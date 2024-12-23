import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

android {
    namespace = "com.sujan.eventanalytics"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.10")
}

mavenPublishing {
    // publishing to https://s01.oss.sonatype.org
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()
}

mavenPublishing {
    coordinates("com.sujan.eventAnalytics", "eventAnalytics", "1.0.0")

    pom {
        name.set("Event Analytics")
        description.set("An open source Android library that makes start the recording user session and track of any event.")
        inceptionYear.set("2024")
        url.set("https://github.com/sujanmt/EventSession/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("EventSession")
                name.set("Event Session")
                url.set("https://github.com/sujanmt/")
            }
        }
        scm {
            url.set("https://github.com/sujanmt/EventSession/")
            connection.set("scm:git:git://github.com:sujanmt/EventSession.git")
            developerConnection.set("scm:git:ssh://git@github.com:sujanmt/EventSession.git")
        }
    }
}