plugins {
    java
}

group = "com.windhc"
version = "0.0.1"

description = "brisk mvc"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public") }
}

dependencies {
    implementation("org.smartboot.http:smart-http-server:1.1.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("cn.hutool:hutool-all:5.6.3")
    implementation("org.slf4j:slf4j-api:1.7.30")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation("org.apache.logging.log4j:log4j-api:2.14.1")
    testImplementation("org.apache.logging.log4j:log4j-core:2.14.1")
    testImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
}
