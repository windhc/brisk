plugins {
    java
}

group = "com.windhc"
version = "0.0.1"

description = "brisk mvc"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val nettyVersion = "4.1.50.Final"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public") }
}

dependencies {
    implementation("io.netty:netty-handler:${nettyVersion}")
    implementation("io.netty:netty-codec-http:${nettyVersion}")
    implementation("jakarta.inject:jakarta.inject-api:2.0.0-RC4")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.slf4j:slf4j-api:1.7.30")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}
