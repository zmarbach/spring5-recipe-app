package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by jt on 6/19/17.
 */
@Slf4j
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return  RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("recipe")
    //giving @ModelAttribute the name of "recipe" adds the command parameter as an attribute on the model with the name "recipe"
    //Binding result is result of validation checks
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult, Model model){

        //if validation fails, we will display errors to user
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors()
                    .forEach( objectError -> log.debug(objectError.toString()));
            return  RECIPE_RECIPEFORM_URL;
        }

        //if validation passes, save recipe as normal
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }


    //have to use @ResponseStatus above the method too if want to return specific Http Status code...@ExceptionHandler overrides the @ResponseStatus on exception class
    @ResponseStatus(HttpStatus.NOT_FOUND)
    //telling spring that anytime NotFoundException is thrown...map to this method and run the logic. So will always go to 404error page
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e){
        log.error("Handling not found error");
        log.error(e.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        //can add error to the ModelAndView cuz we passed the Exception in above as parameter
        modelAndView.addObject("exception", e);

        return modelAndView;
    }


}
