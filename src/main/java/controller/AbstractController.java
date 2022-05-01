package controller;

import main.MainApplication;
import service.SuperService;

public class AbstractController {
    protected SuperService service;
    protected MainApplication application;

    public void setService(SuperService service) {
        this.service = service;
    }

    public void setApplication(MainApplication application) {
        this.application = application;
    }

    public void reset() {
    }
}
