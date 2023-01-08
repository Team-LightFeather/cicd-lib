def static defaultNodeImage = ""

def call(config, closure) {
    withDockerContainer(image: config.buildConfig.registry) {
        closure()
    }
}