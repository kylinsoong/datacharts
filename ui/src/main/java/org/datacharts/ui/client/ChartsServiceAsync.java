package org.datacharts.ui.client;

import org.datacharts.ui.shared.ConfigurationOptions;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChartsServiceAsync {

    void csvFromClasspath(String path, AsyncCallback<ConfigurationOptions> callback);
}
