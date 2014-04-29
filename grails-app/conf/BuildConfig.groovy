grails.project.work.dir = "target"

grails.project.dependency.resolution = {
    inherits("global")
    log "warn"

    repositories {
        grailsCentral()
        grailsPlugins()
        mavenLocal()
        mavenCentral()
        mavenRepo "http://nexus.bertramlabs.com/content/repositories/publicReleases"
    }

    dependencies {
        compile 'com.google.code.gson:gson:2.2.4'
        compile 'oohlalog:oohlalog-4j:0.1.3'
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
