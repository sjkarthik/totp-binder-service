# Totp-binder-service

Helm chart for installing Totp-binder-service module.

## TL;DR

```console
$ helm repo add mosip https://mosip.github.io
$ helm install my-release mosip/Totp-binder-service
```

## Introduction

Totp-binder-service is part of the Totp-binder-service modules, but has a separate Helm chart so as to install and manage it in a completely independent namespace.

## Prerequisites

- Kubernetes 1.12+
- Helm 3.1.0
- PV provisioner support in the underlying infrastructure
- ReadWriteMany volumes for deployment scaling

## Overview
Refer [Commons](https://docs.mosip.io/1.2.0/modules/commons).

## Initialize keycloak for Totp-binder-service
* To initialize keycloak for Totp-binder-service, run below script.
  ```sh
  ./keycloak-init.sh
  ```

## Install 
```
./install.sh
```

## Uninstall
```
./delete.sh
```
