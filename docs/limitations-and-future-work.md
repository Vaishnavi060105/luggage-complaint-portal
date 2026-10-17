# Limitations and Future Enhancements

## Limitations
- Single node, no high availability or load balancing
- In-memory H2 database: data is lost on restart
- Basic form login with two seeded reviewers; no admin screens
- No email/SMS notifications
- Polling instead of webhooks (Jenkins is on a private network)
- Uploaded files stored on local disk

## Future work
- Persistent MySQL/PostgreSQL database
- Webhook-triggered builds
- Kubernetes deployment
- Prometheus and Grafana monitoring
- Email/SMS notifications, admin dashboard, role management
- Cloud hosting and object storage for uploads
