def call(Map args, Closure body) {
    withCredentials([gitUsernamePassword(credentialsId: args.githubCredsId, gitToolName: 'git-tool')]) {
        withDockerContainer(image: args.nodeImage, args: "-e HOME=${args.nodeHome}") {
            try {
                sh "echo registry=https://npm.pkg.github.com >> .npmrc"
                sh "echo //npm.pkg.github.com/:_authToken=${env.GIT_PASSWORD} >> .npmrc"

                body()
            } finally {
                sh 'rm ./.npmrc'
            }
        }
    }
}