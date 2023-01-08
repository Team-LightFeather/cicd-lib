def static defaultNodeImage = ""

def call(config, closure) {
    withDockerContainer(image: conifg.buildConfig.registry) {
        closure()
    }
}