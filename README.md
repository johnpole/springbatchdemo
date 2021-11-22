# springbatchdemo
learning spring batch


Readers

JDBCCursorItemReader
  step 1: data source
  step 2: SQL
  Step 3: mapping the colums to the bean

Read from JSON:
Resource: where to read from the file
JsonObjectReader: How to map the JSON object to JAVA domain object

Read from WebService:
ItemReaderAdapter
Set Target Object
SetTargetMethod
Implement Productservice Adapter
Tell Item reader when to stop reading

Item Writers

FlateFile Item Writer
XML Item Writer
DatabaseItem Writer

Resource* where to write the file
Line Aggreagator let the writer know how to map each properties to each line
Set HEader and Footer

XML Item Writer - StaxEventItemWriter

Resource, Marshaller, RootTagName

nees spring oxm and xstream dependency


Database Item Writer
JDBC Item Writer
set Datasource
setSQL
setItemPrepared Statement


Item Processor

Filtering ( this decide which needs to send to writer) and transformation


Implement BulleProof Jobs

Robust Traceble,Restartable

skip invalid records
