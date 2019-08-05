package weblib;

import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import java.util.Map;

public class TemplateRenderer {

    public static String renderTemplate(String path, Map<String, Object> attributes) {
        return new PebbleTemplateEngine().render(
                new ModelAndView(attributes, path)
        );
    }
}
