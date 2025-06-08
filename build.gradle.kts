import jdk.tools.jlink.resources.jlink
import jdk.tools.jlink.resources.plugins

plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "3.1.1"
}

group = "com.yiaobang"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = "https://jitpack.io"
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    applicationName = "SerialPortKit"
    mainModule.set("com.yiaobang.serialportkit")
    mainClass.set("com.yiaobang.serialportkit.SerialPortKitApplication")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8", "-XX:+UseZGC")
}

javafx {
    version = "24.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

val lombokVersion = "1.18.38"
val atlantafxBaseVersion = "2.0.1"
val jSerialCommVersion = "2.11.0"
val commonsTextVersion = "1.13.1"
val commonsCodecVersion = "1.18.0"
val gsonVersion = "2.13.1"
val junitVersion = "5.13.1"
val rxcontrolsVersion = "11.0.3"

dependencies {
    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("io.github.mkpaz:atlantafx-base:$atlantafxBaseVersion")

    implementation("com.fazecast:jSerialComm:$jSerialCommVersion")
    implementation("org.apache.commons:commons-text:$commonsTextVersion")
    implementation("commons-codec:commons-codec:$commonsCodecVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("com.github.leewyatt:rxcontrols:$rxcontrolsVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    options.set(listOf("--strip-debug", "--compress", "zip-9", "--no-header-files", "--no-man-pages"))
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))

    launcher {
        name = "${application.applicationName}${version}"
        imageName.set(application.applicationName)
    }

    jpackage {
        // --- 通用配置 ---
        outputDir = "jpackage"
        imageName = "${application.applicationName}-${version}"
        skipInstaller = false
        installerName = application.applicationName
        appVersion = version.toString()

        vendor = "yiaobang"
        description =
            "A practical serial port utility built with JavaFX, featuring basic communication, serial port linking, forwarding, session management, and more."


        // 是否跳过创建安装程序(是否不生成安装包)
        skipInstaller = true

        // --- 平台特定配置 ---
        val os = org.gradle.internal.os.OperatingSystem.current()
        when {
            os.isWindows -> {
                icon = "src/main/resources/com/yiaobang/serialportkit/images/appicon/app@3X.ico"
                installerOptions.addAll(
                    listOf(
                        "--win-dir-chooser",// 允许用户选择安装目录
                        "--win-menu",// 在开始菜单创建快捷方式
                        "--win-shortcut",// 在桌面创建快捷方式
                        "--win-menu-group", application.applicationName,// 开始菜单组名
                        "--win-upgrade-uuid", "2c9b34da-7366-4b9b-96d7-86f281c5a204",//升级时使用的唯一标识符
                        "--win-shortcut-prompt",// 提示用户是否创建快捷方式
                        "--win-help-url", "https://github.com/yiaobang/SerialPortKit", // 帮助 URL
                    )
                )
            }

            os.isMacOsX -> {
                icon = "src/main/resources/com/yiaobang/serialportkit/images/appicon/app@4X.icns"
                installerOptions.addAll(
                    listOf(
                        "--mac-package-identifier", "com.yiaobang.serialportkit"// 应用包标识符
                    )
                )
            }

            os.isLinux -> {
                icon = "src/main/resources/com/yiaobang/serialportkit/images/appicon/app@4X.png"
                installerType = "deb"
                installerOptions.addAll(
                    listOf(
                        "--linux-deb-maintainer",
                        "yiaobang@gmail.com", // Deb 包维护者信息
                        "--linux-menu-group",
                        application.applicationName,// 菜单组名
                        "--linux-shortcut",// 创建桌面快捷方式
                        "--linux-app-category",
                        "Utility", // Linux 应用类别
                        "--linux-description",
                        "A practical serial port utility built with JavaFX, featuring basic communication, serial port linking, forwarding, session management, and more.", // 应用描述
                        "--linux-url",
                        "https://github.com/yiaobang/SerialPortKit", // 应用主页 URL
                        "--linux-bundle-name",
                        "serialportkit" // 自定义安装包名
                    )
                )
            }
        }

    }

}
