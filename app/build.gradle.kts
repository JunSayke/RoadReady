plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    // START_OF[Safe Args]
    id("androidx.navigation.safeargs")
    // END_OF[Safe Args]
}

android {
    namespace = "com.example.roadready"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.roadready"
        minSdk = 27
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
    // START_OF[View Binding]
    buildFeatures {
        viewBinding = true
    }
    // END_OF[View Binding]
}

dependencies {
    // START_OF[Retrofit Dependencies]
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    // END_OF[Retrofit Dependencies]

    // START_OF[Picasso Dependencies]
    implementation("com.squareup.picasso:picasso:2.71828")
    // END_OF[Picasso Dependencies]

    // START_OF[View Dependencies]
    implementation("io.github.chaosleung:pinview:1.4.4")
    implementation("com.github.gcacace:signature-pad:1.3.1")
    // END_OF[View Dependencies]

    // START_OF[Google Maps Dependencies]
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    // END_OF[Google Maps Dependencies]

    // START_OF[Google Auth Dependencies]
    implementation("androidx.credentials:credentials:1.3.0-alpha01")

    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0-alpha01")
    // END_OF[Google Auth Dependencies]

    // START_OF[JWT]
    implementation("com.auth0:java-jwt:4.4.0")
    // END_OF[JWT]

    // OTHERS
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}