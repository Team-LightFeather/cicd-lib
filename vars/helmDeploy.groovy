import com.common.defaultConfig

def call(buildConfig, namespace) {
    def chartLocation = "${WORKSPACE}/${defaultConfig.getChartBySystem(buildConfig.system)}"
    def imageName = buildConfig.container.imageName
    def imageRepo = awsUtils.getEcrImageUrl(imageName, null)
    def imageTag = env.GIT_COMMIT.trim()
    def port = buildConfig.container.config.network.port
    def domain = "bimms.lightfeathersandbox.com"
    def certArn = "arn:aws:acm:us-east-2:063989428983:certificate/298bc9b6-a2eb-4184-a646-53426cd46424"
    sh """
        export KUBECONFIG=~/.kube/config;
        aws eks update-kubeconfig --name bims;
        helm upgrade --install ${imageName} ${chartLocation} \
            --namespace ${namespace} \
            --set serviceName=${imageName} \
            --set image.repository=${imageRepo} \
            --set image.tag=${imageTag} \
            --set container.port=${port} \
            --set eks.certificateArn=${certArn} \
            --set domain=${domain};
    """
}