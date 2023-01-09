import com.common.config

def call(buildConfig) {
    for (e in config.environments) {
        deployStage(buildConfig, e)
    }
}

def deployStage(buildCOnfig, e) {
    stage("Deploy to ${e.capitlize()}") {
        sh "echo \'Starting Deploy Process...\'"
        // This block refers to if a build is suppose to be containerized
        if (buildConfig.container != null && buildConfig.container.image.length() > 0) {
            pushToECR(buildConfig)
            helmDeploy(buildConfig)
        }
        // if (buildConfig.staticWebAssets != null) {
        //     buildToS3Bucket(buildConfig)a
        // }
    }
}