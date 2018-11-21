package sc;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

public class AttResponseHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return true;
    }


    //Response given to user.
    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        String response = "Please look ahead... taking attendance.";

        return handlerInput.getResponseBuilder().withSpeech(response).build();
    }
}
