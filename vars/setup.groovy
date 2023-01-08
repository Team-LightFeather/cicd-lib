/*
Setup is to define parameters and environment variables
Additionally it will define process flow of the whole pipeline
for this job in particular
*/
def call() {
    stage('Setup') {
        sh "echo \'Beginning Setup Process...\'"
    }
}