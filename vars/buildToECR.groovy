import com.common.awsUtils

def call(buildConfig) {
    def imageName = buildConfig.container.imageName
    buildContainer(buildConfig.imageName)
    pushContainer(buildConfig.imageName)
}

def buildContainer(String imageName) {
    sh """
        aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()}
        docker build . -t ${imageName}
    """
}

def pushContainer(String imageName) {
    def buildImageTag = awsUtils.getEcrImageUrl(props.service, env.GIT_COMMIT)
    def pushImageTag = awsUtils.getEcrImageUrl(props.service, "latest")
    sh """
        aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()}
        docker tag ${buildImageTagged} ${pushImageTagged}
        docker push ${pushImageTagged}
    """
}