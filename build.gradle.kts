plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application
    
    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
    mavenCentral()
}

val jUnitVersion = "5.7.1"
val slf4jVersion = "1.7.30"

dependencies {
    implementation("com.diffplug.durian:durian:3.4.0") // one-line lambda exception handling
    implementation("com.google.apis:google-api-services-books:v1-rev20201021-1.31.0")
    implementation("com.omertron:API-OMDB:1.5")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("org.slf4j:slf4j-log4j12:$slf4jVersion")

    // JUnit API and testing engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

application {
    // Define the main class for the application.
    mainClass.set("it.unibo.sampleapp.App")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<Test> {
        // Enables JUnit 5 Jupiter module
        useJUnitPlatform()
    }

    register<DefaultTask>("listPlugins") {
        doLast {
            project.plugins.forEach {
                println(it)
            }
        }
    }

    getting(Jar::class) {
        manifest {
            attributes["Main-Class"] = "it.unibo.sampleapp.App"
        }
    }

    task("runMain", JavaExec::class) {
        main = "it.unibo.sampleapp.App"
        classpath = sourceSets["main"].runtimeClasspath
    }
}
