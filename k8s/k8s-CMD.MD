# Scale down to all dependencies
kubectl scale deployments --all --replicas=0 -n default

# Get app logs
kubectl logs ems-app-66664db8cb-zcfqt

# Restart app without restart dependencies
kubectl rollout restart deployment/ems-app 

# open database in dBeaver
kubectl port-forward svc/database 5432:5432