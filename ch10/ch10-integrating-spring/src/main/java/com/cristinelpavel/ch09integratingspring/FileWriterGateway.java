package com.cristinelpavel.ch09integratingspring;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {
	void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
}
