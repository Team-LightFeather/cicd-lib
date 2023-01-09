import com.common.awsUtils

def call(buildConfig) {
    def imageName = buildConfig.container.imageName
    def buildImageTag = awsUtils.getEcrImageUrl(imageName, env.GIT_COMMIT)
    def pushImageTag = awsUtils.getEcrImageUrl(imageName, "latest")
    sh """
        aws ecr get-login-password --region ${env.AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${awsUtils.awsEcrEndpoint()}
        docker tag ${buildImageTagged} ${pushImageTagged}
        docker push ${pushImageTagged}
    """
}
