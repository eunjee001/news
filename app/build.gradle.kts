plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    // kotlin-android-extensions -> kotlin-parcelize
    id("kotlin-parcelize")
}

android {
    namespace = "com.kkyoungs.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kkyoungs.news"
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
    dataBinding {
        enable = true
    }
    testOptions{
        unitTests.isReturnDefaultValues = true
    }

    //Coroutine library 추가 시 발생하는 META-INF 관련 문제를 해결하기 위해 삽입
    configurations.all {
        resolutionStrategy{
            exclude(group= "org.jetbrains.kotlinx", module = "kotlinx.coroutines-debug")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("com.squareup.okhttp3:okhttp:4.4.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("org.jsoup:jsoup:1.13.1")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.7.0")

    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    implementation("androidx.activity:activity-ktx:1.8.2")

    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.8.22")

    implementation("android.arch.work:work-runtime-ktx:1.0.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation ("io.insert-koin:koin-core:3.5.0")
//    implementation("io.insert-koin:koin-test:3.1.6")
//    implementation("io.insert-koin:koin-androidx-viewmodel:2.2.3")
//    implementation("io.insert-koin:koin-androidx-scope:2.2.3")
    implementation("io.insert-koin:koin-android:3.0.2")
    testImplementation ("io.insert-koin:koin-test:3.1.6")

    implementation ("androidx.work:work-runtime-ktx:2.9.0")


}