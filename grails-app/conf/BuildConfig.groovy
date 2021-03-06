grails.project.work.dir = "target"

grails.project.dependency.resolution = {
    inherits("global")
    log "warn"

    repositories {
        grailsCentral()
        grailsPlugins()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        compile 'com.google.code.gson:gson:2.2.4'
        compile 'com.oohlalog:oohlalog-4j:1.0.2'
//        compile 'org.scala-lang:scala-library:2.11.0'
//        compile 'com.outr.javasysmon:javasysmon_2.10:0.3.4'

    }

    plugins {
       build(":release:2.2.1",
            ":rest-client-builder:1.0.3",
            ":tomcat:$grailsVersion",
            ":hibernate:$grailsVersion") {
            export = false
        }

    }
}
