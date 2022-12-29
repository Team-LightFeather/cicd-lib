def _curl(String path) {
    return sh(script: "curl -s http://169.254.169.254/latest/${path}", returnStdout: true).trim()
}

def _curljq(String path, String jqFilter) {
    return sh(script: "curl -s http://169.254.169.254/latest/${path} | jq ${jqFilter}", returnStdout: true).trim()
}

def getAvailabilityZone() {
    return _curl("meta-data/placement/availability-zone")
}

def getRegion() {
    return _curl("meta-data/placement/region")
}

def getIdentityDocument() {
    return _curl("dynamic/instance-identity/document")
}

def getAccountNumber() {
    return _curljq("dynamic/instance-identity/document", ".AccountId")
}
