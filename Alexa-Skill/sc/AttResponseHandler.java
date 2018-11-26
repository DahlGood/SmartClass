package sc;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.Optional;

public class AttResponseHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return true;
    }


    //Response given to user.
    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        AWSCredentials credentials = new BasicAWSCredentials("AKIAIP6FJXLSG6ZSKPRA", "MnjNItNpmOVLRMAaK7aSsLOTFRAaUJffntrWwh0/");
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();
        CreateQueueRequest createQueueRequest = new CreateQueueRequest("ScriptStarter");
        String url = sqs.createQueue(createQueueRequest).getQueueUrl();

        String message = "Test from skill 2";

        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest().withQueueUrl(url).withMessageBody(message).withDelaySeconds(30);

        sqs.sendMessage(sendMessageStandardQueue);


        String response = "Please look ahead... taking attendance.";

        return handlerInput.getResponseBuilder().withSpeech(response).build();
    }
}
