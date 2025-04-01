plugins {

    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.utkarsh.datedemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.utkarsh.datedemo"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))  // Firebase BOM
    implementation("com.google.firebase:firebase-database-ktx")          // Firebase Database (Kotlin)
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}