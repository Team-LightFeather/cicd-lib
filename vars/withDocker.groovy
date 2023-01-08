def static defaultNodeImage = ""

def call(config, closure) {
    withDockerContainer(image: config.registry) {
        closure()
    }
}