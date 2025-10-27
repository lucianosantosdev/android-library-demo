/**
 * Retrieves the SDK version from local.properties file.
 * If the file or property does not exist, returns "development".
 */
fun getVersionFromLocalProperties(): String {
    val propsFile = rootProject.file("local.properties")
    if (propsFile.exists()) {
        val props = java.util.Properties()
        propsFile.inputStream().use { props.load(it) }
        return props.getProperty("RELEASE_VERSION") ?: "development"
    }
    return "development"
}

// Export variables to be used in other build scripts
extra["releaseVersion"] = getVersionFromLocalProperties()