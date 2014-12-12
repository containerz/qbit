package io.advantageous.qbit.http;

import java.util.function.Consumer;

/**
 * Created by rhightower on 10/28/14.
 * @author rhightower
 */
public interface HttpClient {
    void sendHttpRequest(HttpRequest request);


    void sendWebSocketMessage(WebSocketMessage webSocketMessage);

    void periodicFlushCallback(Consumer<Void> periodicFlushCallback);


    void run();

    void flush();

    void stop();

}