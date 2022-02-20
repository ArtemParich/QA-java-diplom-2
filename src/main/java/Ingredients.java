import io.qameta.allure.Step;

import java.util.ArrayList;

public class Ingredients {

    public ArrayList<String> ingredients;

    @Override
    public String toString() {
        return "Ingredients{" +
                "ingredients=" + ingredients +
                '}';
    }

    public Ingredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Step("Getting ingredients")
    public static Ingredients getIngredients() {
        final ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        ingredients.add("61c0c5a71d1f82001bdaaa74");
        ingredients.add("61c0c5a71d1f82001bdaaa6e");
        return new Ingredients(ingredients);
    }

    @Step("Getting empty ingredients")
    public static Ingredients getEmptyIngredients() {
        final ArrayList<String> ingredients = new ArrayList<>();
        return new Ingredients(ingredients);
    }

    @Step("Getting incorrect hash ingredients")
    public static Ingredients getIncorrectHashIngredients() {
        final ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        ingredients.add("61c0c5a71d1f82001bdaaa");
        ingredients.add("61c0c5a71d1f82001bdaaa6e");
        return new Ingredients(ingredients);
    }
}
