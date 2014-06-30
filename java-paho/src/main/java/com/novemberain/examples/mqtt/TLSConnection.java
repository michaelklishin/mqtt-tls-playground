package com.novemberain.examples.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class TLSConnection {
  public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, MqttException, UnrecoverableKeyException, KeyManagementException {
    MqttClient client = new MqttClient("ssl://localhost:8883", "paho-java-1");
    MqttConnectOptions opts = new MqttConnectOptions();

    final char[] passphrase = "bunnies".toCharArray();

    // client key
    KeyStore ks = KeyStore.getInstance("PKCS12");
    ks.load(new FileInputStream("/Users/antares/Tools/rabbitmq/tls/client_key.p12"), passphrase);

    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    kmf.init(ks, passphrase);

    // server certificate
    KeyStore tks = KeyStore.getInstance("JKS");
    // created the key store with
    // keytool -importcert -alias rmq -file ./server_certificate.pem -keystore ./jvm_keystore
    tks.load(new FileInputStream("/Users/antares/Tools/rabbitmq/tls/jvm_keystore"), passphrase);

    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(tks);

    SSLContext ctx = SSLContext.getInstance("SSLv3");
    ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    opts.setSocketFactory(ctx.getSocketFactory());

    client.connect(opts);
  }
}
