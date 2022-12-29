def getRegion() {
    return sh(script: "curl http://169.254.169.254/", returnStdout: true)
}