# svea-pmt-admin-api
Svea Webpay Payment Admin API

Library for talking to https://paymentadminapi.svea.com/api

This API is primarily used to administer order that has been created using
the checkout.

API documentation is found here https://paymentadminapi.svea.com/documentation/#/

## Use

Releases are found in Maven-Central repository and can easliy be used by adding a dependency to you pom.xml.

    <dependency>
        <groupId>org.notima.api.webpay</groupId>
        <artifactId>pmt-admin-api</artifactId>
        <version>2.1.3</version>
    </dependency>

Or in karaf

	install -s mvn:com.google.code.gson/gson/2.8.9
	install -s mvn:org.notima.api.webpay/pmt-admin-api
	

