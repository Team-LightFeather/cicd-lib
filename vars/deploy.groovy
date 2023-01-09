import com.common.defaultConfig

def call(buildConfig) {
    for (e in defaultConfig.environments()) {
        deployStage(buildConfig, e)
    }
}

def deployStage(buildConfig, e) {
    stage("Deploy to ${e.capitalize()}") {
        sh "echo \'Starting Deploy Process...\'"
        // This block refers to if a build is suppose to be containerized
        if (buildConfig.container != null && buildConfig.container.imageName != null && buildConfig.container.imageName.length() > 0) {
            pushToECR(buildConfig)
            helmDeploy(buildConfig)
        }
        // if (buildConfig.staticWebAssets != null) {
        //     buildToS3Bucket(buildConfig)a
        // }
    }
}