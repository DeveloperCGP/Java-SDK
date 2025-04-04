plugins {
    id("java")
    kotlin("jvm")
    id("maven-publish")
    id("java-library")
}

group = "com.comerciaglobalpayments"
version = "01.00.01"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("JavaPaymentSdk") {
            from(components["java"])
            groupId = "com.comerciaglobalpayments"
            artifactId =  "java-sdk"
        }
    }
    repositories {
        maven {
            url = uri(project.findProperty("url") as String? ?: System.getenv("URL") ?: "")
            credentials {
                username = project.findProperty("user") as String? ?: System.getenv("USERNAME") ?: ""
                password = project.findProperty("token") as String? ?: System.getenv("TOKEN") ?: ""
            }
        }
    }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("commons-validator:commons-validator:1.8.0") {
        exclude(group = "commons-logging", module = "commons-logging")
    }
    implementation("org.json:json:20231013")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.corundumstudio.socketio:netty-socketio:2.0.3"){
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    implementation("io.socket:socket.io-client:2.1.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.5")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.mockk:mockk:1.13.9")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    exclude( "META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
kotlin {
    jvmToolchain(17)
}
