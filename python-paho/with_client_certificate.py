#!/usr/bin/env python

import paho.mqtt.client as mqtt

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("$SYS/#")

client = mqtt.Client()
client.on_connect = on_connect
client.tls_set("/Users/antares/Tools/rabbitmq/tls/ca_certificate.pem",
               "/Users/antares/Tools/rabbitmq/tls/client_certificate.pem",
               "/Users/antares/Tools/rabbitmq/tls/client_key.pem")
# disables peer verification
# client.tls_insecure_set(True)
client.connect("mercurio.local", 8883, 60)

client.loop_forever()
