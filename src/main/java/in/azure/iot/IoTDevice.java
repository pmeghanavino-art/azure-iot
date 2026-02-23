package in.azure.iot;

import com.microsoft.azure.sdk.iot.device.*;

public class IoTDevice {

    private static final String CONNECTION_STRING =System.getenv("IOT_DEVICE_CONN");
    public static void main(String[] args) {

        DeviceClient client = null;

        try {
            client = new DeviceClient(CONNECTION_STRING, IotHubClientProtocol.MQTT);

            client.open(true);

            System.out.println("Device Connected → Machine ACTIVE");

            while (true) {

                String payload = "{ \"status\": \"ACTIVE\" }";

                Message msg = new Message(payload);

                client.sendEventAsync(msg, (status, context, exception) -> {

                    if (exception == null) {
                        System.out.println("Message Sent → " + status);
                    } else {
                        System.out.println("Send Failed → " + exception);
                    }

                }, null);

                Thread.sleep(30000);
            }

        } catch (Exception e) {

            System.out.println("Connection Lost / Device OFF");
            System.out.println("Reason: " + e.getMessage());

        } finally {

            try {
                if (client != null) client.close();
            } catch (Exception ignored) {}
        }
    }
}