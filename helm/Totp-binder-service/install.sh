#!/bin/bash
# Installs all Totp-binder-service helm charts
## Usage: ./install.sh [kubeconfig]

if [ $# -ge 1 ] ; then
  export KUBECONFIG=$1
fi

NS=Totp-binder-service
CHART_VERSION=1.2.0

Totp-binder-service_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-Totp-binder-service-host})

echo Create $NS namespace
kubectl create ns $NS

function installing_Totp-binder-service() {
  helm repo update

#  ./keycloak-init.sh

#  echo Please enter the recaptcha admin site key for domain $Totp-binder-service_HOST
#  read ESITE_KEY
#  echo Please enter the recaptcha admin secret key for domain $Totp-binder-service_HOST
#  read ESECRET_KEY

#  echo Setting up captcha secrets
#  kubectl -n $NS create secret generic Totp-binder-service-captcha --from-literal=Totp-binder-service-captcha-site-key=$ESITE_KEY --from-literal=Totp-binder-service-captcha-secret-key=$ESECRET_KEY --dry-run=client -o yaml | kubectl apply -f -

#  echo Setting up dummy values for Totp-binder-service misp license key
#  kubectl create secret generic Totp-binder-service-misp-onboarder-key -n $NS --from-literal=mosip-Totp-binder-service-misp-key='' --dry-run=client -o yaml | kubectl apply -f -

#  ./copy_cm_func.sh secret Totp-binder-service-misp-onboarder-key Totp-binder-service config-server

  echo Copy configmaps
  ./copy_cm.sh

  echo copy secrets
  ./copy_secrets.sh

#  kubectl -n config-server set env --keys=Totp-binder-service-captcha-site-key --from secret/Totp-binder-service-captcha deployment/config-server --prefix=SPRING_CLOUD_CONFIG_SERVER_OVERRIDES_
#  kubectl -n config-server set env --keys=Totp-binder-service-captcha-secret-key --from secret/Totp-binder-service-captcha deployment/config-server --prefix=SPRING_CLOUD_CONFIG_SERVER_OVERRIDES_
#  kubectl -n config-server set env --keys=mosip-Totp-binder-service-misp-key --from secret/Totp-binder-service-misp-onboarder-key deployment/config-server --prefix=SPRING_CLOUD_CONFIG_SERVER_OVERRIDES_

#  kubectl -n config-server get deploy -o name |  xargs -n1 -t  kubectl -n config-server rollout status

#  echo "Do you have public domain & valid SSL? (Y/n) "
#  echo "Y: if you have public domain & valid ssl certificate"
#  echo "n: If you don't have a public domain and a valid SSL certificate. Note: It is recommended to use this option only in development environments."
#  read -p "" flag

  if [ -z "$flag" ]; then
    echo "'flag' was provided; EXITING;"
    exit 1;
  fi
  ENABLE_INSECURE=''
  if [ "$flag" = "n" ]; then
    ENABLE_INSECURE='--set enable_insecure=true';
  fi

  echo Installing Totp-binder-service
  helm -n $NS install Totp-binder-service mosip/Totp-binder-service --version $CHART_VERSION $ENABLE_INSECURE

  kubectl -n $NS  get deploy -o name |  xargs -n1 -t  kubectl -n $NS rollout status

  echo Installed Totp-binder-service service
  return 0
}

# set commands for error handling.
set -e
set -o errexit   ## set -e : exit the script if any statement returns a non-true return value
set -o nounset   ## set -u : exit the script if you try to use an uninitialised variable
set -o errtrace  # trace ERR through 'time command' and other functions
set -o pipefail  # trace ERR through pipes
installing_Totp-binder-service   # calling function