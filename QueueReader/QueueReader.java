import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

public class QueueReader {

    public QueueReader() {

        //Creating connection SQS service.
        AWSCredentials credentials = new BasicAWSCredentials("access_id", "access_key");
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();
        CreateQueueRequest createQueueRequest = new CreateQueueRequest("ScriptStarter");
        String url = sqs.createQueue(createQueueRequest).getQueueUrl();

        //Retreiving the message from the queue.
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(url)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(1);

        //Storing the message.
        List<Message> msg = sqs.receiveMessage(receiveMessageRequest).getMessages();

        /*

        RUN FACIAL RECOGNITION HERE

         */

        //Deleting the message from the queue after it's been received.
        for (Message m : msg) {
            sqs.deleteMessage(url, m.getReceiptHandle());
        }

    }

}