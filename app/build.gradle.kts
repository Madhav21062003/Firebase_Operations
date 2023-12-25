plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.madhavsolanki.firebase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.madhavsolanki.firebase"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Authentication
    implementation("com.google.firebase:firebase-auth:22.3.0")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database:20.3.0")

    // Firebase Realtime Storage
    implementation("com.google.firebase:firebase-storage:20.3.0")

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore:24.10.0")

    //FCM
    implementation("com.google.firebase:firebase-messaging:23.4.0")


    //Picasso For Image Upload
    implementation ("com.squareup.picasso:picasso:2.8")

}