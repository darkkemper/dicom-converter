import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    id("io.ktor.plugin") version "2.2.2"
    id("com.github.johnrengelman.shadow") version "4.0.4"
    application
}

group = "ru.darkkemper"
version = "0.0.1"

repositories {
    maven {
        url = uri("https://www.dcm4che.org/maven2/")
    }
    mavenCentral()
    maven(url = "https://dl.bintray.com/kodein-framework/kodein-dev")
}

kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("dcm4che:dcm4che-imageio:2.0.29")
    implementation("dcm4che:dcm4che-imageio-rle:2.0.29")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    implementation("org.apache.logging.log4j:log4j-api:2.19.0")
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
    implementation("com.sun.media:jai_imageio:1.1")
    implementation("info.picocli:picocli:4.7.0")
    kapt("info.picocli:picocli-codegen:4.7.0")
}

application {
    mainClass.set("ru.darkkemper.dicomconverter.DicomAppKt")
}

tasks.withType<ShadowJar>() {
    mergeServiceFiles()
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClass,
                "Implementation-Vendor" to "darkkemper.ru",
                "Implementation-Version" to "0.0.1"
            )
        )
    }
}

ktor {
    fatJar {
        archiveFileName.set("dicom-converter.$version.jar")
    }
}