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
    implementation("javax.inject:javax.inject:1")
    implementation("javax.inject:javax.inject-tck:1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation("org.slf4j:slf4j-api:1.7.30")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}
