import com.common.config

def call(buildConfig) {
    for (env in config.environments) {
        deployStage(buildConfig, env)
    }
}

def deployStage(buildCOnfig, env) {
    stage("Deploy to ${env.capitlize()}") {
        sh "echo \'Beginning Deploy Process...\'"
        // This block refers to if a build is suppose to be containerized
        if (buildConfig.container != null && buildConfig.container.image.length() > 0) {
            buildToECR(buildConfig)
        }
        if (buildConfig.staticWebAssets != null && buildConfig.contai) {
            buildToS3Bucket(buildConfig)
        }
    }
}