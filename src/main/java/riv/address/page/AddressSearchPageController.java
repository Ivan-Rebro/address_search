package riv.address.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class AddressSearchPageController {

    /**
     * Страница с полнотекстовым поиском по адресу
     */
    @GetMapping("/")
    ModelAndView searchPage() {
        return new ModelAndView("index.html");
    }
}
