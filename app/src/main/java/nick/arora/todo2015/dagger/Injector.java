package nick.arora.todo2015.dagger;

import nick.arora.todo2015.BaseApplication;

public enum Injector {
    INSTANCE;

    private ApplicationComponent applicationComponent;

    Injector(){
    }

    public void initializeApplicationComponent(BaseApplication app) {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .appContextModule(new AppContextModule(app))
                .build();
        this.applicationComponent = applicationComponent;

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
