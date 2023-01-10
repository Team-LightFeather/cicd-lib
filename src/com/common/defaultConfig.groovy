package com.common

static def environments() {
    return ["develop", "integration", "production"]
}

static def getChartBySystem(system) {
    if (system == "springboot") {
        return "resources/helm/springboot/chart"
    }
}