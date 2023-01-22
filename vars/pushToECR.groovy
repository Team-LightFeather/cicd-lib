def call(buildConfig) {
    ecrContainer(buildConfig) {
        d -> run(buildConfig)
    }

}

def ecrContainer(buildConfig, closure) {
    sh "aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()};"
    closure()
}

def run(buildConfig) {
    def imageName = buildConfig.container.imageName
    def buildImageTag = awsUtils.getEcrImageUrl(imageName, env.GIT_COMMIT)
    def pushImageTag = awsUtils.getEcrImageUrl(imageName, "latest")
    createIfNotExist(imageName)
    sh """
        aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()}
        docker push ${buildImageTag}
        docker tag ${buildImageTag} ${pushImageTag}
        docker push ${pushImageTag}
    """
}

def createIfNotExist(String imageName) {
    sh """
        aws ecr create-repository --repository-name ${imageName} || true;
    """
}