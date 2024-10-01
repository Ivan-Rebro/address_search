package riv.address.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Полнотекстовый поиск по адресу
 */
@Component
class AddressSearcher {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String ADDRESS_SEARCH_QUERY =
        """
        with query (address) as (
            values (:search)
        )
        select a.addressid,
               a.addressexpand,
               similarity(q.address, a.addressexpand) as sml
          from addresses.address a,
               query q
         order by a.addressexpand <-> q.address
         limit 25
        """;

    List<Map<String, String>> search(String address) {
        Map<String, Object> map = new HashMap<>();
        map.put("search", address);

        List<Map<String, String>> addresses = new ArrayList<>();
        jdbcTemplate.query(
            ADDRESS_SEARCH_QUERY,
            map,
            rs -> {
                Map<String, String> addressItem = new HashMap<>();
                addressItem.put("id", rs.getString(1)); // идентификатор
                addressItem.put("address", rs.getString(2)); // адрес
                addressItem.put("similarity", rs.getString(3)); // схожесть
                if (!addressItem.get("similarity").equals("0")) {
                    addresses.add(addressItem);
                }
            }
        );

        return addresses;
    }
}
