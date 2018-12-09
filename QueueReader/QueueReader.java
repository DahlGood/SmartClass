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

        //Creating TimerTask
        TimerTask timerTask = new TimerTask()
        {
            String[] path = { "./faceRecognition/video_face_counter.py" };

            public void run()

            {
                //if the message contains "Take attendance", the facial recognition will run
                if(Arrays.asList(msg).contains("Take attendance"))
                    Runtime.getRuntime().exec(path);            }

        };

        //Timer repeats the run() method every 3 seconds
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 3000);

        //Deleting the message from the queue after it's been received.
        for (Message m : msg) {
            sqs.deleteMessage(url, m.getReceiptHandle());
        }

    }

}
