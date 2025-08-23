import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.service)
}

val environmentsFile = rootProject.file("sensitive/environments.properties")
val environmentsProperties = Properties().apply {
    load(FileInputStream(environmentsFile))
}

val signingFile = rootProject.file("sensitive/signing.properties")
val signingProperties = Properties().apply {
    load(FileInputStream(signingFile))
}

android {
    namespace = "com.countrylist"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.countrylist"
        minSdk = 24
        targetSdk = 36
        versionCode = 27
        versionName = "3.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = signingProperties["KEYALIAS"] as String
            keyPassword = signingProperties["KEYPASSWORD"] as String
            storeFile = file(signingProperties["STOREFILE"] as String)
            storePassword  = signingProperties["STOREPASSWORD"] as String
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")

            buildConfigField("String", "URL_BASE", environmentsProperties["URL_BASE_RELEASE"] as String)
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            buildConfigField("String", "URL_BASE", environmentsProperties["URL_BASE_DEBUG"] as String)

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.jackson)
    implementation(libs.retrofit.adapter.rxjava2)

    //Gson
    implementation(libs.gson)

    //KOIN
    implementation(libs.koin)
    implementation(libs.koin.compose)

    //Firebase
    implementation(libs.firebase.core)
    implementation(libs.firebase.database)

    //Logging Network Calls
    implementation(libs.logging.interceptor)

    //Async Image
    implementation(libs.async.image)
    implementation(libs.async.image.network)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}