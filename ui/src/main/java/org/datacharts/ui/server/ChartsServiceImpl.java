package org.datacharts.ui.server;

import org.datacharts.ui.client.ChartsService;
import org.datacharts.ui.shared.ConfigurationOptions;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ChartsServiceImpl extends RemoteServiceServlet implements ChartsService {

    private static final long serialVersionUID = -8897252088923534411L;

    @Override
    public ConfigurationOptions csvFromClasspath(String path) {
        
        ConfigurationOptions options = new ConfigurationOptions();
        
        return options;
    }

}
