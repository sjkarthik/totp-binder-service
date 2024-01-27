# totp-binder-service

Helm chart for installing totp-binder-service module.

## TL;DR
## Introduction

totp-binder-service is part of the totp modules, but has a separate Helm chart so as to install and manage it in a completely independent namespace.

## Prerequisites

- Kubernetes 1.12+
- Helm 3.1.0
- PV provisioner support in the underlying infrastructure
- ReadWriteMany volumes for deployment scaling

## Install 
```
./install.sh
```

## Uninstall
```
./delete.sh
```
