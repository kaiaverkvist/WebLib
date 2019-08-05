
# WebLib  
Abstraction for Spark that allows cleaner routing code.  
  
# Example of usage:  
  
 > Application.java
```java
public static void main(String[] args) {
    WebLibHost webLibHost = new WebLibHost(8080, "/public");
}
```

Now that we've spawned the WebLib instance, we can define some routes.

> IndexView.java
```java
@Route(Path = "/", Template = "templates/index.html", Method = HttpMethod.get)  
public class IndexView implements IView {
    @Replicate
    public String Name = "this variable is replicated through {{ Name }} in your template.";

    @Override
    public String getViewString(String path, String template, Request request, Response response, HttpMethod method) {
        return TemplateRenderer.renderTemplate(this);
    }
}
```

This is all you need to get started.
