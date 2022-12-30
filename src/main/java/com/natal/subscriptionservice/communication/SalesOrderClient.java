package com.natal.subscriptionservice.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sales-order", url = "${sales-order.host}/sales-order")
public interface SalesOrderClient {

    @PutMapping("/cancel/{document}")
    void cancelOrdersByClient(@PathVariable("document") String document);

}
