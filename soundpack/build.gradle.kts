plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.vanniktech.maven.publish)
    id("signing")
}

android {
    namespace = "com.endi.soundpack"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

signing {
    useGpgCmd()
}

group = "io.github.endid7"
version = "1.0.0"

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates(
        groupId = "io.github.endid7",
        artifactId = "soundpack",
        version = "1.0.0"
    )

    // bagian POM kamu tetap seperti sekarang
}