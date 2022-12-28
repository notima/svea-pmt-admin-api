# Tests

To create a test suite sample file, remove the 

	src/test/resources/test-order-suite.json

and run

	TestOrderSuite
	
## Single tests

Single tests extends the class TestBase.

If you want to run tests against a single merchant id, use the 

	src/test/resources/config-template.xml
	
and as a template and save real credentials in the file

	src/test/resources/config-test.xml
	
