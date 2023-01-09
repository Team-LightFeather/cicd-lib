def call(buildConfig) {
    stage('Build') {
        sh "echo \'Beginning Build Process...\'"
        def system = buildConfig.system.toLowerCase()
        if (system == "springboot") {
            buildContainer(buildConfig)
        }
        if (system == "reactjs") {
            buildStatic(buildConfig)
        }
    }
}

def buildContainer(buildConfig) {
    sh "echo \'Building Springboot System...\'"
    withDocker(
        buildConfig: buildConfig,
        registry: "gradle:jdk17-alpine"
    )
    {
        data -> runContainer(data)
    }
    sh """
        aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()}
        docker build . -t ${imageName}
    """
}

def runContainer(data) {
    sh "gradle build"
}

def buildStatic(buildConfig) {
    sh "echo \'Building ReactJS System\'"
    withDocker(
        buildConfig: buildConfig,
        registry: "node:19-alpine"
    ) {
        data -> runStatic(data)
    }
}

def runStatic(data) {
    sh "npm build"
}
