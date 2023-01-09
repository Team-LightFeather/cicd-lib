static def _curl(String path) {
    return sh(script: "curl -s http://169.254.169.254/latest/${path}", returnStdout: true).trim()
}

static def _curljq(String path, String jqFilter) {
    return sh(script: "curl -s http://169.254.169.254/latest/${path} | jq ${jqFilter}", returnStdout: true).trim()
}

static def  getAvailabilityZone() {
    return _curl("meta-data/placement/availability-zone")
}

static def getRegion() {
    return _curl("meta-data/placement/region")
}

static def getIdentityDocument() {
    return _curl("dynamic/instance-identity/document")
}

static def getAccountNumber() {
    return _curljq("dynamic/instance-identity/document", ".AccountId")
}

static def getEcrImageUrl(String containerName, String tag) {
    if(tag == null)
        return "${awsEcrEndpoint()}/${containerName}";
    else
        return "${awsEcrEndpoint()}/${containerName}:${tag}";
}

static def awsEcrEndpoint() {
    return "${getAccountNumber()}.dkr.ecr.${getRegion()}.amazonaws.com"
}
