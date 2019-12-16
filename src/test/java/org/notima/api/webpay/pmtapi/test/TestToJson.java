package org.notima.api.webpay.pmtapi.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.notima.api.webpay.pmtapi.PmtApiUtil;
import org.notima.api.webpay.pmtapi.entity.Order;
import org.notima.api.webpay.pmtapi.entity.OrderRow;

public class TestToJson {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrderToJson() {
		Order order = new Order();
		order.setMerchantOrderId("56943879");
		OrderRow row = new OrderRow();
		row.setOrderRowId(200L);
		List<OrderRow> rows = new ArrayList<OrderRow>();
		order.setOrderRows(rows);
		rows.add(row);
		String json = PmtApiUtil.gson.toJson(order);
		System.out.println(json);
	}

}
