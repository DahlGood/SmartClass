import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class Queue_Service {

    public static void main(String[] args) throws Exception {

        AWSCredentials credentials = new BasicAWSCredentials("access_key_id", "access_key_secret");
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();
        CreateQueueRequest createQueueRequest = new CreateQueueRequest("ScriptStarter");
        String url = sqs.createQueue(createQueueRequest).getQueueUrl();

        String message = "Hello";



        send(credentials, sqs, url, message);

        receive(sqs, url);

    }

    public static void send(AWSCredentials credentials, AmazonSQS sqs, String url, String message) {

        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest().withQueueUrl(url).withMessageBody(message).withDelaySeconds(30);

        sqs.sendMessage(sendMessageStandardQueue);

    }

    public static void receive(AmazonSQS sqs, String url) {

        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(url)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        List<Message> msg = sqs.receiveMessage(receiveMessageRequest).getMessages();

        System.out.println(msg.get(0));

    }

}