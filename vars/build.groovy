def call(buildConfig) {
    stage('Build') {
        sh "echo \'Beginning Build Process...\'"
        def system = buildConfig.system.toLowerCase()
        if (system == "springboot") {
            buildSpringboot(buildConfig)
        }
        if (system == "reactjs") {
            buildReactJs(buildConfig)
        }
    }
}

def buildSpringboot(buildConfig) {
    sh "echo \'Building Springboot System...\'"
    withDocker(
        buildConfig: buildConfig,
        registry: "gradle:jdk17-alpine"
    )
    {
        data -> runSpringbootBuild(data)
    }
    def image = awsUtils.getEcrImageUrl(buildConfig.container.imageName, env.GIT_COMMIT)
    sh """
        aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()}
        docker build . -t ${image} -f ./cicd-lib/docker/Dockerfile.springboot
    """
}

/*
This runs a build and will generate a build jar and unit test reports
*/
def runSpringbootBuild(data) {
    sh "gradle build"
}

def buildReactJs(buildConfig) {
    sh "echo \'Building ReactJS System\'"
    withDocker(
        buildConfig: buildConfig,
        registry: "node:19-alpine"
    ) {
        data -> runStatic(data)
    }
}

def runStatic(data) {
    sh "npm ci"
    sh "npm run build"
}
