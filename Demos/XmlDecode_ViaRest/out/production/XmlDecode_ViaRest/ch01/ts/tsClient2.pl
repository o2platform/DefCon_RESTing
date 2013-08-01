#!/usr/bin/perl -w

use SOAP::Lite;

my $endpoint = 'http://127.0.0.1:9876/ts'; # endpoint
my $uri      = 'http://ts.ch01/';          # namespace

my $client = SOAP::Lite->uri($uri)->proxy($endpoint);

my $response = $client->getTimeAsString()->result();
print $response, "\n";
$response = $client->getTimeAsElapsed()->result();
print $response, "\n";
