// To add data to a collection
curl -X POST 'http://localhost:18983/api/collections/wikipedia/update'\
 -H 'Content-Type: application/json' \
 -d "//mnt//d//works//workspaces//EclipseWorkspaces//POC//web-analytics-microservice//docker-compose//data//films.json"
 
// To set the autocommit settings
curl -X POST 'http://localhost:18983/api/collections/wikipedia/config'\ 
 -H 'Content-Type: application/json'\
 -d '{"set-property":{"updateHandler.autoCommit.maxTime":1500}}'
