plugins {
    java
}

group = "com.windhc"
version = "0.0.1"

description = "brisk mvc"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    mavenCentral()
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation("org.smartboot.http:smart-http-server:1.1.15")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.10")
    implementation("cn.hutool:hutool-all:5.8.5")
    implementation("org.slf4j:slf4j-api:1.7.36")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("ch.qos.logback:logback-core:1.2.11")
    testImplementation("ch.qos.logback:logback-classic:1.2.11")
}
