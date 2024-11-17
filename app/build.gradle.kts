plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.flickster"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.flickster"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }

}

dependencies {

    val lifecycle_version = "2.8.6"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //noinspection UseTomlInstead
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    //noinspection UseTomlInstead
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
}