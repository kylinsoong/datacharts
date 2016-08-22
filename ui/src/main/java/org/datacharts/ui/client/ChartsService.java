package org.datacharts.ui.client;

import org.datacharts.ui.shared.ConfigurationOptions;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("charts")
public interface ChartsService {

    ConfigurationOptions csvFromClasspath(String path);
}
