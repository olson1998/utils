package com.olson1998.messaging.domain.model;

import com.olson1998.messaging.constant.Status;

public interface Reply extends EncodedOutbound {

    Status getStatus();

}
