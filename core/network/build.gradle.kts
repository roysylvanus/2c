import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.techadive.network"
    compileSdk = 35

    val secretsProperties = Properties()
    val secretsFile = rootProject.file("secrets.properties")
    if (secretsFile.exists()) {
        secretsFile.inputStream().use { secretsProperties.load(it) }
    }

    defaultConfig {
        minSdk = 26

        val token = secretsProperties.getProperty("TMDB_ACCESS_TOKEN") ?: ""
        buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$token\"")

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    hilt {
        enableAggregatingTask = false
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.dagger.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.code.gson)
    implementation(libs.retrofit2)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.okhttp3LoggingInterceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}