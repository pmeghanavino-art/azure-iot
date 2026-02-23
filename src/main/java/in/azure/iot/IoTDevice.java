package in.azure.iot;

import com.microsoft.azure.sdk.iot.device.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class IoTDevice implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        String conn =    System.getenv("IOT_DEVICE_CONN");

        if (conn == null || conn.isBlank()) {
            throw new RuntimeException("Missing IOT_DEVICE_CONN environment variable");
        }

        DeviceClient client = new DeviceClient(conn, IotHubClientProtocol.MQTT);
        client.open(true);

        System.out.println("Device Connected → Machine ACTIVE");

        while (true) {

            Message msg = new Message("{\"status\":\"ACTIVE\"}");

            client.sendEventAsync(msg, (status, context, exception) -> {

                if (exception == null) {
                    System.out.println("Message Sent → " + status);
                } else {
                    System.out.println("Send Failed → " + exception);
                }

            }, null);

            Thread.sleep(30000);
        }
    }
}