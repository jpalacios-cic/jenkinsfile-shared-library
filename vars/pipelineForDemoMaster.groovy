import groovy.json.JsonSlurper

def deployApp(newDeployment, kubeconfig) {
    newDeployment.metadata.name = "nodejs-app-${env.VERSION}"
    newDeployment.metadata.labels.version = "${env.VERSION}"
    newDeployment.spec.selector.matchLabels.version = "${env.VERSION}"
    newDeployment.spec.template.metadata.labels.version = "${env.VERSION}"
    newDeployment.spec.template.spec.containers[0].image = "${env.DOCKER_IMAGE}:${env.VERSION}"

    sh "rm deployment.yaml"
    writeYaml file: "deployment.yaml", data: newDeployment

    sh "kubectl --kubeconfig ${kubeconfig} apply -f deployment.yaml"
}