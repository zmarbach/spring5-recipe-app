package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try{
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            if(!recipeOptional.isPresent()){
                //todo handle error
                log.error("No recipe found with id: " + recipeId);
            }
            else{
                Recipe recipe = recipeOptional.get();
                Byte[] byteObjects = new Byte[file.getBytes().length];

                //converting byte primitive to Byte Object
                int i = 0;
                for (byte b : file.getBytes()){
                    byteObjects[i++] = b;
                }

                recipe.setImage(byteObjects);
                recipeRepository.save(recipe);
            }

        } catch (IOException e) {
            //todo handle error better
            log.error("An error occurred", e);
            e.printStackTrace();

        }
    }
}
