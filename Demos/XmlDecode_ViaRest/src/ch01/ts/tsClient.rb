#!/usr/bin/ruby

# one Ruby package for SOAP-based services
require 'soap/wsdlDriver' 

wsdl_url = 'http://127.0.0.1:9876/ts?wsdl'


service = SOAP::WSDLDriverFactory.new(wsdl_url).create_rpc_driver

# Save request/response messages in files named '...soapmsgs...'
service.wiredump_file_base = 'soapmsgs'

# Invoke service operations.
result1 = service.getTimeAsString
result2 = service.getTimeAsElapsed

# Output results.
puts "Current time is: #{result1}"
puts "Elapsed milliseconds from the epoch: #{result2}"
