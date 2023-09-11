# First create the solr cluster and the zookeeper cluster
# Then create a configset using the schema designer
# add an empty document and then start creating fields
# Create collection using the configset just created
# In the Admin UI, choose Add Copy Field, then fill out the source * and destination as _text_
curl -X PUT -H "Content-Type:application/octet-stream" \
--data-binary @/mnt/d/works/workspaces/EclipseWorkspaces/POC/web-analytics-microservice/docs/wikipedia_configset.zip \
"http://localhost:18983/api/cluster/configs/wikipedia"

# List all available configsets
curl http://localhost:18983/api/cluster/configs/