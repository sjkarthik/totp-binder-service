#!/bin/bash
# Installs all totp-binder-service helm charts
## Usage: ./install.sh [kubeconfig]

if [ $# -ge 1 ] ; then
  export KUBECONFIG=$1
fi

NS=totp
CHART_VERSION=1.2.0

echo Create $NS namespace
kubectl create ns $NS

function installing_totp-binder-service() {
  helm repo update

  echo Copy configmaps
  ./copy_cm.sh

  kubectl -n totp set env --keys=mosip-totp-binder-service-host --value=totp-binder-service.onpremdev.idencode.link --from ConfigMaps/global

  TOTP_BINDER_SERVICE_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-totp-binder-service-host})

#  echo copy secrets
#  ./copy_secrets.sh

  echo Installing totp-binder-service
  helm -n $NS install totp-binder-service $HOME/totp-binder-service/helm/totp-binder-service --version $CHART_VERSION

  kubectl -n $NS  get deploy -o name |  xargs -n1 -t  kubectl -n $NS rollout status

  echo Installed totp-binder-service
  return 0
}

# set commands for error handling.
set -e
set -o errexit   ## set -e : exit the script if any statement returns a non-true return value
set -o nounset   ## set -u : exit the script if you try to use an uninitialised variable
set -o errtrace  # trace ERR through 'time command' and other functions
set -o pipefail  # trace ERR through pipes
installing_totp-binder-service   # calling function