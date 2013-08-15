grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.repos.bertramlabsSnap.url = "http://nexus.bertramlabs.com/content/repositories/snapshots"
grails.project.repos.bertramlabsSnap.type = "maven"
grails.project.repos.bertramlabsSnap.username = System.getProperty('project.repos.bertramlabsSnap.username')
grails.project.repos.bertramlabsSnap.password = System.getProperty('project.repos.bertramlabsSnap.password')

grails.project.repos.bertramlabsRel.url = "http://nexus.bertramlabs.com/content/repositories/releases"
grails.project.repos.bertramlabsRel.type = "maven"
grails.project.repos.bertramlabsRel.username = System.getProperty('project.repos.bertramlabsRel.username')
grails.project.repos.bertramlabsRel.password = System.getProperty('project.repos.bertramlabsRel.password')


grails.project.repos.default = "bertramlabsSnap"
grails.release.scm.enabled = false


grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
        mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        compile 'com.google.code.gson:gson:2.2.2'
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":release:2.2.0",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}
