/*
Call function does 4 things, it will setup, build, test, deploy
It will ingest a build parameter that will configure the workflow
*/
def call(buildConfig) {
    // Setup
    setup(buildConfig)
    // Build
    build(buildConfig)
    // Scan
    scan(buildConfig)
    // Deploy
    deploy(buildConfig)
}