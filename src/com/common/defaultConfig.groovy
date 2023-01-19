package com.common

static def environments() {
    return ["dev", "int", "prod"]
}

static def getChartBySystem(system) {
    if (system == "springboot") {
        return "cicd-lib/resources/helm/springboot/chart"
    }
}