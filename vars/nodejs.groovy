def call(Map args, Closure body) {
    withCredentials([gitUsernamePassword(credentialsId: args.githubCredsId, gitToolName: 'git-tool')]) {
        withDockerContainer(image: args.nodeImage, args: "-e HOME=${args.nodeHome}") {
            body()
        }
    }
}