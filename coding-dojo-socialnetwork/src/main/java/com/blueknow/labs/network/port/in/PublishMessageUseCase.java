/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.port.in;

import java.util.List;

public interface PublishMessageUseCase {

    void publish(final String user, final String message);

    List<String> findMessagesByUser(final String user);
}
