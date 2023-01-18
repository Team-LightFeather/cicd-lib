def call(buildConfig, environment) {
    def paramPath = "/${buildConfig.projectName}/ui/${environment}/bucket_name"
    def s3Bucket = awsUtils.getValueFromParamStore("/some/path")
    print("Begin uploading to ${environment} S3 Bucket...")
    sh """
        aws s3 sync ./build s3://${s3Bucket}
    """
}