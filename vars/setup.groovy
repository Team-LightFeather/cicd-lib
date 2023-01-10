/*
Setup is to define parameters and environment variables
Additionally it will define process flow of the whole pipeline
for this job in particular
*/
def call(buildConfig) {
    stage('Setup') {
        sh "echo \'Beginning Setup Process...\'"
        def system = buildConfig.system.toLowerCase()
        env.AWS_DEFAULT_REGION = awsUtils.getRegion()
        if (system == "java") {
            env.OUTPUT_DIR = "build/libs"
            env.TEST_REPORT_DIR = ""
        }
        if (system == "nodejs") { // Note we need to define this further, not sure if system is correct, maybe framework? springboot vs reactjs?
            env.OUTPUT_DIR = "build"
            env.TEST_REPORT_DIR = ""
        }
        /*
        This is to import docker and helm and other resources to be used in the pipeline
        */
        dir("cicd-lib") {
            git url: 'https://github.com/Team-LightFeather/cicd-lib.git', branch: 'feature/orchestration', credentialsId: 'github'
        }
    }
}
