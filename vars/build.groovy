def call(buildConfig) {
    stage('Build') {
        sh "echo \'Beginning Build Process...\'"
        def system = buildConfig.system.toLowerCase()
        if (system == "java") {
            sh "echo \'Building Java System...\'"
            docker(
                buildConfig: buildConfig,
                registry: "gradle:jdk17-alpine"
            ) {
                data -> run(data)
            }
        }

        if (system == "nodejs") {

        }
    }
}

def run(data) {
    sh "gradle build"
}