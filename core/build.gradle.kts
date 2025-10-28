apply(from = "$rootDir/gradle/version.gradle.kts")
val releaseVersion: String by extra

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    id("jacoco")
    `maven-publish`
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
}
jacoco {
    toolVersion = libs.versions.jacoco.get()
}
// Apply Jacoco configuration
apply(from = "$rootDir/gradle/jacoco.gradle.kts")

android {
    namespace = "dev.lucianosantos.library.demo"
    compileSdk {
        version = release(36)
    }

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Tell Gradle we want to publish both debug & release variants
    publishing {
        multipleVariants {
            allVariants()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


afterEvaluate {
    publishing {
        publications {
            // === Release publication ===
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "dev.lucianosantos"
                artifactId = "library-demo-release"
                version = releaseVersion
            }

            // === Debug publication ===
            create<MavenPublication>("debug") {
                from(components["debug"])
                groupId = "dev.lucianosantos"
                artifactId = "library-demo-debug"
                version = releaseVersion
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/lucianosantosdev/android-library-demo")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}