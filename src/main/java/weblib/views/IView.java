package weblib.views;

import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public interface IView {
    String getViewString(String path, String template, Request request, Response response, HttpMethod method);
}

