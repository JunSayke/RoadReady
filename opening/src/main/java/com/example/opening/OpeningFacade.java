package com.example.opening;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;

import com.example.core.MainFacade;

public class OpeningFacade extends MainFacade {
    public static NavigationListener toHomepage = () -> {};
    private static OpeningFacade openingFacade = null;
    private NavController openingNavController;

    public synchronized static OpeningFacade getInstance() {
        assert openingFacade != null;
        return openingFacade;
    }

    public synchronized static OpeningFacade initInstance(FragmentActivity activity) {
        if (openingFacade == null) {
            openingFacade = new OpeningFacade(activity);
        }
        return openingFacade;
    }

    private OpeningFacade(FragmentActivity activity) {
        super(activity);
    }

    public NavController getOpeningNavController() {
        return openingNavController;
    }

    public void setOpeningNavController(NavController openingNavController) {
        this.openingNavController = openingNavController;
    }
}