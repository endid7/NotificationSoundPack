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

group = "io.github.endid7"
version = "1.1.2"

signing {
    useGpgCmd()
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates(
        groupId = "io.github.endid7",
        artifactId = "soundpack",
        version = "1.1.2"
    )

    pom {
        name.set("SoundPack")
        description.set("Indonesian payment notification sound library for Android")
        url.set("https://github.com/endid7/NotificationSoundPack")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("endid7")
                name.set("Endi")
                url.set("https://github.com/endid7")
            }
        }

        scm {
            url.set("https://github.com/endid7/NotificationSoundPack")
            connection.set(
                "scm:git:git://github.com/endid7/NotificationSoundPack.git"
            )
            developerConnection.set(
                "scm:git:ssh://git@github.com/endid7/NotificationSoundPack.git"
            )
        }
    }

    dependencies {
        testImplementation("junit:junit:4.13.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    }
}