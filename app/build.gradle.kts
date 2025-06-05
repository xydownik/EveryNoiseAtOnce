plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.google.services)
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin") version "2.7.7"
}
val clientId: String = project.findProperty("CLIENT_ID") as String? ?: ""
val clientSecret: String = project.findProperty("CLIENT_SECRET") as String? ?: ""

android {
    namespace = "com.example.everynoiseatonce"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.everynoiseatonce"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Если вы хотите хранить Spotify-ключи здесь (учтите безопасность!).
        buildConfigField("String", "SPOTIFY_CLIENT_ID", "\"$clientId\"")
        buildConfigField("String", "SPOTIFY_CLIENT_SECRET", "\"$clientSecret\"")
    }
    signingConfigs {
        create("release") {
            storeFile = file("C:/Users/Public/release-key1.jks")
            storePassword = "A31c12e04"
            keyAlias = "key1"
            keyPassword = "A31c12e04"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        buildConfig=true
    }
}

dependencies {
    // AndroidX Core/AppCompat/Material
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Lifecycle, ViewModel, LiveData, Coroutines, Flow
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Room (кэш)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.swiperefreshlayout)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // WorkManager (планировщик задач)
    implementation(libs.androidx.work.runtime.ktx)

    // Retrofit + Moshi + OkHttp
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.moshi.kotlin)

    // Dagger 2 или Hilt
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)


    // Glide (для загрузки изображений)
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // Espresso (UI-тесты)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // RxJava (для MVP-экрана)
    implementation(libs.rxjava3)
    implementation(libs.rxandroid)

    // Firebase (Auth, FCM, Crashlytics)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.crashlytics.ktx)

    // Unit-testing
    testImplementation(libs.junit)

    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.session)

}

