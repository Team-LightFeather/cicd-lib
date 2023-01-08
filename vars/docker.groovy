def static defaultNodeImage = ""

def call(config, closure) {
    if(args.containsKey("githubCredsId")) {
        withCredentials([gitUsernamePassword(credentialsId: config.credentialId, gitToolName: 'git-tool')]) {
            withDockerContainer(image: args.nodeImage, args: "-e HOME=${args.nodeHome}") {
                closeure()
            }
        }
    }
}