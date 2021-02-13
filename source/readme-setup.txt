Normal setup:

run the DDL script on your postgreSQL engine		"Persistence\db_init.sql"
(optional) run the DML script on your postgreSQL engine	"Persistence\db_populate.sql"
enter your db credentials and address to line 14 in	"SEP3 - tier 3\src\main\java\SEP3_tier3.java"
recompile tier 3 and start it
enter tier 3 address to line 24 in 			"SEP3 - tier 2\src\main\java\org\EvaAndTheSovietHouseholdAppliances\SEP3_tier2.java"
recompile tier 2 and start it
enter tier 2 address to line 12 in 			"SEP3 - tier 1\Data\HttpService.cs"
recompile tier 1 and start it

NUnit test setup:

if the db is already initialized, drop the schema	"Persistence\db_delete.sql"
run the DDL script on your postgreSQL engine		"Persistence\db_init.sql"
DO NOT run the DML populate script
enter your db credentials and address to line 14 in	"SEP3 - tier 3\src\main\java\SEP3_tier3.java"
recompile tier 3 and start it
enter tier 3 address to line 24 in 			"SEP3 - tier 2\src\main\java\org\EvaAndTheSovietHouseholdAppliances\SEP3_tier2.java"
recompile tier 2 and start it
enter tier 2 address to line 19 in 			"SEP3 - test\WebServices Unit Test\Controllers.cs"
run the unit tests