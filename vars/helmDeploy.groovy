import com.common.defaultConfig

def call(buildConfig, namespace) {
    def chartLocation = "${WORKSPACE}/${defaultConfig.getChartBySystem(buildConfig.system)}"
    def imageName = buildConfig.container.imageName
    def imageRepo = awsUtils.getEcrImageUrl(imageName, null)
    def imageTag = env.GIT_COMMIT.trim()
    def port = buildConfig.container.network.port
    def domain = "bimms.lightfeathersandbox.com"
    def certArn = "arn:aws:acm:eu-west-1:063989428983:certificate/473f2d38-311e-4065-b564-6da01ce08539"
    sh """
        export KUBECONFIG=~/.kube/config;
        aws eks update-kubeconfig --name bimms;
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