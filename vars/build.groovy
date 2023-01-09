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
    dir("cicd-lib") {
        git url: 'https://github.com/Team-LightFeather/cicd-lib.git', branch: 'feature/orchestration', credentialsId: 'github'
    }
    sh """
        aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()}
        docker build . -t ${buildConfig.container.imageName} -f ./cicd-lib/docker/Dockerfile.springboot
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
