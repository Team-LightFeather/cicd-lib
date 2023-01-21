/*
Call function does 4 things, it will setup, build, test, deploy
It will ingest a build parameter that will configure the workflow
*/
def call(buildConfig) {
    // Setup
    setup(buildConfig)
    if (isApplicationPipeline(buildConfig)) {
        applicationPipeline(buildConfig)
    }
    if (isInfraPipeline(buildConfig)) {
        terraformPipeline(buildConfig)
    }
}

def applicationPipeline(buildConfig) {
    // Build
    build(buildConfig)
    // Scan
    scan(buildConfig)
    // Deploy
    deploy(buildConfig)
}

def terraformPipeline(buildConfig) {
    terraformInit(buildConfig)
    terraformPlan(buildConfig)
    terraformApply(buildConfig)
}