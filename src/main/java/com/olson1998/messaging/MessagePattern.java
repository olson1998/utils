package com.olson1998.messaging;

import java.util.List;

public interface MessagePattern {

    List<String> getLabels();

    String getCommand();

    String getPayload();

}
