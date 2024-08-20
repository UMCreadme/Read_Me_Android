import org.gradle.api.JavaVersion
import java.io.FileInputStream
import java.util.Properties

// Load properties from local.properties file
val properties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.readme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.readme"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${properties.getProperty("KAKAO_NATIVE_APP_KEY")}\"")
        buildConfigField("String", "SERVER_URL", "\"${properties.getProperty("SERVER_URL")}\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties["KAKAO_NATIVE_APP_KEY"] as String
        }
        release {
            isMinifyEnabled = false
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties["KAKAO_NATIVE_APP_KEY"] as String
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
        buildConfig = true
        dataBinding = true
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Google Play Services 및 추가 종속성
    implementation("com.google.android.gms:play-services-maps:18.0.0")

    // 추가 impl
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("me.relex:circleindicator:2.1.6")

    // 리사이클러 뷰 설정
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Android 공식 문서 : ViewModel 최신 dependencies
    val lifecycle_version = "2.8.4"
    val arch_version = "2.2.0"

    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-service:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-reactivestreams:$lifecycle_version")
    testImplementation("androidx.arch.core:core-testing:$arch_version")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:$lifecycle_version")

    implementation("androidx.viewpager2:viewpager2:1.1.0") // ViewPager2
    implementation("androidx.fragment:fragment-ktx:1.8.2") // ViewModels를 위한 최신 fragment 연결

    // Retrofit2 & Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (optional, for logging)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // 기본 impl
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.identity.credential.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // 카카오톡
    implementation("com.kakao.sdk:v2-all:2.20.3") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.20.3") // 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-cert:2.20.3") // 카카오톡 인증 서비스 API 모듈

    // Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    // 해시태그
    implementation("com.google.android.flexbox:flexbox:3.0.0")
}
