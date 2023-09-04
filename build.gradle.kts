plugins {
    id("java")
}


group = "org.example"


allprojects {
    repositories {
        mavenCentral()
    }
}


subprojects {
    version = "1.0-SNAPSHOT"

    tasks.withType<JavaCompile>().configureEach {
        //options.compilerArgs.add("--enable-preview")
        javaCompiler.set(javaToolchains.compilerFor {
            languageVersion.set(JavaLanguageVersion.of(17))
        })
    }
}
