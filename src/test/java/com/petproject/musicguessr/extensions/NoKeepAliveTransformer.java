package com.petproject.musicguessr.extensions;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformerV2;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

/**
 * Решает проблему соединения, которая возникает из-за перезапуска WireMock между тестами.<br>
 * <a href="https://stackoverflow.com/questions/55624675/how-to-fix-nohttpresponseexception-when-running-wiremock-on-jenkins/68848928#68848928">Stackoverflow</a>
 */
public class NoKeepAliveTransformer implements ResponseDefinitionTransformerV2 {

    @Override
    public String getName() {
        return "keep-alive-disabler";
    }

    @Override
    public ResponseDefinition transform(ServeEvent serveEvent) {
        return ResponseDefinitionBuilder
                .like(serveEvent.getResponseDefinition())
                .withHeader("Connection", "close")
                .build();
    }
}