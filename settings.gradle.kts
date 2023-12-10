pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AlphaIntelligence"
include(":app")
include(":home")
include(":home:domain")
include(":home:data")
include(":home:presentation")
include(":core")
include(":crypto_info")
include(":crypto_info:data")
include(":crypto_info:domain")
include(":crypto_info:presentation")
include(":navigation")
