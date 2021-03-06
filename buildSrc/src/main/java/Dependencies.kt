object Dependencies {
    const val versionCompose = "1.1.1"


    object OkHttp {
        const val version = "4.9.3"

        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
        const val okHttp = "com.squareup.okhttp3:okhttp:$version"
    }

    object Room {
        const val version = "2.4.2"

        const val room = "androidx.room:room-runtime:$version"
        const val roomKtx = "androidx.room:room-ktx:$version"
        const val roomKapt = "androidx.room:room-compiler:$version"
        const val roomTest = "androidx.room:room-testing:$version"
    }

    object Gson {
        const val version = "2.8.6"

        const val gson = "com.google.code.gson:gson:$version"
    }

    object Retrofit {
        const val version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Compose {
        const val versionAccompanist = "0.23.1"
        const val versionCharts = "0.3.4"

        const val runtime = "androidx.compose.runtime:runtime:$versionCompose"
        const val compiler = "androidx.compose.compiler:compiler:$versionCompose"
        const val ui = "androidx.compose.ui:ui:$versionCompose"
        const val foundation = "androidx.compose.foundation:foundation:$versionCompose"
        const val foundation_layout = "androidx.compose.foundation:foundation-layout:$versionCompose"
        const val material = "androidx.compose.material:material:$versionCompose"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$versionCompose"
        const val accompanist = "com.google.accompanist:accompanist-swiperefresh:$versionAccompanist"
        const val charts = "ma.hu:compose-charts:$versionCharts"
    }

    object Navigation {
        const val version = "2.4.1"

        const val navigationCompose = "androidx.navigation:navigation-compose:$version"
    }

    object Orbit {
        const val version = "4.3.2"
        const val orbitCore = "org.orbit-mvi:orbit-core:$version"
        const val orbit = "org.orbit-mvi:orbit-viewmodel:$version"
        const val orbitTest = "org.orbit-mvi:orbit-test:$version"
    }

    object Core {
        const val versionCoreKtx = "1.7.0"
        const val versionAppCompat = "1.4.1"
        const val versionMaterial = "1.5.0"
        const val versionCoroutines = "1.6.0"

        const val coreKtx = "androidx.core:core-ktx:$versionCoreKtx"
        const val appCompat = "androidx.appcompat:appcompat:$versionAppCompat"
        const val material  = "com.google.android.material:material:$versionMaterial"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$versionCoroutines"
    }

    object Di {
        const val version = "3.1.5"

        const val koin = "io.insert-koin:koin-android:$version"
        const val koinNavGraph = "io.insert-koin:koin-androidx-navigation:$version"
        const val koinCompose = "io.insert-koin:koin-androidx-compose:$version"
    }


    object Testing {
        const val versionJUnit = "4.13.2"
        const val versionMockk = "1.12.3"

        const val jUnit = "junit:junit:$versionJUnit"
        const val mockk =  "io.mockk:mockk:$versionMockk"
        const val mockkAndroid =  "io.mockk:mockk-android:$versionMockk"
        const val composeUiTest =  "androidx.compose.ui:ui-test-junit4:$versionCompose"
        const val composeTestRule = "androidx.compose.ui:ui-test-manifest:$versionCompose"

    }

    object Plugins {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val kotlinAndroid = "org.jetbrains.kotlin.android"
        const val kapt = "kapt"
    }
}