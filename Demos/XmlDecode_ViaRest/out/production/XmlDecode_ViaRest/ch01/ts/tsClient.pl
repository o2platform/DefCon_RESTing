#!/usr/bin/perl -w

use SOAP::Lite;
my $url = 'http://127.0.0.1:9876/ts?wsdl';
my $service = SOAP::Lite->service($url);

print "\nCurrent time is: ", 
    $service->getTimeAsString();
print "\nElapsed milliseconds from the epoch: ", 
    $service->getTimeAsElapsed(), "\n";
