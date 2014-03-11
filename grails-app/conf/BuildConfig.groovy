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
        compile 'oohlalog:oohlalog-4j:0.1.1'
//        runtime 'com.google.code.gson:gson:2.2.2'
//        compile 'com.google.code.gson:gson:2.2.2'
       // build 'com.google.code.gson:gson:2.2.2'
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
