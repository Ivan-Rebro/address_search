package riv.address.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
class AddressSearchApiController {

    @Autowired
    private AddressSearcher addressSearcher;

    /**
     * API для работы с полнотекстовым поиском по адресу
     */
    @GetMapping("/api")
    List<Map<String, String>> search(
        @RequestParam("address") String address
    ) {
        return addressSearcher.search(address);
    }
}
