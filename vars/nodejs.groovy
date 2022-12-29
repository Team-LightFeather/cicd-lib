/**
 * Sets up a nodejs environment to run commands in (via docker)
 *
 * Supported arguments
 *  githubCredsId: if set, this will setup a temporary .npmrc file for authenticating to the github npm package repo
 *  nodeImage: What docker image to use for the node environment
 *  nodeHome: What to set the HOME environment variable to in the docker container
 *
 * @param args
 * @param body
 * @return
 */
def call(Map args, Closure body) {
    if(args.containsKey("githubCredsId")) {
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
    } else {
        withDockerContainer(image: args.nodeImage, args: "-e HOME=${args.nodeHome}") {
            body()
        }
    }

}