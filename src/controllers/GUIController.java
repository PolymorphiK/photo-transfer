package controllers;

import models.GUIModel;
import views.GUIView;

public class GUIController {
    private GUIView view;
    private GUIModel model;

    public GUIController(GUIView view, GUIModel model) {
        this.view(view);
    }

    public GUIView view() {
        return this.view;
    }

    public void view(GUIView view) {
        this.view = view;

        this.view.setEnabled(true);
    }
}
