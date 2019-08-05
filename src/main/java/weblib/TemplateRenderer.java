package weblib;

import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.ClassFilter;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;
import weblib.replication.Replicate;
import weblib.views.IView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateRenderer {

    /**
     * Renders a template using Pebble.
     * Note: @Replicate is used to pass template variables to the rendering.
     * @param view
     * @param path
     * @return
     */
    public static String renderTemplate(Class<? extends IView> view, String path) {
        // Declare a variable for the attributes
        Map<String, Object> attributes = new HashMap<>();

        // Get the view's reflection class.
        Class viewClass = view.getClass();

        for(Field field : viewClass.getDeclaredFields()) {
            Replicate replicateAnnotation = field.getAnnotation(Replicate.class);

            // If we find the annotation @Replicate, we can add it to the attributes:
            if(replicateAnnotation != null) {
                Object value = null;
                try {
                    value = field.get(view);
                    attributes.put(field.getName(), value);
                } catch (IllegalAccessException e) {
                    System.out.println("Ignored @Replicate value due to IllegalAccessException " + e);
                }
            }
        }

        return new PebbleTemplateEngine().render(
                new ModelAndView(attributes, path)
        );
    }
}
