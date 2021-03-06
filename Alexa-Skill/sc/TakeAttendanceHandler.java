package sc;

import com.amazon.ask.Skill;
import com.amazon.ask.builder.StandardSkillBuilder;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.util.JacksonSerializer;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TakeAttendanceHandler implements RequestStreamHandler {

    private final Skill skillAtt;
    private final JacksonSerializer serializer;

    public TakeAttendanceHandler() {

        //Builds Skill
        skillAtt = new StandardSkillBuilder()
                //Adds functionality from AttResponseHandler to the Alexa Skill Base.
                .addRequestHandler(new AttResponseHandler())
                //Finalizes the skill.
                .build();

        serializer = new JacksonSerializer();

    }

    @Override
    /*
        InputStream gathers information provided by the Alexa device.
        OutputStream sends out modified data back out to amazon servers.
     */
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        //Deserialize user input.
        String request = IOUtils.toString(inputStream);
        //Converts the request to a format readable by Java.
        RequestEnvelope requestEnvelope = serializer.deserialize(request, RequestEnvelope.class);

        ResponseEnvelope responseEnvelope = skillAtt.invoke(requestEnvelope);
        //Converts our data to a format readable by Amazon.
        byte[] response = serializer.serialize(responseEnvelope).getBytes(StandardCharsets.UTF_8);
        outputStream.write(response);


    }
}
