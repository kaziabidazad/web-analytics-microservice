#If facing with corrupt file error during cluster startup.
#####You need to fix the corrupt indexes.

#####Run these commands for all the shards of the cluster.

#####Step 1 -> Download the lucene-core-x.x.x.jar file (check your solr version and find out the depedent luce-core jar from maven)

#####Step 2-> Initiate the exorcise command
<code>
java -cp lucene-core-6.3.0.jar -ea:org.apache.lucene... org.apache.lucene.index.CheckIndex <path_to_index_folder> -exorcise
</code>

#####Example:


<code>
java -cp lucene-core-9.7.0.jar -ea:org.apache.lucene... org.apache.lucene.index.CheckIndex D:\works\workspaces\EclipseWorkspaces\POC\web-analytics-microservice\data\solr3\data\wikipedia_shard3_replica_n16\data\index.20230917082406740\ -exorcise
</code>

#####Note: This will delete the corrupt segments, therefore all documents under the segment will be lost!!

######Always backup periodically to avoid this data loss.