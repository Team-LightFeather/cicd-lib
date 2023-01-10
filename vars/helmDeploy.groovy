import com.common.defaultConfig

def call(buildConfig) {
    def chartLocation = "${WORKSPACE}/${defaultConfig.getChartBySystem(buildConfig.system)}"
    def imageName = buildConfig.container.imageName
    def imageRepo = awsUtils.getEcrImageUrl(imageName, null)
    def imageTag = env.GIT_COMMIT.trim()
    sh """
        export KUBECONFIG=~/.kube/config
        aws eks update-kubeconfig --name bimms
        helm upgrade --install ${imageName} ${chartLocation} \
            --set serviceName=${imageName} \
            --set image.repository=${imageRepo} \
            --set image.tag=${imageTag}
    """
}