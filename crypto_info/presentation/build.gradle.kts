plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

composeCompiler {
    enableStrongSkippingMode = true
}

android {
    namespace = "com.presentation"
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.coil.compose)

    implementation(libs.voyager.kodein)
    implementation(libs.voyager.androidx)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.navigation)

    implementation(libs.kodein)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.material3)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":crypto_info::domain"))
    implementation(project(":core"))
}