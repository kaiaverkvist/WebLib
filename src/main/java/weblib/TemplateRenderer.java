package weblib;

import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.ClassFilter;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;
import weblib.replication.Replicate;
import weblib.routing.Route;
import weblib.views.IView;

import java.lang.annotation.Annotation;
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
     * @return
     */
    public static String renderTemplate(IView view) {
        // Declare a variable for the attributes
        Map<String, Object> attributes = new HashMap<>();

        Class<?> viewClass = view.getClass();

        Route route = viewClass.getAnnotation(Route.class);

        for(Field field : viewClass.getDeclaredFields()) {
            Replicate replicateAnnotation = field.getAnnotation(Replicate.class);

            // If we find the annotation @Replicate, we can add it to the attributes:
            if(replicateAnnotation != null) {
                Object value = null;
                try {
                    value = field.get(view);
                    attributes.put(field.getName(), value);
                } catch (IllegalAccessException e) {
                    System.out.println("(Not an error!) Ignored @Replicate value: " + e);
                }
            }
        }

        String template = route.Template();

        return new PebbleTemplateEngine().render(
                new ModelAndView(attributes, template)
        );
    }
}
