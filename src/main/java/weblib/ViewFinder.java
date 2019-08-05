package weblib;

import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.ClassFilter;
import weblib.routing.Route;
import weblib.views.IView;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class ViewFinder {

    /**
     * Holds a reference to all instances of IView that we have found.
     */
    public static Set<IView> Views;

    /**
     * Returns a set containing all the views we found.
     * Side effect: updates the <code>List<IView> Views</code> object.
     *
     * @return Set containing view instances.
     */
    public static Set<IView> getAnnotatedViews() {
        Set<IView> views = new HashSet<>();

        // Surround this use of reflection with try to better handle errors.
        try {

            // This should return all classes that extend from IView.
            List<Class<?>> classes = CPScanner.scanClasses(
                    new ClassFilter().appendInterface(IView.class)
            );

            // This maps the classes to their route attribute, so we can handle both at the same time.
            Map coupledClassMap = classes.stream().collect(
                    Collectors.toMap(
                            viewClass -> viewClass.getAnnotation(Route.class),
                            ViewFinder::createViewInstance
                    )
            );

            // Goes through the Set and adds it to the view list.
            for (Object entry : coupledClassMap.entrySet()) {
                Map.Entry<Route, IView> viewPair = (Map.Entry<Route, IView>) entry;

                System.out.println("Adding class: " + viewPair.getValue().getClass().getName());
                views.add(viewPair.getValue());
            }

        } catch (Exception ex) {
            System.err.println("Exception in Viewfinder + " + ex);
        }

        // Update the global set.
        Views = views;

        // Finally return the result.
        return views;
    }

    /**
     * Returns an instance of a IView class.
     *
     * @param viewClass
     * @return instance of the class
     */
    private static <T> T createViewInstance(Class<T> viewClass) {
        try {
            return viewClass.newInstance();
        } catch (Exception e) {
            return null; //Bad idea but now it's waste of time
        }
    }
}
