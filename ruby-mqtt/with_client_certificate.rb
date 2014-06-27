#!/usr/bin/env ruby

require 'rubygems'
require "mqtt"

client = MQTT::Client.new('localhost', :ssl => :TLSv1)
client.ca_file   = "/Users/antares/Tools/rabbitmq/tls/ca_certificate.pem"
client.cert_file = "/Users/antares/Tools/rabbitmq/tls/client_certificate.pem"
client.key_file  = "/Users/antares/Tools/rabbitmq/tls/client_key.pem"

client.connect do
  client.subscribe('test')

  client.publish('test', "hello world")

  topic, message = client.get
  p [topic, message]
end
