def _curl(String path) {
    return sh(script: "curl -s http://169.254.169.254/latest/${path}", returnStdout: true).trim()
}

def _curljq(String path, String jqFilter) {
    return sh(script: "curl -s http://169.254.169.254/latest/${path} | jq ${jqFilter}", returnStdout: true).trim()
}

def  getAvailabilityZone() {
    return _curl("meta-data/placement/availability-zone")
}

def getRegion() {
    return _curl("meta-data/placement/region")
}

def getIdentityDocument() {
    return _curl("dynamic/instance-identity/document")
}

def getAccountNumber() {
    return _curljq("dynamic/instance-identity/document", ".accountId")
}

def getEcrImageUrl(String containerName, String tag) {
    if(tag == null)
        return "${awsEcrEndpoint()}/${containerName}";
    else
        return "${awsEcrEndpoint()}/${containerName}:${tag}";
}

def awsEcrEndpoint() {
    return "${getAccountNumber()}.dkr.ecr.${getRegion()}.amazonaws.com"
}

def getValueFromParamStore(String value, String region = "us-east-1") {
    return sh(
        script: """
            aws ssm get-parameter --name ${value} \
                --region ${region} --no-paginate \
                --output text --query "Parameter.Value" 
        """,
        returnStdout: true
    )
}

def getValueFromSecretStore(String secret_id, String query = null, String region = "us-east-1") {
    def output = sh(
        script: """
            aws secretsmanager get-secret-value \
                -- region ${region} --output text \
                --secret-id ${secret_id} --query "SecretString"
        """,
        returnStdout: true
    )
    if (query != null) {
        return "something jq of output"
    }
    return output
}
