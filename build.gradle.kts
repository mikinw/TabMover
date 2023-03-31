plugins {
    id("org.jetbrains.intellij") version "1.12.0"
    java
    kotlin("jvm") version "1.3.61"
}

group = "com.mnw.tabmover"
version = "1.5.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
//    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2022.1.1")
    updateSinceUntilBuild.set(false)
    plugins.set(listOf("com.intellij.java"))
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    patchPluginXml {
        version.set("${project.version}")
        sinceBuild.set("221")
        untilBuild.set("231.*")
    }
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
}
