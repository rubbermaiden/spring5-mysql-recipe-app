package borracha.springframework.controllers;

import borracha.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 6/1/17.
 */
@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");

        model.addAttribute("recipes", recipeService.getRecipes());
        model.addAttribute("controllerName", "IndexController");
        model.addAttribute("actionName", "home");

        return "index";
    }

    @RequestMapping("/contact")
    public String getContactPage(Model model) {
        log.debug("Getting Contact page");

        model.addAttribute("controllerName", "IndexController");
        model.addAttribute("actionName", "contact");

        return "contact";
    }

    @RequestMapping("/console")
    public String getConsolePage(Model model) {
        log.debug("Getting Console page");

        model.addAttribute("controllerName", "IndexController");
        model.addAttribute("actionName", "console");

        return "console";
    }
}
